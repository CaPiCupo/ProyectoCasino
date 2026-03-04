package casino;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Random;

public class Poker {
	//JUEGO POKER MODALIDAD TEXAS HOLD'EM m
	//private Usuario us; 
	//private Usuario us2;
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
	static String[] liNombreAi = {"Cashy MacMoneyFace", "Chris MoneyMaker", "Trabajador de cuello azul", "Casado con los ahorros", "Un pensionista", "Quique", "Goku", "LeBrons", "La virgen Maria", "Rebeca Racañez", "Mario Hidalgo", "Ea Nasir", "Yakub", "Abraxas", "Manny Heffley", "Super Bigote", "John Casino", "Gru", "Shrek", "Grug Crood", "El Lorax", "Balatro Balatrez", "Dr. Gregory House", "Media Docena de Minions", "Un tipo con un sombrero guay", "Hegel", "Teto Kasane", "Dos duendes en una gabardina", "3 puros y una persona", "Dinero Dinerez", "El preterito pluscuamperfecto", "Schopenhauer", "Subaru Estrella", "Mortadelo", "Sticky Joe", "El PIB de Haiti", "Apple Jack", "Un chino", "Hideo Kojima", "Señor Pink", "Mordekaiser", "D.B. Cooper","Hornet", "Fishy ahh looking person" , "Therian de un pez gota", "THE CEO MINDSET", "Sheldon l.cooper","Kike","Quike","Kique", "Opsie! All Aces"};
	
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
	        us.setAllIn(false);
	        us.setSidePot(false);
	        us.setCartas(new Cartas[2]);
	        UsuarioPk u1 = us;
	        j[0].sUs(us);
	        j[0].sAi(null);
	        j[0].sPos(0);
			j[0].setTipo("us");
	        if (multijugador && us2 != null) {
	            us2.setConDineroAun(true);
	            us2.setValueF(us2.getUsuario().getDinero());
	            us2.setValueP(BigDecimal.ZERO);
	            us2.setCalleAct(0);
	            us2.setAllIn(false);
	            us2.setSidePot(false);
	            us2.setCartas(new Cartas[2]);
	            UsuarioPk u2 = us2;
		        j[1].sUs(us2);
		        j[1].sAi(null);
		        j[1].sPos(1);
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
				ai[i].setAllIn(false);
				ai[i].setSidePot(false);
				j[i].sAi(ai[i]);
				j[i].sUs(null);
				j[i].sPos(i);
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
				ai[i].setAllIn(false);
				ai[i].setSidePot(false);
				j[i].sAi(ai[i]);
				j[i].sUs(null);
				j[i].sPos(i);
				j[i].setTipo("ai");
			}
		}
		
	}
	public void generarCarta() {	
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
	public static char identificarValor (ArrayList<Cartas> c) {
		   
		
	}
	
	/**/
	
	public void juegodePoker() {
		Random r = new Random();

		Met.empujarMucho();
		boolean booPo;
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
						int caca = 0;
						if(multijugador) {
							System.out.print("FICHAS STACK JUGADOR 1: ");
							int st = sc.nextInt();
							if (st <= 0){
								System.out.println("Apuesta invalida");
								break;
							} else if(us.getDinero().compareTo(new BigDecimal(st)) < 0){
								System.out.println("Falto de dinero");
								break;
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
									break;
								} else if(us2.getDinero().compareTo(new BigDecimal(st2)) < 0){
									System.out.println("Falto de dinero");
									break;
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
								break;
							} else if(us.getDinero().compareTo(new BigDecimal(st)) < 0){
								System.out.println("Falto de dinero");
								break;
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
							//stack.add(mediarStack(stack1, stack2));
							boolean partida;
							do {
								partida = true;
								generarAI();
								boolean booRn;
								int prCiega = r.nextInt(4); //Quien empieza
								//boolean booGuardar = true;
								do {
									booRn = true;
									
									System.out.println("\n----------|NUEVA MESA|----------");
									System.out.println("OPONENTES");
									//MOSTRAR OPONENTES
									for(int i = 0; i<j.length; ++i) {
										if(j[i].getTipo().equals("ai")) {
											System.out.println("Jugador: " + j[i].gAi().getNombreAI() + "  Stack: " + j[i].gAi().getDinero() + " fichas");
											/*AQUI IRIA UN MENSAJITO SOBRE LA PERSONALIDAD*/
										}
									}
									Cartas[] CtUso = barajar(ct);
									Cartas[] Ctcentro = new Cartas[5];
									BigDecimal Ciega = new BigDecimal("0");
									BigDecimal Pot = new BigDecimal("0");
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
									//
									boolean calleActiva = true;
									do {
										System.out.println(ronda);
										System.out.println("\n"
												+ "----------------|" + liCalle[ronda] + "|----------------\n"
												+ "Pot: " + Pot + " $\n"
												+ liCalle[ronda] +": " + Ctcentro[0].getCp() + "  " + Ctcentro[1].getCp() + "  " + Ctcentro[2].getCp() + "  " + Ctcentro[3].getCp() + "  " + Ctcentro[4].getCp() + "  \n"										
												);
										int xJug = prCiega;
										int fin = 0;
										int apuestaCalle = 0;
										boolean pagarCiega = false;
										if(ronda == 0) {
											pagarCiega = true;
										}
										boolean booAp;
										do {
											booAp = true;
											if(j[xJug].getTipo().equals("us")) {
												System.out.println("--- Jugador " + j[xJug].gUs().getUsuario().getNombre() + " ---");
												System.out.println("Stack: " + j[xJug].gUs().getStack() + " fichas");
												//CIEGA
												if(pagarCiega && xJug == prCiega) {
													BigDecimal smallBlind = Ciega.divide(BigDecimal.TWO).setScale(0 , RoundingMode.CEILING);
													System.out.println("CIEGA PEQUEÑA: " + smallBlind );	
													j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(smallBlind));
													apuestaCalle += smallBlind.intValue();
												} else if(pagarCiega && xJug== (prCiega + 1) % j.length) {
														System.out.println("CIEGA GRANDE: " + Ciega );	
														j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(Ciega));
														apuestaCalle += Ciega.intValue();
												} 
												Cartas[] cUs = j[xJug].gUs().getCartas();
												System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
												
											} else if(j[xJug].getTipo().equals("ai")) {
												System.out.println("--- Jugador " + j[xJug].gAi().getNombreAI() + " ---");
												System.out.println("Stack: " + j[xJug].gAi().getDinero() + " fichas");
												//CIEGA
												if(pagarCiega && xJug == prCiega) {
													BigDecimal smallBlind = Ciega.divide(BigDecimal.TWO).setScale(0 , RoundingMode.CEILING);
													System.out.println("CIEGA PEQUEÑA: " + smallBlind );	
													j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(smallBlind));
													apuestaCalle += smallBlind.intValue();
												} else if(pagarCiega && xJug == (prCiega + 1) % j.length) {
													System.out.println("CIEGA GRANDE: " + Ciega );	
													j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(Ciega));	
													apuestaCalle += Ciega.intValue();
												}
												Cartas[] cUs = j[xJug].gAi().getCartas();
												System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
												
											}
											
											xJug = (xJug + 1) % j.length;
											Pot = Pot.add(new BigDecimal(apuestaCalle));
											//TEMPORAL
											++fin;
											if(fin >= j.length) {
												booAp = false;
											}
										} while (booAp);
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
										calleActiva = false;
									break;
									}
									} while (calleActiva);
									prCiega = (prCiega + 1) % j.length;
									
									++caca;
									if(caca == 10) {
										partida = false;
										booRn = false;
									}
								} while (booRn);
								
								//SOLO PARA PRUEBAS
								++caca;
								if(caca == 10) {
									partida = false;
								}
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