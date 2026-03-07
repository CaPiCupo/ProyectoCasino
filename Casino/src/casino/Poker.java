package casino;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;

public class Poker {
	//JUEGO POKER MODALIDAD TEXAS HOLD'EM m
	//private Usuario us; 
	//private Usuario us2;
	/* TODO --- LIST
	 * Mirar el check para quienes pagaron la ciega y fueron igualados - HECHO (CREO)
	 * Poner modo sin ai para probar el funciones BOOCACA quitar AL ENTREGA 
	 * ARREGLAR EL BOOLEAN QUE ESCONDE LAS CARTAS (tambien falla en el ALL IN)
	 * 
	 */
	private UsuarioPk us; 
	private UsuarioPk us2;
	private boolean multijugador;
	static Jugador[] j = new Jugador[4];
	static final int maxct = 52;
	static Cartas[] ct = new Cartas[maxct];
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Cartas> liCUs = new ArrayList<Cartas>(); //Para Analizar
	static ArrayList<Cartas> liCAi = new ArrayList<Cartas>();
	static ArrayList<Cartas> liCTotal = new ArrayList<Cartas>();
	static BigDecimal stack1;
	static BigDecimal stack2;
	static BigDecimal stack;
	static String[] liCalle = {"PREFLOP", "FLOP", "TURN", "RIVER"};
	static String[] liTipoAi = {"EarlyH2", "EarlyH1", "EarlyHB", "EarlyB1" ,"EarlyB2","TrueHB", "LateH2", "LateH1", "LateHB", "LateB1" ,"LateB2", "JCK"};
	static String[] liNombreAi = {"Cashy MacMoneyFace", "Chris MoneyMaker", "Trabajador de cuello azul", "Casado con los ahorros", "Un pensionista", "Quique", "Goku", "LeBrons", "La virgen Maria", "Rebeca Racañez", "Mario Hidalgo", "Ea Nasir", "Yakub", "Abraxas", "Manny Heffley", "Super Bigote", "John Casino", "Federico Apuestas", "Gru", "Shrek", "Grug Crood", "El Lorax", "Balatro Balatrez", "Dr. Gregory House", "Media Docena de Minions", "Un tipo con un sombrero guay", "Hegel", "Teto Kasane", "Dos duendes en una gabardina", "3 puros y una persona", "Dinero Dinerez", "El preterito pluscuamperfecto", "Schopenhauer", "Subaru Estrella", "Mortadelo", "Sticky Joe", "El PIB de Haiti", "Apple Jack", "Un chino", "Hideo Kojima", "Señor Pink", "Mordekaiser", "D.B. Cooper","Hornet", "Fishy ahh looking person" , "Therian de un pez gota", "THE CEO MINDSET", "Sheldon l.cooper", "Kike", "Quike", "Kique", "Opsie! All Aces", "Rigoberto Faroles" , "Un Fiat multipla"};
	
	public Poker (UsuarioPk us, UsuarioPk us2,/*Usuario us, Usuario us2,*/ boolean multijugador) {
		this.us = us;
		this.multijugador = multijugador;
		this.us2 = us2;
		
		for(int i = 0; i<j.length; i++) {
	        j[i] = new Jugador();
		}
		
		generarCarta();
		generarJugadores();
		juegodePoker();
				
	}
	
	public void generarJugadores() {
		    us.setConDineroAun(true);
	        us.setValueF(us.getUsuario().getDinero());
	        us.setValueP(BigDecimal.ZERO); 
	        us.setCalleAct(0);
	        us.setSidePot(false);
	        us.setCartas(new Cartas[2]);
	        UsuarioPk u1 = us;
	        j[0].sUs(us);
	        j[0].sAi(null);
	        //j[0].sPos(0);
			j[0].setTipo("us");
	        if (multijugador && us2 != null) {
	            us2.setConDineroAun(true);
	            us2.setValueF(us2.getUsuario().getDinero());
	            us2.setValueP(BigDecimal.ZERO);
	            us2.setCalleAct(0);
	            us2.setSidePot(false);
	            us2.setCartas(new Cartas[2]);
	            UsuarioPk u2 = us2;
		        j[1].sUs(us2);
		        j[1].sAi(null);
		        //j[1].sPos(1);
		        j[1].setTipo("us");
	        }
	}
	
	public void generarAI() {
		PokerAI[] ai = new PokerAI[4];
	    Random r = new Random();
		if (multijugador && us2 != null) {
			for(int i = 2; i < 4; ++i) {
				ai[i] = new PokerAI();
				ai[i].setTipoAI(liTipoAi[r.nextInt(liTipoAi.length)]);
				ai[i].setNombreAI(liNombreAi[r.nextInt(liNombreAi.length)]);
				ai[i].setCartas(new Cartas[2]);
				ai[i].setDinero(stack.multiply(BigDecimal.valueOf(0.75f + r.nextFloat() * (0.50f))).setScale(0, RoundingMode.HALF_EVEN));
				ai[i].setPosicion(i);
				ai[i].setSidePot(false);
				j[i].sAi(ai[i]);
				j[i].sUs(null);
				//j[i].sPos(i);
				j[i].setTipo("ai");
			}
		} else {
			for(int i = 1; i < 4; ++i) {
				ai[i] = new PokerAI();
				ai[i].setTipoAI(liTipoAi[r.nextInt(liTipoAi.length)]);
				ai[i].setNombreAI(liNombreAi[r.nextInt(liNombreAi.length)]);
				ai[i].setCartas(new Cartas[2]);
				ai[i].setDinero(stack.multiply(BigDecimal.valueOf(0.75f + r.nextFloat() * (0.50f))).setScale(0, RoundingMode.HALF_EVEN));
				ai[i].setPosicion(i);
				ai[i].setSidePot(false);
				j[i].sAi(ai[i]);
				j[i].sUs(null);
				//j[i].sPos(i);
				j[i].setTipo("ai");
			}
		}
		
	}
	public static void generarCarta() {	
		int i;
		int i2 = 0;
		int cnm = 1;
		BigDecimal vnm = new BigDecimal("0.05"); //Para el valor de las cartas
		BigDecimal pnm = new BigDecimal("0.05");
		for (i = 0; i < maxct; i++) {
        ct[i] = new Cartas();
        if(cnm == 1) { //MAS VALOR PARA CARTAS ALTAS (+0.05 2-10 +0.10 10-A)
        		ct[i].setNumero("A");
        		ct[i].setValor(BigDecimal.valueOf(1.00)); 
        } else if(cnm == 10) {
  			ct[i].setNumero("T");
      		ct[i].setValor(BigDecimal.valueOf(0.55));
        } else if(cnm == 11) {
  			ct[i].setNumero("J");
      		ct[i].setValor(BigDecimal.valueOf(0.65));
        } else if(cnm == 12) {
			ct[i].setNumero("Q");
      		ct[i].setValor(BigDecimal.valueOf(0.75));
        } else if(cnm == 13) {
			ct[i].setNumero("K");
      		ct[i].setValor(BigDecimal.valueOf(0.85));
        } else {
      		ct[i].setNumero(String.valueOf(cnm));
      		ct[i].setValor(vnm);
        }
        switch (i2) {
            case (0):
                ct[i].setColor("♥"); // C de Corazon
            break;
            case (1):
                ct[i].setColor("♦"); // D de Diamante
            break;
            case (2):
            	ct[i].setColor("♣"); // T de trebol
            break;
            case (3):
            	ct[i].setColor("♠"); // P de Pica
		    break;
        }
		    cnm++;
		    vnm = vnm.add(pnm);
	        if (cnm > 13) {
	      	cnm = 1;
	      	vnm = new BigDecimal("0.05");
	     	i2++;
	        }
		}
		
	}

	public static Cartas[] barajar(Cartas[] ct) {
	    Random r = new Random();
	    for (int i = ct.length - 1; i > 0; i--) {
	        int j = r.nextInt(i + 1);

	        Cartas x = ct[i];
	        ct[i] = ct[j];
	        ct[j] = x;
	    }
	    return ct;
	}
	
	/*public BigDecimal mediarStack(BigDecimal st1, BigDecimal st2) {
		if(!multijugador) {
			return st1;
		} else {
			BigDecimal r;
			return r.add((st1.add(st2)).divide(BigDecimal.TWO));
		}
	}*/
	public void comprobarAllIn(int xJug, Cartas[] c) {
		
	    if (j[xJug].getTipo().equals("us")) {
	        if (j[xJug].gUs().getStack().compareTo(BigDecimal.ZERO) <= 0) {
	        	j[xJug].gUs().setStack(BigDecimal.ZERO);
	        	j[xJug].setActionAllIn(true);
	        	
				if((j[0].isActionAllIn() == true || j[0].isActionFoldeo() == true) && (j[1].isActionAllIn() == true || j[1].isActionFoldeo() == true) && (j[2].isActionAllIn() == true || j[2].isActionFoldeo() == true) && (j[3].isActionAllIn() == true || j[3].isActionFoldeo() == true)) {
					c[0].setOculto(false);
					c[1].setOculto(false);	    
				}
	        }
	    } 
	    else if (j[xJug].getTipo().equals("ai")) {
	        if (j[xJug].gAi().getDinero().compareTo(BigDecimal.ZERO) <= 0) {
	        	j[xJug].gAi().setDinero(BigDecimal.ZERO);
	        	j[xJug].setActionAllIn(true);
				if((j[0].isActionAllIn() == true || j[0].isActionFoldeo() == true) && (j[1].isActionAllIn() == true || j[1].isActionFoldeo() == true) && (j[2].isActionAllIn() == true || j[2].isActionFoldeo() == true) && (j[3].isActionAllIn() == true || j[3].isActionFoldeo() == true)) {
					c[0].setOculto(false);
					c[1].setOculto(false);	    
				}
	        }
	    }
	}
	
	public static void reiniciarDatos(){
			
	}
	public boolean todosFoldeados () {
		int xJugSinFoldear = 0;
		int unicoSinFolder = -1;

		for(int i = 0; i < j.length; i++){
		    if(!j[i].isActionFoldeo()){
		    	xJugSinFoldear++;
		    	unicoSinFolder = i;
		    }
		}

		if(xJugSinFoldear == 1){
		   return true;
		} else {
			return false;
		}
	}
	public void repartirPot(int ganador, BigDecimal mainPot){

	    if(j[ganador].getTipo().equals("us")){
	        j[ganador].gUs().setStack(
	        	
	            j[ganador].gUs().getStack().add(mainPot)
	        );
	    }
	    else{
	        j[ganador].gAi().setDinero(
	            j[ganador].gAi().getDinero().add(mainPot)
	        );
	    }
	    //ESTO ES RIDICULO, PONER DESPUES DE LLAMAR A ESTE METODO
	    mainPot = BigDecimal.ZERO;
	}
	
	
	public static char identificarValor () {
		   
		
	}
	
	public static void optionPokerUs (int resp, int xJug, Cartas[] c) {
		
	}
	
	public  void darStack() {
		//HACERLO PROCEDIMIENTO
		if(multijugador) {
			System.out.print("FICHAS STACK JUGADOR 1: ");
			int st = sc.nextInt();
			if (st <= 0){
				System.out.println("Apuesta invalida");
			} else if(us.getDinero().compareTo(new BigDecimal(st)) < 0){
				System.out.println("Falto de dinero");
			} else {							
				System.out.println("");
				stack1 = BigDecimal.valueOf(st);
				us.setStack(stack1);
				if(us.getUsuario().getTmEnDeuda() != -1) {
				us.getUsuario().setTmEnDeuda(us.getUsuario().getTmEnDeuda() -1);
				System.out.println("TURNOS DEUDA SOBRANTES: " + us.getUsuario().getTmEnDeuda());
				}
			
			System.out.print("FICHAS STACK JUGADOR 2: ");
				int st2 = sc.nextInt();
				if (st2 <= 0){
					System.out.println("Apuesta invalida");
				} else if(us2.getDinero().compareTo(new BigDecimal(st2)) < 0){
					System.out.println("Falto de dinero");
				} else {							
					System.out.println("");
					stack2 = BigDecimal.valueOf(st2);
					us2.setStack(stack2);
					if(us2.getUsuario().getTmEnDeuda() != -1) {
					us2.getUsuario().setTmEnDeuda(us2.getUsuario().getTmEnDeuda() -1);
					System.out.println("TURNOS DEUDA SOBRANTES: " + us2.getUsuario().getTmEnDeuda());
					}
				}
				BigDecimal suma = stack1.add(stack2);
				stack = suma.divide(BigDecimal.TWO);
				}
		} else {
			System.out.print("FICHAS STACK: ");
			int st = sc.nextInt();
			if (st <= 0){
				System.out.println("Apuesta invalida");
			} else if(us.getDinero().compareTo(new BigDecimal(st)) < 0){
				System.out.println("Falto de dinero");
			} else {							
				System.out.println("");
				stack = BigDecimal.valueOf(st);
				us.setStack(stack);
				if(us.getUsuario().getTmEnDeuda() != -1) {
				us.getUsuario().setTmEnDeuda(us.getUsuario().getTmEnDeuda() -1);
				System.out.println("TURNOS DEUDA SOBRANTES: " + us.getUsuario().getTmEnDeuda());
				}
			}
		}
	}
	/**/
	
	public void juegodePoker() {
		Random r = new Random();

		Met.empujarMucho();
		boolean booPo;
		boolean boocaca = true;
		do {

			booPo = false;
	    	System.out.println("-------|MENU POKER|-------");
				if(multijugador) {
					if(us.getUsuario().getTmEnDeuda() != -1) {
						System.out.println(" Nombre Usuario Anfitrión " + ": "+ us.getUsuario().getNombre() + "   Dinero: " + us.getUsuario().getDinero() + "$" + "   Deuda: -" + us.getUsuario().getDeuda() + "$" + "   Tiempo Deuda: " + us.getUsuario().getTmEnDeuda() + " turnos    ");

					} else {
						System.out.println(" Nombre Usuario Anfitrión " + ": "+ us.getUsuario().getNombre() + "   Dinero: " + us.getUsuario().getDinero() + "$" + "   Usos del Banco: " + us.getUsuario().getUsoBanco()); 

					}
					if(us2.getUsuario().getTmEnDeuda() != -1) {
						System.out.println(" Nombre Usuario Invitado " + ":  "+ us2.getUsuario().getNombre() + "   Dinero: " + us2.getUsuario().getDinero() + "$" + "   Deuda: -" + us2.getUsuario().getDeuda() + "$" + "   Tiempo Deuda: " + us2.getUsuario().getTmEnDeuda() + " turnos    ");

					} else {
						System.out.println(" Nombre Usuario Invitado " + ":  "+ us2.getUsuario().getNombre() + "   Dinero: " + us2.getUsuario().getDinero() + "$" + "   Usos del Banco: " + us2.getUsuario().getUsoBanco()); 

					}	
				} else {
					if(us.getUsuario().getTmEnDeuda() != -1) {
						System.out.println(" Nombre Usuario " + ": "+ us.getUsuario().getNombre() + "   Dinero: " + us.getUsuario().getDinero() + "$" + "   Deuda: -" + us.getUsuario().getDeuda() + "$" + "   Tiempo Deuda: " + us.getUsuario().getTmEnDeuda() + " turnos    ");

					} else {
						System.out.println(" Nombre Usuario " + ": "+ us.getUsuario().getNombre() + "   Dinero: " + us.getUsuario().getDinero() + "$" + "   Usos del Banco: " + us.getUsuario().getUsoBanco()); 

					}	
				}			
				System.out.println(
						 "   -JUGAR (1)" +
						 "\n   -Salir (2)"  
						 );
				System.out.print("\nIntroduzca numero correspondiente: ");
				int resp = sc.nextInt();
				System.out.println("\n");
						switch (resp) {

						case (1):
/*		System.out.print("\nDINERO APUESTA: ");
		int apuesta = sc.nextInt();
		if (apuesta <= 0){
		System.out.println("Apuesta invalida");
		break;
		} else if(us.getDinero().compareTo(new BigDecimal(apuesta)) < 0){
		System.out.println("Falto de dinero");
		break;
		} else {
		
		System.out.println("");
		BigDecimal ap = new BigDecimal(apuesta);
		if(us.getTmEnDeuda() != -1) {
		us.setTmEnDeuda(us.getTmEnDeuda() -1);
		System.out.println("TURNOS DEUDA SOBRANTES: " + us.getTmEnDeuda());
		}*/	
		//stack.add(mediarStack(stack1, stack2));
							
							
							darStack();
							boolean partida;
							//MESA DONDE SE JUEGA (SE GENERA QUIEN ES EL PRIMERO Y LOS BOTS)
							do {
								partida = true;

								if(boocaca) {
								generarAI();
								}
								int prCiega = r.nextInt(4); //Quien empieza
								System.out.println("\n----------|NUEVA MESA|----------");
								System.out.println("OPONENTES");
								//MOSTRAR OPONENTES
								for(int i = 0; i<j.length; ++i) {
									if(j[i].getTipo().equals("ai")) {
										System.out.println("Jugador: " + j[i].gAi().getNombreAI() + "  Stack: " + j[i].gAi().getDinero() + " fichas");
										/*AQUI IRIA UN MENSAJITO SOBRE LA PERSONALIDAD*/
									}
								}
								//boolean booGuardar = true;
								boolean booRn;
								//PARTIDA SE GENERA LAS MANOS (CONTINUA CON LAS MISMAS POSICIONES Y LOS MISMOS BOT)
								do {
									booRn = true;
						
									Cartas[] CtUso = barajar(ct);
									Cartas[] Ctcentro = new Cartas[5];
									BigDecimal Ciega = new BigDecimal("0");
									BigDecimal mainPot = new BigDecimal("0");
									int topeMazo = 0;
									int ronda = 0; //Preflop
									//DAR CARTAS
									for(int i = topeMazo; i<5; ++i) {
										Ctcentro[i] = CtUso[i];	
										Ctcentro[i].setOculto(true);
										topeMazo++;
									}
									for(int i = 0; i<j.length; ++i) {
										if(j[i].getTipo().equals("us")) {
												Cartas[] c = new Cartas[2];
												c[0] = CtUso[++topeMazo];
												c[1] = CtUso[++topeMazo];
												if(multijugador) {
													c[0].setOculto(true);
													c[1].setOculto(true);
												}
												j[i].gUs().setCartas(c);
											System.out.println();
										} else if(j[i].getTipo().equals("ai")) {
												Cartas[] c = new Cartas[2];
												c[0] = CtUso[++topeMazo];
												c[0].setOculto(true);
												c[1] = CtUso[++topeMazo];
												c[1].setOculto(true);
												j[i].gAi().setCartas(c);	
										}
									}
									//GENERAR CIEGA
									if(j[prCiega].getTipo().equals("us")) {
										Ciega = j[prCiega].gUs().getStack().multiply(new BigDecimal("0.05"));
									} else if(j[prCiega].getTipo().equals("ai")) {
										Ciega = j[prCiega].gAi().getDinero().multiply(new BigDecimal("0.05"));
									}
									Ciega = Ciega.setScale(0, RoundingMode.HALF_EVEN);
									//REINICIAR BOOLE
									boolean pagarCiega = true;
									boolean pagarCiegaGrande = true;
									boolean pagarCiegaPequeña = true;
									boolean calleActiva = true;

									int potCalle = 0;
									int apuestaCalle = 0;
									for(int i = 0; i<j.length; i++) {
										j[i].setActionFoldeo(false);
										j[i].setActionAllIn(false);
										j[i].setApuesta(0);
									}
									//SE JUEGA LAS RONDAS
									do {
										System.out.println("\n"
												+ "----------------|" + liCalle[ronda] + "|----------------\n"
												+ "Pot: " + mainPot + " $\n"
												+ liCalle[ronda] +": " + Ctcentro[0].getCp() + "  " + Ctcentro[1].getCp() + "  " + Ctcentro[2].getCp() + "  " + Ctcentro[3].getCp() + "  " + Ctcentro[4].getCp() + "  \n"										
												);
										int xJug = prCiega;
										int fin = 0;
										//boolean pagarCiega = false;
										boolean checkAvairable = true;
										for(int i = 0; i<j.length; i++) {
											j[i].setActionCheckIgualar(false);
											
										}
										//boolean betAvairable = true;
										if(ronda == 0) {
											pagarCiegaGrande = true;
											pagarCiegaPequeña = true;
										}
										boolean booAp;
										//LOS JUGADORES ELIGEN LAS OPCIONES EN CADA RONDA
										do {
											booAp = true;
											if(pagarCiegaGrande == true || pagarCiegaPequeña == true) {
												pagarCiega = true;
											} else {
												pagarCiega = false;
											}
											j[xJug].setActionCheckIgualar(false);
											//TURNO JUGADOR
											if(j[xJug].getTipo().equals("us")) {
												System.out.println("--- Jugador " + j[xJug].gUs().getUsuario().getNombre() + " ---");
												System.out.println("Stack: " + j[xJug].gUs().getStack() + " fichas");
												System.out.println("POT: " + (potCalle + mainPot.intValue()) + "    (POT TOTAL " + mainPot.intValue() + " + " + "POT CALLE " + potCalle + ")" );
												System.out.println("APUESTA CALLE ACTUAL: " + apuestaCalle);
												System.out.println("APUESTA JUGADOR: " + j[xJug].getApuesta());
												int minApuesta = apuestaCalle - j[xJug].getApuesta();
												//CIEGA
												if(pagarCiega && xJug == prCiega) {
													BigDecimal smallBlind = Ciega.divide(BigDecimal.TWO).setScale(0 , RoundingMode.CEILING);
													System.out.println("CIEGA PEQUEÑA: " + smallBlind );	
													j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(smallBlind));
													potCalle += smallBlind.intValue();
													apuestaCalle = smallBlind.intValue();
													j[xJug].setApuesta(j[xJug].getApuesta() + smallBlind.intValue());
													//mainPot = mainPot.add(smallBlind);
													pagarCiegaPequeña = false;
													
													Cartas[] cUs = j[xJug].gUs().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
													checkAvairable = false;
												} else if(pagarCiega && xJug== (prCiega + 1) % j.length) {
														System.out.println("CIEGA GRANDE: " + Ciega );	
														j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(Ciega));
														potCalle += Ciega.intValue();
														apuestaCalle = Ciega.intValue();
														j[xJug].setApuesta(j[xJug].getApuesta() + Ciega.intValue());
														//mainPot = mainPot.add(Ciega);
														pagarCiegaGrande = false;
														
														Cartas[] cUs = j[xJug].gUs().getCartas();
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
														checkAvairable = false;
												} else {
													if(j[xJug].isActionFoldeo() == true) {
														Cartas[] cUs = j[xJug].gUs().getCartas();
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + " FOLDEO \n");
														
													} else if(j[xJug].isActionAllIn() == true){
														Cartas[] cUs = j[xJug].gUs().getCartas();
														if((j[0].isActionAllIn() == true || j[0].isActionFoldeo() == true) && (j[1].isActionAllIn() == true || j[1].isActionFoldeo() == true) && (j[2].isActionAllIn() == true || j[2].isActionFoldeo() == true) && (j[3].isActionAllIn() == true || j[3].isActionFoldeo() == true)) {
															cUs[0].setOculto(false);
															cUs[1].setOculto(false);	    
														}
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + " ALL IN \n");
													} else {
													
													Cartas[] cUs = j[xJug].gUs().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
													
													boolean boorespAcciones;
													do {
														//int resp1 = 0;
														boorespAcciones = false;
														System.out.println("(1) Foldear");
														if(checkAvairable) {
															System.out.println("(2) Check" + "\n(3) Apostar");
															//resp1 += 10;
														} else {
															if(minApuesta == 0) {
																System.out.println("(2) Check" + "\n(3) Subir");
															} else {
																System.out.println("(2) Igualar || " + minApuesta + " FICHAS ||" + "\n(3) Subir");
															}
														}
														if(multijugador) {
															System.out.println("(4) Ver Mano - Solo Multijugador");
															System.out.println("(5) VISOR DATOS");
															//resp1 += 20;
														} else {
															System.out.println("(4) VISOR DATOS");
														}
														System.out.print("\nIntroduzca numero correspondiente: ");
														int respUs = sc.nextInt();
														System.out.println();
														//resp1 += respUs;
													
														//optionPokerUs(resp1, xJug, cUs);
														switch(respUs) {
													    case 1: //FOLD
													    	j[xJug].setActionFoldeo(true);
													        break;
													    case 2:
													        if(checkAvairable){
													            //CHECK
													        	j[xJug].setActionCheckIgualar(true);
													        } else {
													        	//CALL IGUALAR
															/*	System.out.println("¿Nombre Introducido (" + usArray[i].getNombre() + ") es correcto?  N/S");
																String respNombre = sc.next().toUpperCase();
																	if(respNombre.equals("N") || respNombre.equals("NO")) {
																		System.out.println("REANUDAR" + "\n");
																		boo = true;
																	} else if(respNombre.equals("S") || respNombre.equals("SI")) {
																		System.out.println("NOMBRE USUARIO 1: " + usArray[i].getNombre());
																		met.limpiarConsola(); //Esta mierda solo funciona si el casino es ejecutado en CMD o la consola de Linux, solo es estetico (se puede borrar)
																		System.out.println("" +
																				"\nANALIZANDO DINERO EN LA CUENTA"
																				);*/
													        	j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(new BigDecimal(minApuesta)));
											        	        potCalle += minApuesta;
											        	        j[xJug].setApuesta(j[xJug].getApuesta() + minApuesta);
													        	j[xJug].setActionCheckIgualar(true);
											        	        checkAvairable = false;													            
													        }
													        break;
													    case 3:
													        if(checkAvairable){
													            //BET
													        	sc.nextLine();
													        	System.out.println("(Escribir ALL IN para poner TODO. Escribir 0 o menos, o SALIR para volver)");
													        	System.out.print("FICHAS APOSTAR: ");
													        	String betresp = sc.nextLine().trim().toUpperCase();

													        	
													        	if(Met.esNumero(betresp)) {
													        	    int num = Integer.parseInt(betresp);

													        	    if(num <= 0) {
													        	        boorespAcciones = true;
													        	    } else if(num < j[xJug].gUs().getStack().intValue()) {
													        	        j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(new BigDecimal(num)));
													        	        potCalle += num;
													        	        apuestaCalle = num;
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + num);
													        	        checkAvairable = false;
													        	    } else if(num == j[xJug].gUs().getStack().intValue()) {
													        	        potCalle += j[xJug].gUs().getStack().intValue();
													        	        apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	        j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	        j[xJug].setActionAllIn(true);
														        	    checkAvairable = false;
													        	    } else { 
													        	    	System.out.println("FICHAS INSUFIENTES");
														        	    boorespAcciones = true;
													        	    }
													        	} else if(betresp.equalsIgnoreCase("ALL IN")) {
													        	    potCalle += j[xJug].gUs().getStack().intValue();
												        	        apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	    j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	    j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	    j[xJug].setActionAllIn(true);
													        	    checkAvairable = false;
													        	} else if(betresp.equalsIgnoreCase("SALIR")) {
													        	    boorespAcciones = true;
													        	} else {
													        	    System.out.println("Caracter Desconocido");
													        	    boorespAcciones = true;
													        	}
													        } else {
													            //RAISE
													        	sc.nextLine();
													        	System.out.println("(Escribir ALL IN para poner TODO. Escribir la apuesta minima o menos, o SALIR para volver)");
													        	
													        	System.out.print("FICHAS APOSTAR (MIN APUESTA: " + minApuesta + "): ");
													        	String raiseResp = sc.nextLine().trim().toUpperCase();
													        	
													        	if(Met.esNumero(raiseResp)) {
													        	    int num = Integer.parseInt(raiseResp);

													        	    if(num <= minApuesta) {
													        	        boorespAcciones = true;
													        	    } else if(num < j[xJug].gUs().getStack().intValue()) {
													        	        j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(new BigDecimal(num)));
													        	        potCalle += num;
													        	        apuestaCalle = num;
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + num);
													        	        checkAvairable = false;
													        	    } else if(num == j[xJug].gUs().getStack().intValue()) {
													        	        potCalle += j[xJug].gUs().getStack().intValue();
													        	        apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	        j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	        j[xJug].setActionAllIn(true);
														        	    checkAvairable = false;
  
													        	    } else { 
													        	    	System.out.println("FICHAS INSUFIENTES");
														        	    boorespAcciones = true;
													        	    }
													        	} else if(raiseResp.equalsIgnoreCase("ALL IN")) {
													        	    potCalle += j[xJug].gUs().getStack().intValue();
													        	    apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	    j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	    j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	    j[xJug].setActionAllIn(true);
													        	    checkAvairable = false;
													        	} else if(raiseResp.equalsIgnoreCase("SALIR")) {
													        	    boorespAcciones = true;
													        	} else {
													        	    System.out.println("Caracter Desconocido");
													        	    boorespAcciones = true;
													        	}
													        }
													        System.out.println("");
													        break;
													    case 4:
													        if(multijugador){
													            //VER MANO
													        	cUs[0].setOculto(false);
													        	cUs[1].setOculto(false);
													        	System.out.println("Tu mano se va a mostrar en 2 segundo durante un segundo y medio");
													        	Met.esperarSeg(2000);
													        	System.out.print("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
													        	Met.esperarSeg(1500);
													        	System.out.println("\r");
													        	cUs[0].setOculto(true);
													        	cUs[1].setOculto(true);
													        	boorespAcciones = true;
													        } else {
													            //VISOR DATOS
													        }
													        break;
													    case 5:
													        if(multijugador){
													            //VISOR DATOS
													        }
													        break;	
													    default:											
													    	boorespAcciones = true;
													    	break;
														}
													} while (boorespAcciones);
												}
											}
											//TURNO BOT
											} else if(j[xJug].getTipo().equals("ai")) {
												System.out.println("--- Jugador " + j[xJug].gAi().getNombreAI() + " ---");
												System.out.println("Stack: " + j[xJug].gAi().getDinero() + " fichas");
												System.out.println("DESARROLLADOR TIPO AI: " + j[xJug].gAi().getTipoAI());
												//CIEGA
												if(pagarCiega && xJug == prCiega) {
													BigDecimal smallBlind = Ciega.divide(BigDecimal.TWO).setScale(0 , RoundingMode.CEILING);
													System.out.println("CIEGA PEQUEÑA: " + smallBlind );	
													j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(smallBlind));
													potCalle += smallBlind.intValue();
													apuestaCalle = smallBlind.intValue();
													j[xJug].setApuesta(j[xJug].getApuesta() + smallBlind.intValue());
													//mainPot = mainPot.add(smallBlind);
													pagarCiegaPequeña = false;
													
													Cartas[] cUs = j[xJug].gAi().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
													checkAvairable = false;
												} else if(pagarCiega && xJug == (prCiega + 1) % j.length) {
													System.out.println("CIEGA GRANDE: " + Ciega );	
													j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(Ciega));	
													potCalle += Ciega.intValue();
													apuestaCalle = Ciega.intValue();
													j[xJug].setApuesta(j[xJug].getApuesta() + Ciega.intValue());

													//mainPot = mainPot.add(Ciega);
													pagarCiegaGrande = false;
													
													Cartas[] cUs = j[xJug].gAi().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
													checkAvairable = false;
												} else {
													if(j[xJug].isActionFoldeo() == true) {
														Cartas[] cUs = j[xJug].gAi().getCartas();
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + " FOLDEO \n");
														
													} else if(j[xJug].isActionAllIn() == true){
														Cartas[] cUs = j[xJug].gAi().getCartas();
														if((j[0].isActionAllIn() == true || j[0].isActionFoldeo() == true) && (j[1].isActionAllIn() == true || j[1].isActionFoldeo() == true) && (j[2].isActionAllIn() == true || j[2].isActionFoldeo() == true) && (j[3].isActionAllIn() == true || j[3].isActionFoldeo() == true)) {
															cUs[0].setOculto(false);
															cUs[1].setOculto(false);	    
														}
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + " ALL IN \n");
													} else {
													
														Cartas[] cUs = j[xJug].gAi().getCartas();
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
													
												
														int numOp = 0;
														System.out.print("\n" + j[xJug].gAi().getNombreAI() + " esta pensado...");
														Met.esperarSeg(600);
														System.out.println("\rAUN NO HE HECHO LA TOMA DE DECICIONES DE LA AI ASI QUE VA A FOLDEAR");
														j[xJug].setActionFoldeo(true);
													}
												}
												
											}
											
											System.out.println("");
											xJug = (xJug + 1) % j.length;
											//betAvairable = false;

											//TEMPORAL QUITAR FIN Y ACTIVAR EL DE ABAJO
										boolean todosIgualaron = true;
											for(int i=0;i<j.length;i++) {
											    if(j[i].isActionFoldeo() || j[i].isActionAllIn())
											        continue;
											    // EXCEPCION CIEGA GRANDE PREFLOP
											    /*if(ronda == 0 && i == (prCiega + 1) % j.length && !(j[i].getApuesta() < apuestaCalle))
											        continue;
											    /* ESTO FALLA EN 2 COSAS (una mas pero no se si es por esto)
											     * - EL QUE PAGA LA CIEGA GRANDE SI TODOS LES IGUALA LA CIEGA EL NO PODRA SUBIRLA EN SU SIGUENTE TURNO 
											     * - CUANDO INICIA EL FLOP SI EL PRIMERO ESTA FOLD O ALL IN O SIMPLEMENTE HACE CHECK TODO EL TURNO SE SALTA
											     * - SOLO ESTA PARTE DEL CODIGO GENERA BUGS CON EL GUARDADO DE LA APUESTA (SE REINICIA A CERO)
											     * */
											    if(j[i].getApuesta() < apuestaCalle) {
											        todosIgualaron = false;
											        break;
											    }
											}
											
											++fin;
											if(fin >= j.length && todosIgualaron) {
												booAp = false;
											}
											Met.esperarSeg(450);
										} while (booAp);
									/*REINICIAR VALORES*/	
									System.out.println("Siguente ronda");
									mainPot = mainPot.add(new BigDecimal(potCalle));
									potCalle = 0;
									apuestaCalle = 0;
									for(int i = 0; i < j.length; i++) {
									    j[i].setApuesta(0);
									    if(!j[i].isActionFoldeo() && !j[i].isActionAllIn()) {
									        j[i].setActionCheckIgualar(false);
									    }
									}
									/*JUSTO AQUI SE LE DA EL DINERO AL GANADOR*/
									pagarCiega = false;
									ronda++;
									switch(ronda) {
									case(1): //Flop
										Ctcentro[0].setOculto(false);
										Ctcentro[1].setOculto(false);
										Ctcentro[2].setOculto(false);
									break;
									case(2): //Turn
										Ctcentro[3].setOculto(false);
									break;
									case(3): //River
										Ctcentro[4].setOculto(false);
									break;
									case(4):
										System.out.println("Fin de la Partida");
										System.out.println("\n"
											+ "----------------|DATOS FINALES|----------------\n"
											+ "Pot: " + mainPot + " $\n"
											+ "CARTAS CENTRO: " + Ctcentro[0].getCp() + "  " + Ctcentro[1].getCp() + "  " + Ctcentro[2].getCp() + "  " + Ctcentro[3].getCp() + "  " + Ctcentro[4].getCp() + "  \n"										
										);
										for(int i = 0; i<j.length; ++i) {
											if(j[i].getTipo().equals("us")) {
												Cartas[] cUs = j[xJug].gUs().getCartas();
												cUs[0].setOculto(false);
												cUs[1].setOculto(false);
												System.out.print("MANO DE " + j[i].gUs().getNombre() + ":  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
											} else if(j[i].getTipo().equals("ai")) {
												Cartas[] cUs = j[xJug].gAi().getCartas();
												cUs[0].setOculto(false);
												cUs[1].setOculto(false);
												System.out.print("MANO DE " + j[i].gAi().getNombreAI() + ":  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
											}
											if(j[i].isActionFoldeo()) {
												System.out.println("   FOLD");
											} else {
												System.out.println("");
											}
										}
										System.out.println("");
										System.out.println("GANADOR: "  );
										calleActiva = false;
									break;
										}
									
									} while (calleActiva);
									prCiega = (prCiega + 1) % j.length;	


								} while (booRn);
								
							} while (partida);	
							/*} else {
								//BigDecimal dnAct2 = us2.getUsuario().getDinero();
							}*/
							break;
						//}
						case (2):
							booPo = false;
						break;
						default:
							System.out.println("Caracter no encontrado");
						break;	
						}
		} while (booPo);	
	}
}