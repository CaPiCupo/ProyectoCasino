package casino;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * Poner modo sin ai para probar el funciones BOOCACA quitar AL ENTREGA - YA NO IMPORTA
	 * ARREGLAR CIEGA GRANDE PARA QUE PUEDA JUGAR TRAS QUE LE IGUALEN LA CIEGA - HECHO (CREO TAMBIEN)
	 * ARREGLAR EL BOOLEAN QUE ESCONDE LAS CARTAS (tambien falla en el ALL IN)
	 * SALTAR LAS TUS TURNOS SI TODOS HAN FOLDEAO O VAN ALL IN;
	 * EL CODIGO SE CAGA ENCIMA CON NUMEROS MUY MUY GRANDES:		SOLUCIONES PARA ESO:
	 *  DEBUG OBTENER APUESTA DE JUGADOR INDEX0: 1000000000			-NOSE
	 *  DEBUG OBTENER APUESTA DE JUGADOR INDEX1: 783460140			-BIGDECIMAL HASTA EN LA SOPA
	 *  DEBUG OBTENER APUESTA DE JUGADOR INDEX2: 1222395420			-PONER Y BUSCAR UN LIMITE PARA LAS APUESTAS 
	 *  DEBUG OBTENER APUESTA DE JUGADOR INDEX3: 1089540243	 
	 *  Pot Principal: -1161126736$ WHAAAA PASO DE EL INT MAX VALUE
	 *  
	 *  SI SE GENERA UN CUARTO POT (ES DECIR UNO DONDE SOLO HAY EL DINERO SOBRANTE DE UN ALL IN, ESTE DEBE DE DEVOLVER EL DINERO 
	 *  	ACTUALMENTE DA UN ERROR DE ARRAYOUTOFBOUNDS PORQUE EL METODO LE DA EL DINERO A EL QUE GANA - HECHO
	 *  HACER EL EMPATE - HECHO
	 *  CUANDO TERMINE LA PARTIDA, BUSCAR Y LOCALIZAR LOS JUGADORES CON 0 FICHAS
	 *  	SI ES UNA AI: GENERAR OTRA
	 *  	SI ES UN JUGADOR SE LE PIDE SI QUIERE MAS FICHAS; O SI NO TIENE DINERO O QUIERE SALIR, SACARLO DE LA PARTIDA
	 *  		SI MULTIJUGADOR ESTA ACTIVO Y UNO DE LOS JUGADORES NO PUEDE PAGAR:
	 *  		-SI ES EL INVITADO SE QUITA DEL POKER, SE ESTABLECE "FALSE" EN MULTIJUGADOR, Y SE REINICIA EL POKER; 
	 *  		-SI ES EL ANFITRION, AUTOMATICAMENTE SE SALE DE POKER HACIA EL MENU (O ESTE PASA A SER EL INVITADO Y SE HACE LO DEL ARRIBA)
	 *  
	 */
	private UsuarioPk us; 
	private UsuarioPk us2;
	private boolean multijugador;
	static Jugador[] j = new Jugador[4];
	static final int maxct = 52;
	static Cartas[] ct = new Cartas[maxct];
	static Scanner sc = new Scanner(System.in);
	static BigDecimal stack1;
	static BigDecimal stack2;
	static BigDecimal stack;
	static String[] liCalle = {"PREFLOP", "FLOP", "TURN", "RIVER"};
	static String[] liTipoAi = {"EarlyH2", "EarlyH1", "EarlyHB", "EarlyB1" ,"EarlyB2","TrueHB", "LateH2", "LateH1", "LateHB", "LateB1" ,"LateB2", "JCK"};
	static String[] liNombreAi = {"Cashy MacMoneyFace", "Chris MoneyMaker", "Trabajador de cuello azul", "Casado con los ahorros", "Un pensionista", "Quique", "Goku", "LeBrons", "Rebeca Racañez", "Ea Nasir", "Yakub", "Abraxas", "Manny Heffley", "Super Bigote", "John Casino", "Federico Apuestas", "Gru", "Shrek", "Grug Crood", "El Lorax", "Balatro Balatrez", "Dr. Gregory House", "Media Docena de Minions", "Un tipo con un sombrero muy guay", "Hegel", "Teto Kasane", "Dos duendes en una gabardina", "Tres puros y una persona", "Dinero Dinerez", "El preterito pluscuamperfecto", "Schopenhauer", "Subaru Estrella", "Mortadelo", "Sticky Joe", "El PIB de Haiti", "Apple Jack", "Un chino", "Hideo Kojima", "Señor Pink", "Mordekaiser", "D.B. Cooper","Hornet", "Therian de un pez gota", "THE CEO MINDSET", "Sheldon l.cooper", "Kike", "Quike", "Kique", "Opsie! All Aces", "Rigoberto Faroles" , "Un Fiat multipla", "Margaret Thatcher", "La Ciudad de Birmingham", "Bebe con hidrocefalia", "Quique una vez mas", "Martin Allin", "El Wario de Quique, Duidue", "Uno del Lepe", "Mayonesa que cobro vida", "Dr.Holocaust", "El Sozio", "Don Chorbo Galaxia", "MENA con la paga minima", "É um macaco"};
	
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
          		//ct[i].setValor(BigDecimal.valueOf(0.70));
        } else if(cnm == 10) {
  			ct[i].setNumero("T");
      		ct[i].setValor(BigDecimal.valueOf(0.55));
      		//ct[i].setValor(BigDecimal.valueOf(0.50));
        } else if(cnm == 11) {
  			ct[i].setNumero("J");
      		ct[i].setValor(BigDecimal.valueOf(0.65));
      		//ct[i].setValor(BigDecimal.valueOf(0.55));
        } else if(cnm == 12) {
			ct[i].setNumero("Q");
      		ct[i].setValor(BigDecimal.valueOf(0.75));
      		//ct[i].setValor(BigDecimal.valueOf(0.60));
        } else if(cnm == 13) {
			ct[i].setNumero("K");
      		ct[i].setValor(BigDecimal.valueOf(0.85));
      		//ct[i].setValor(BigDecimal.valueOf(0.65));
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
	
	/*public int calcularGanadorEntre(List<Integer> jugadores, Cartas[] ctCentro){
		//TODO EL EMPATE
	    int xJugMejor = jugadores.get(0);
	    int jugsEmp = -1;

	    for(int i : jugadores){
	    	
	    	if(xJugMejor != i) {
	    		int cacaMu = esIGanador(i, xJugMejor, ctCentro);	    	
	    		if(cacaMu== 1){
	    			xJugMejor = i;
	    			jugsEmp = -1;
	    		} else if(cacaMu == 2) { //EMPARTE ENTRE DOS 
	    			if(jugsEmp >= 10) {
	    				if(jugsEmp >= 100) {
	    						jugsEmp += xJugMejor*1000;
	    				} else {
	    					jugsEmp += xJugMejor*100;
	    				}
	    			} else {
	    				jugsEmp= i + xJugMejor*10;
	    			}
	    			xJugMejor = i;	
	    		}
	    	} else {
	    		xJugMejor = i;
	    		System.out.println("DEBUG ERAN EL MISMO JUGADOR");
	    	}
	    		
        	System.out.println("DEBUG JUGADOR INDICE I: " + i);
        	System.out.println("DEBUG JUGADOR MEJOR ACTUAL: " + xJugMejor);
        	System.out.println("DEBUG NUMERO EMPATE: " + jugsEmp);
	    }

	    if(jugsEmp != -1) {
	    	System.out.println("DEBUG ERROR HUBO EMPATE");
	    	return jugsEmp;
	    }
	    return xJugMejor;
	}*/
	//TODO TODO TODO TODO TODO
	public List<Integer> calcularGanadoresEntre(List<Integer> jugadores, Cartas[] ctCentro) {

	    List<Integer> ganadores = new ArrayList<>();
	    int xJugMejor = jugadores.get(0);
	    ganadores.add(xJugMejor);

	    for(int i : jugadores) {
	    	
	    	if(xJugMejor != i) { 
	        	int comp = esIGanador(i, xJugMejor, ctCentro);
	        	if(comp == 1) { 
	        		xJugMejor = i;
	        		ganadores.clear();
	        		ganadores.add(i);
	        	} else if(comp == 2) { 
	            //PARA EL EMPATE
	        		ganadores.add(i);
	        	}
	    	} else {
	    		xJugMejor = i;
	    		
	    	}
	    }

	    return ganadores;
	}
	
	public int esIGanador(int indexNuevo, int indexGanador, Cartas[] ctCentro) {
		long loNuevo = identificarMano(indexNuevo, ctCentro);
		long loGanViejo = identificarMano(indexGanador, ctCentro);
		
		if(loNuevo > loGanViejo) {
			//TODO EMPATE REPARTIR EL POT CORRESPONDIENTE POR LA MITAD
			return 1;
		} else if(loNuevo == loGanViejo){
			return 2;
		}
		return 0;
	}
	
	public long identificarMano(int xJug, Cartas[] ctCentro) {
		Cartas[] ctJug;
		if(j[xJug].getTipo().equals("us")) {
			ctJug = j[xJug].gUs().getCartas();
		} else {
			ctJug = j[xJug].gAi().getCartas();
		}
		Cartas[] ctTotal = new Cartas[7];
		int idxTotal = 0;
		for(Cartas c : ctCentro) {
			ctTotal[idxTotal++] = c;
		}
		for(Cartas c : ctJug) {
			ctTotal[idxTotal++] = c;
		}

		long valorMano = 0L;
		int parejas = 0;
		boolean trio = false;
		boolean escalera = false;
		boolean color = false;
		boolean poker = false;
		boolean escaleraColor = false;
		//TODO
		/* SI VOY A COMPARAR UN INT IMPORTANTE QUE EL VALOR DE LAS CARTAS NO PUEDAN SUMAR HASTA LLEGAR A OTRA MANO
		 * EJEMPLO: SI ES PAR mano += 1000; que sean los millares lo que marquen la mano; (9000 desde la escarera de color a 0000 de la carta unica)
		 * MARIO POR CIERTO, LA DOBLE PAREJA SE BASA EN LA PAREJA CON EL VALOR MAYOR, Y SI SON IGUALES PUES EN LA PAREJA CON MENOR VALOR; LOS MISMO CON EL FULL HOUSE.
		 *
		 * REGLAS PARA DESEMPATAR: SE CUENTAN LAS 5 CARTAS MAS ALTAS
		 * Parejas 			3 Kickers
		 * Doble Pareja		1 Kickers
		 * Trio 			2 kickers
		 * Escalera  		Carta Mas Alta
		 * Color 			Entre las 5 cartas del color, la mas alta
		 * Full House 		Valor de Trio Mas Alto o valor de la pareja mas alta, o si hay dos trios, el trio mas alto
		 * Poker			1 Kicker
		 * */
		
		//IDENTIFICAR NUMERO DE PAREJAS: STRING = EL NUMERO; INTEGER = VECES QUE ESTE APARECE
		//	HashMap<KEYS, VALUE>
		HashMap<String, Integer> numIguales = new HashMap<String, Integer>();
		for(Cartas c : ctTotal) {
			String valor = c.getNumero();
			numIguales.put(valor, numIguales.getOrDefault(valor, 0) + 1);
		}

		List<Integer> valorPareja = new ArrayList<>();
		List<Integer> valorTrio = new ArrayList<>();
		List<Integer> valorPoker = new ArrayList<>();
		List<Integer> valorCartasSuelta = new ArrayList<>();
		
		for(Map.Entry<String, Integer> entrada : numIguales.entrySet()) {
			if(entrada.getValue() == 4) {
				valorPoker.add(getValorRealCarta(entrada.getKey()));
				poker = true;
				if(parejas > 0) {
					valorCartasSuelta.add(valorPareja.get(0));
					if(parejas == 2) {
						valorCartasSuelta.add(valorPareja.get(1));
					}
						
				}
			}
			if(entrada.getValue() == 3) {
				valorTrio.add(getValorRealCarta(entrada.getKey()));
				trio = true;
			}
			if(entrada.getValue() == 2) {
				valorPareja.add(getValorRealCarta(entrada.getKey()));
				parejas++;
			}
			if(entrada.getValue() == 1 || ((entrada.getValue() == 2) && poker == true)) {
				valorCartasSuelta.add(getValorRealCarta(entrada.getKey()));
			}
		}

		//IDENTIFICAR ESCALERA:
		HashMap<Integer, Integer> numValorEscalera = new HashMap<Integer, Integer>();
		for(Cartas c : ctTotal) {
			int valorReal = getValorRealCarta(c.getNumero());
			numValorEscalera.put(valorReal, numValorEscalera.getOrDefault(valorReal, 0) + 1);
		}
		List<Integer> val = new ArrayList<>(numValorEscalera.keySet());
		Collections.sort(val);
		
		int cons = 1;
		int maxCons = 1;
		int ctMaxEscalera = 0;
		for(int i = 1; i<val.size(); i++) {
			if(val.get(i) == val.get(i -1) +1) {
				cons++;
				maxCons = Math.max(maxCons, cons);
				if(maxCons >= 5) {
					escalera = true;
					ctMaxEscalera = val.get(i);
				}				
			} else {
				cons = 1;
			}
		}

		//DE ALGUNA MANERA DEBO DE ENCONTRAR QUE A2345 SEA ESCALERA
		List<Integer> expAceTo5li = List.of(14, 2, 3, 4, 5);
		if(val.containsAll(expAceTo5li)) {
			escalera = true;
			ctMaxEscalera = 5;
		}

		//IDENTIFICAR COLOR:
		HashMap<String, Integer> colorIgual = new HashMap<String, Integer>();
		for(Cartas c : ctTotal) {
			String colorA = c.getColor();
			colorIgual.put(colorA, colorIgual.getOrDefault(colorA, 0) + 1);
			
		}
		
		List<Integer> valorColor = new ArrayList<>();
		String colorPalo = null;
		for(Map.Entry<String, Integer> entrada: colorIgual.entrySet()) {
			if(entrada.getValue() >= 5) {
				colorPalo = entrada.getKey();
				color = true;
				break;
			}
		}
		
		if(colorPalo != null) {
			for(Cartas c : ctTotal) {
				if(c.getColor().equals(colorPalo)) {
					valorColor.add(getValorRealCarta(c.getNumero()));
				}
			}
		}
		//IDENTIFICAR ESCALERA DE COLOR:

		Collections.sort(valorColor);
		
		int consC = 1;
		int maxConsC = 1;
		int ctMaxEscaleraC = 0;
		for(int i = 1; i<valorColor.size(); i++) {
			if(valorColor.get(i) == valorColor.get(i -1) +1) {
				consC++;
				maxConsC = Math.max(maxConsC, consC);
				if(maxConsC >= 5) {
					escaleraColor = true;
					ctMaxEscaleraC = valorColor.get(i);
				}				
			} else {
				consC = 1;
			}
		}

		//DE ALGUNA MANERA DEBO DE ENCONTRAR QUE A2345 SEA ESCALERA
		List<Integer> expAceTo5liC = List.of(14, 2, 3, 4, 5);
		if(valorColor.containsAll(expAceTo5liC)) {
			escaleraColor = true;
			ctMaxEscaleraC = 5;
		}
		Collections.sort(valorCartasSuelta, Collections.reverseOrder()); //REVERSE ORDER PORQUE MUCHAS VECES SOLO QUIERO EL VALOR MAS ALTO
		Collections.sort(valorPareja, Collections.reverseOrder());
		Collections.sort(valorTrio, Collections.reverseOrder());
		Collections.sort(valorPoker, Collections.reverseOrder());
		Collections.sort(valorColor, Collections.reverseOrder());
		//PARA LA ESCALERA NO HAY UNA LISTA SOLO UN INT CON SU VALOR MAXIMO
		
		//DAR VALORES DE LA MANOS (EN MILLARES+1 9000000000-0000000000) Y EL VALOR DE LAS CARTAS PARA DIFERENCIAR ENTRE ELLAS
		//ESCALERA DE COLOR	
		if(escaleraColor) {		
			long escColor = 900000000000L;
			escColor += ctMaxEscaleraC*1000000; //1000000 porque si es entre (14-10) se me sumaria al 9000000
			valorMano = escColor;
			System.out.println("DEBUG MANO ESCALERA: " + valorMano);
		} else if(poker == true &&!(valorPoker.isEmpty())) {
			long esPoker = 80000000000L;
			esPoker += valorPoker.get(0)*100000000;
			esPoker += valorCartasSuelta.get(0)*1000000;
			valorMano = esPoker;
			System.out.println("DEBUG MANO POKER: " + valorMano);

			//FULL HOUSE
		} else if(trio == true && parejas > 0) {
			long esFullHouse = 70000000000L;
			if(valorTrio.size() == 2) {
				esFullHouse += valorTrio.get(0)*100000000;
				esFullHouse += valorTrio.get(1)*1000000;
			} else {
				esFullHouse += valorTrio.get(0)*100000000;
				esFullHouse += valorPareja.get(0)*1000000;
			}
			
			valorMano = esFullHouse;
			System.out.println("DEBUG MANO FULL HOUSE: " + valorMano);
		} else if(color == true &&!(valorColor.isEmpty())) {
						  long esColor = 60000000000L;
			esColor += valorColor.get(0)*100000000;
			esColor += valorColor.get(1)*1000000;
			esColor += valorColor.get(2)*10000;
			esColor += valorColor.get(3)*100;
			esColor += valorColor.get(4);
			valorMano = esColor;
		} else if(escalera == true && ctMaxEscalera != 0) {
			long esCalera = 50000000000L;
			esCalera += ctMaxEscalera*1000000;
			valorMano = esCalera;
			System.out.println("DEBUG MANO ESCALERA: " + valorMano);
		} else if(trio == true &&!(valorTrio.isEmpty())) {
			long esTrio = 40000000000L;
			esTrio += valorTrio.get(0)*100000000;
			esTrio += valorCartasSuelta.get(0)*1000000;
			esTrio += valorCartasSuelta.get(1)*10000;
			valorMano = esTrio;
			//DOBLE PAREJA
			System.out.println("DEBUG MANO TRIO: " + valorMano);
		} else if(parejas == 2 &&!(valorPareja.isEmpty())) {
			long esDoblePareja = 30000000000L;
			esDoblePareja += valorPareja.get(0)*100000000;
			esDoblePareja += valorPareja.get(1)*1000000;
			esDoblePareja += valorCartasSuelta.get(0)*10000;
			valorMano = esDoblePareja;
			System.out.println("DEBUG MANO DOBLEPAREJA: " + valorMano);
		} else if(parejas == 1 &&!(valorPareja.isEmpty())) {
			long esPareja = 20000000000L;
			esPareja += valorPareja.get(0)*100000000;
			esPareja += valorCartasSuelta.get(0)*1000000;
			esPareja += valorCartasSuelta.get(1)*10000;
			esPareja += valorCartasSuelta.get(2)*100;
			valorMano = esPareja;
			//CARTAS SUELTA
			System.out.println("DEBUG MANO PAREJA: " + valorMano);
		} else {
			long ctSueltas = valorCartasSuelta.get(0)*100000000 + valorCartasSuelta.get(1)*1000000 + valorCartasSuelta.get(2)*10000 + valorCartasSuelta.get(3)*100 + valorCartasSuelta.get(4);
			valorMano = ctSueltas;
			System.out.println("DEBUG MANO CARTASSUELTAS: " + valorMano);
		}
		
		
		return valorMano;
	}
	
	public int getValorRealCarta(String num) {
		
		int x = 0;
		switch(num) {
		case ("A"): 
			x = 14;
		break;
		case("K"): 
			x = 13;
		break;
		case("Q"):
			x = 12;
		break;
		case("J"): 
			x = 11;
		break;
		case("T"):
			x = 10;
		break;
		case("9"):
			x = 9;
		break;
		case("8"):
			x = 8;
		break;
		case("7"):
			x = 7;
		break;
		case("6"):
			x = 6;
		break;
		case("5"):
			x = 5;
		break;
		case("4"):
			x = 4;
		break;
		case("3"):
			x = 3;
		break;
		case("2"):
			x = 2;
		break;
		}
		
		return x;
	}
	/**
	 * Hace que funcione esto
	 * @return
	 */
	public List<Pot> calcularPots() {
		/*TODO HECHO
		 * HAY UN PROBLEMA EN LAS APUESTAS EN EL ADD 
		 * DEBUG 100% PROBLEMA EN EL  if(diferencia.compareTo(BigDecimal.ZERO) > 0
			DEBUG NIVEL ACTUAL 0 ==> TIENE QUE SER AQUI EL PROBLEMA
			DEBUG NIVEL ANTERIOR 0
			DEBUG VALOR DIFERENCIA 0
			DEBUG tamaño pots: 0
		 */
	    List<BigDecimal> apuestas = new ArrayList<>();
	    
	    for(int i = 0; i < j.length; i++){
	      //  if(!j[i].isActionFoldeo()){ //PORQUE???? MARIO DEL PASADO PAYASO
	            apuestas.add(new BigDecimal(j[i].getApuesta()));
	           // System.out.println("DEBUG APUESTA DEL JUGADOR " + i + " :" + j[i].getApuesta());
	        //} 
	    }

	    Collections.sort(apuestas);

	    List<Pot> pots = new ArrayList<>();

	    BigDecimal nivelAnterior = BigDecimal.ZERO;

	    for(int i = 0; i < apuestas.size(); i++){

	        BigDecimal nivelActual = apuestas.get(i);
	        BigDecimal diferencia = nivelActual.subtract(nivelAnterior);
	        
	        
	        //TODO Esto creo que es lo que esta dando problemas Edit# Si, si que lo es
	        if(diferencia.compareTo(BigDecimal.ZERO) > 0){

	            List<Integer> jugadoresPot = new ArrayList<>();
	            int jugSinFold = 0;
	        	//System.out.println("DEBUG 0% PROBLEMA EN EL if(diferencia.compareTo(BigDecimal.ZERO) > 0");
	            /**TODO
	             * QUIZA ESTO && new BigDecimal(j[k].getApuesta()).compareTo(nivelActual) >= 0 SEA UNA MALA IDEA 
	             * LOS POTS DEBEN DE FUNCIONAR ASI
	             * PACO: 10 ALL IN; PABLO (GANA): 20 ALL IN; PAULO: 50 ALL IN; POL: 50 IGUALA
	             * PACO -10
	             * PABLO: SOLO GANA EL POT1 Y EL POT 2: +40 POT 1 Y MAS +60 POT 2 (TOTAL 100)
	             * PAULO SOLO PIERDE EL POT 1 Y 2:  -20 Y GANA DEVUELTA LOS +30
	             * POL LO MISMO QUE PAULO: -2O (SE QUEDA CON 30)
	             *
	             */
	            for(int k = 0; k < j.length; k++){
	            	if(new BigDecimal(j[k].getApuesta()).compareTo(nivelActual) >= 0) {
	            		jugSinFold++;
	            	}
	                if(!j[k].isActionFoldeo() && new BigDecimal(j[k].getApuesta()).compareTo(nivelActual) >= 0){
	                    jugadoresPot.add(k);
	                }
	            }
	            BigDecimal pot = diferencia.multiply(new BigDecimal(jugSinFold));
	            pots.add(new Pot(pot, jugadoresPot));
	        } else {
	        	/*System.out.println("DEBUG 100% PROBLEMA EN EL  if(diferencia.compareTo(BigDecimal.ZERO) > 0");
	        	System.out.println("DEBUG NIVEL ACTUAL" + nivelActual);
	        	System.out.println("DEBUG NIVEL ANTERIOR " + nivelAnterior);
	        	System.out.println("DEBUG VALOR DIFERENCIA " + diferencia);*/
	        }

	        nivelAnterior = nivelActual;
	    }

	    return pots;
	}
	
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


								generarAI();
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
										//TODO ESTOY LOCO? O Cartas[] c = new Cartas[2]; Y LUEGO j[i].gUs().setCartas(c); NO DEBERIA DE DARTE CARTAS DEL CtUso?
										if(j[i].getTipo().equals("us")) {
												Cartas[] c = new Cartas[2];
												c[0] = CtUso[++topeMazo];
												c[1] = CtUso[++topeMazo];
												if(multijugador) {
													c[0].setOculto(true);
													c[1].setOculto(true);
												}
												j[i].gUs().setCartas(c);
												//TODO El metodo de creacion de pots 
												j[i].setApuesta(0);
											System.out.println();
										} else if(j[i].getTipo().equals("ai")) {
												Cartas[] c = new Cartas[2];
												c[0] = CtUso[++topeMazo];
												c[0].setOculto(true);
												c[1] = CtUso[++topeMazo];
												c[1].setOculto(true);
												j[i].gAi().setCartas(c);
												j[i].setApuesta(0);
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
										j[i].setActionPagoCiegaGrande(false);
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
													
													if(smallBlind.compareTo(j[xJug].gUs().getStack()) >= 0) {
														System.out.println("ALL IN a falta de no poder pagar la ciega");
														smallBlind = j[xJug].gUs().getStack();
														j[xJug].gUs().setStack(BigDecimal.ZERO);
														j[xJug].setActionAllIn(true);
													} else {
														j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(smallBlind));
													}
													potCalle += smallBlind.intValue();
													if(apuestaCalle < smallBlind.intValue()) {
													apuestaCalle = smallBlind.intValue();
													}
													j[xJug].setApuesta(j[xJug].getApuesta() + smallBlind.intValue());
													//mainPot = mainPot.add(smallBlind);
													pagarCiegaPequeña = false;
													
													Cartas[] cUs = j[xJug].gUs().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
													checkAvairable = false;
												} else if(pagarCiega && xJug== (prCiega + 1) % j.length) {
														System.out.println("CIEGA GRANDE: " + Ciega );	
														
														if(Ciega.compareTo(j[xJug].gUs().getStack()) >= 0) {
															System.out.println("ALL IN a falta de no poder pagar la ciega");
															Ciega = j[xJug].gUs().getStack();
															j[xJug].gUs().setStack(BigDecimal.ZERO);
															j[xJug].setActionAllIn(true);
														} else {
															j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(Ciega));
														}
														potCalle += Ciega.intValue();
									        	        if(apuestaCalle < Ciega.intValue()) {
									        	        	apuestaCalle = Ciega.intValue();
									        	        }
									        	        j[xJug].setApuesta(j[xJug].getApuesta() + Ciega.intValue());
														j[xJug].setActionPagoCiegaGrande(true);
														//mainPot = mainPot.add(Ciega);
														pagarCiegaGrande = false;
														
														Cartas[] cUs = j[xJug].gUs().getCartas();
														System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp() + "\n");
														checkAvairable = false;
														/*if(Ciega.compareTo(j[xJug].gAi().getDinero()) >= 0) {
														System.out.println("ALL IN a falta de no poder pagar la ciega");
														Ciega = j[xJug].gAi().getDinero();
									        	        j[xJug].gAi().setDinero(BigDecimal.ZERO);
									        	        j[xJug].setActionAllIn(true);
													} else {
														j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(Ciega));	
													}	
														potCalle += Ciega.intValue();
									        	        if(apuestaCalle < Ciega.intValue()) {
									        	        	apuestaCalle = Ciega.intValue();
									        	        }
														j[xJug].setApuesta(j[xJug].getApuesta() + Ciega.intValue());
														j[xJug].setActionPagoCiegaGrande(true);*/
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
															} else if(minApuesta >= j[xJug].gUs().getStack().intValue()) {
																System.out.println("(2) Igualar || ALL IN ||");																
															}else {
																System.out.println("(2) Igualar || " + minApuesta + " FICHAS ||" + "\n(3) Subir");
															}
														}
														if(multijugador) {
															if(minApuesta >= j[xJug].gUs().getStack().intValue()) {
																System.out.println("(3) Ver Mano - Solo Multijugador");
																System.out.println("(4) VISOR DATOS");														
															} else {
															System.out.println("(4) Ver Mano - Solo Multijugador");
															System.out.println("(5) VISOR DATOS");
															//resp1 += 20;
															}
														} else {
															if(minApuesta >= j[xJug].gUs().getStack().intValue()) {
																System.out.println("(3) VISOR DATOS");														
															} else {
																System.out.println("(4) VISOR DATOS");
															}
														}
														System.out.print("\nIntroduzca numero correspondiente: ");
														int respUs = sc.nextInt();
														System.out.println();
														
														if(minApuesta >= j[xJug].gUs().getStack().intValue()) {
															respUs += 10;
														}
														//resp1 += respUs;
													
														//optionPokerUs(resp1, xJug, cUs);
														switch(respUs) {
													    case 1, 11: //FOLD
													    	j[xJug].setActionFoldeo(true);
													        break;
													    case 2, 12:
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
																if(minApuesta >= j[xJug].gUs().getStack().intValue()) {
													        	    potCalle += j[xJug].gUs().getStack().intValue();
												        	        apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	    j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	    j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	    j[xJug].setActionAllIn(true);
													        	    checkAvairable = false;
																} else {	
																	j[xJug].gUs().setStack(j[xJug].gUs().getStack().subtract(new BigDecimal(minApuesta)));
												        	        potCalle += minApuesta;
												        	        j[xJug].setApuesta(j[xJug].getApuesta() + minApuesta);
												        	        j[xJug].setActionCheckIgualar(true);
												        	        checkAvairable = false;			
																}
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
														        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
														        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
														        	    }
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + num);
													        	        checkAvairable = false;
													        	        fin--;
													        	    } else if(num == j[xJug].gUs().getStack().intValue()) {
													        	        potCalle += j[xJug].gUs().getStack().intValue();
														        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
														        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
														        	    }
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	        j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	        j[xJug].setActionAllIn(true);
														        	    checkAvairable = false;
														        	    fin--;
													        	    } else { 
													        	    	System.out.println("FICHAS INSUFIENTES");
														        	    boorespAcciones = true;
													        	    }
													        	} else if(betresp.equalsIgnoreCase("ALL IN")) {
													        	    potCalle += j[xJug].gUs().getStack().intValue();
													        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
													        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	    }
													        	    j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	    j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	    j[xJug].setActionAllIn(true);
													        	    checkAvairable = false;
													        	    fin--;
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
														        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
														        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
														        	    }
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + num);
													        	        checkAvairable = false;
													        	        fin--;
													        	    } else if(num == j[xJug].gUs().getStack().intValue()) {
													        	        potCalle += j[xJug].gUs().getStack().intValue();
														        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
														        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
														        	    }
													        	        j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	        j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	        j[xJug].setActionAllIn(true);
														        	    checkAvairable = false;
														        	    fin--;
  
													        	    } else { 
													        	    	System.out.println("FICHAS INSUFIENTES");
														        	    boorespAcciones = true;
													        	    }
													        	} else if(raiseResp.equalsIgnoreCase("ALL IN")) {
													        	    potCalle += j[xJug].gUs().getStack().intValue();
													        	    if(apuestaCalle < j[xJug].gUs().getStack().intValue()) {
													        	    	apuestaCalle = j[xJug].gUs().getStack().intValue();
													        	    }
													        	    j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gUs().getStack().intValue());
													        	    j[xJug].gUs().setStack(BigDecimal.ZERO);
													        	    j[xJug].setActionAllIn(true);
													        	    checkAvairable = false;
													        	    fin--;
													        	} else if(raiseResp.equalsIgnoreCase("SALIR")) {
													        	    boorespAcciones = true;
													        	} else {
													        	    System.out.println("Caracter Desconocido");
													        	    boorespAcciones = true;
													        	}
													        }
													        System.out.println("");
													        break;
													    case 4, 13:
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
													    case 5, 14:
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
													
													if(smallBlind.compareTo(j[xJug].gAi().getDinero()) >= 0) {
														System.out.println("ALL IN a falta de no poder pagar la ciega");
														smallBlind = j[xJug].gAi().getDinero();
														j[xJug].gAi().setDinero(BigDecimal.ZERO);
														j[xJug].setActionAllIn(true);
													} else {
														j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(smallBlind));
													}
													potCalle += smallBlind.intValue();
													if(apuestaCalle < smallBlind.intValue()) {
														apuestaCalle = smallBlind.intValue();
													}
													j[xJug].setApuesta(j[xJug].getApuesta() + smallBlind.intValue());
													
													//mainPot = mainPot.add(smallBlind);
													pagarCiegaPequeña = false;
													
													Cartas[] cUs = j[xJug].gAi().getCartas();
													System.out.println("MANO:  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
													checkAvairable = false;
												} else if(pagarCiega && xJug == (prCiega + 1) % j.length) {
													System.out.println("CIEGA GRANDE: " + Ciega );	
													
													//TODO HACER QUE ESTE MODELO DE CIEGA ESTE EN TODAS LAS CIEGAS
													if(Ciega.compareTo(j[xJug].gAi().getDinero()) >= 0) {
														System.out.println("ALL IN a falta de no poder pagar la ciega");
														Ciega = j[xJug].gAi().getDinero();
									        	        j[xJug].gAi().setDinero(BigDecimal.ZERO);
									        	        j[xJug].setActionAllIn(true);
													} else {
														j[xJug].gAi().setDinero(j[xJug].gAi().getDinero().subtract(Ciega));	
													}	
														potCalle += Ciega.intValue();
									        	        if(apuestaCalle < Ciega.intValue()) {
									        	        	apuestaCalle = Ciega.intValue();
									        	        }
														j[xJug].setApuesta(j[xJug].getApuesta() + Ciega.intValue());
														j[xJug].setActionPagoCiegaGrande(true);
													
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
														Met.esperarSeg(1500);
														System.out.println("\rAUN NO HE HECHO LA TOMA DE DECISIONES DE LA AI ASI QUE VA IR ALL IN QUE TE JODAN");
														//j[xJug].setActionFoldeo(true);
									        	        potCalle += j[xJug].gAi().getDinero().intValue();
									        	        if(apuestaCalle < j[xJug].gAi().getDinero().intValue()) {
									        	        	apuestaCalle = j[xJug].gAi().getDinero().intValue();
									        	        }
									        	        j[xJug].setApuesta(j[xJug].getApuesta() + j[xJug].gAi().getDinero().intValue());
									        	        j[xJug].gAi().setDinero(BigDecimal.ZERO);
									        	        j[xJug].setActionAllIn(true);
										        	    checkAvairable = false;
										        	    fin--;
														
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
											     * HECHO YA VA BIEN
											     * TODO TODO TODO TODO TODO TODO TODO TODO
											     * VUELVE A IR TODOO MAL LA CIEGA Y TE VUELVE A SALTAR SI TE LA IGUALARON O TE FOLDEARON Y LA APUESTA JUGADOR SALE 0 OTRA VEZ
											     * VALE HECHO BIEN DE NUEVO
											     * */
											    if(j[i].isActionPagoCiegaGrande() == true) {
											    	j[i].setActionPagoCiegaGrande(false);
											    	todosIgualaron = false;
											    	if(apuestaCalle == Ciega.intValue() && prCiega == xJug) {
											    		checkAvairable = true;
											    	}
											    	break;
											    }
											    if(j[i].getApuesta() < apuestaCalle) {
											        todosIgualaron = false;
											        break;
											    }
											}
											
											//SE ASEGURA QUE AL MENOS LOS 4 JUGADORES JUGARON EL TURNO
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
										System.out.println("DEBUG OBTENER APUESTA DE JUGADOR INDEX" + i + ": " +  j[i].getApuesta());
									    //j[i].setApuesta(0);
									    if(!j[i].isActionFoldeo() && !j[i].isActionAllIn()) {
									        j[i].setActionCheckIgualar(false);
									    }
									}
									/*JUSTO AQUI SE LE DA EL DINERO AL GANADOR*/
									pagarCiega = false;
									ronda++;
									//TODO SI TODOS LOS JUGADOS MENOS UNO HAN FOLDEADO 
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
										
									System.out.println("DEBUG ANTES DE CALL POTS APUESTA TOTAL: " +j[xJug].getApuesta());
										List<Pot> pots = calcularPots();
										
										System.out.println("DEBUG tamaño pots: " + pots.size());
										//TODO LOS POTS NO SE ESTAN FORMANDO BIEN, SALE QUE POTS SIZE VALE CERO - HECHO
										/* TODO MIRA ESTO MARIO
											DEBUG OBTENER APUESTA DE JUGADOR INDEX0: 5
											DEBUG OBTENER APUESTA DE JUGADOR INDEX1: 3
											DEBUG OBTENER APUESTA DE JUGADOR INDEX2: 5
											DEBUG OBTENER APUESTA DE JUGADOR INDEX3: 0
											Fin de la Partida
											DEBUG ANTES DE CALL POTS APUESTA TOTAL: 3
											DEBUG APUESTA DEL JUGADOR 0 :5  =========================================> SOLO ESTA COJIENDO EL DEL QUE NO HA FOLDEADO (INDEX 0: 5)
											DEBUG 0% PROBLEMA EN EL if(diferencia.compareTo(BigDecimal.ZERO) > 0
											DEBUG tamaño pots: 1										 * 					
										 * */
										System.out.println("\n----------------|DATOS FINALES|----------------\n");
										
										for(int i = 0; i<pots.size(); ++i) {
											Pot pot = pots.get(i);

											if(pot.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
												continue;
											}
											
											String nmPot;
											switch(i) {
											case(0): 
												nmPot = "Pot Principal: " + pot.getCantidad().intValue() + "$";
											break;
											case(1):
												nmPot = "Pot Segundario: " + pot.getCantidad().intValue() + "$";
											break;
											case(2):
												nmPot = "Pot Terciario: " + pot.getCantidad().intValue() + "$";
											break;
											case(3):
												nmPot = "Pot Cuartenario: " + pot.getCantidad().intValue() + "$";
											break;
											default:
												nmPot = "Pot Numero " + i + ": " + pot.getCantidad().intValue() + "$";  ;
											}
											
											System.out.println(nmPot);
											
											System.out.print("Participan: ");
											boolean coma = false;
											for(int k : pot.getJugador()) {
												if(coma) {
													System.out.print(", ");
												}
												if(j[k].getTipo().equals("us")) {
													System.out.print( j[k].gUs().getNombre());
												} else {
													System.out.print( j[k].gAi().getNombreAI());
												}
												coma = true;
											}
											System.out.println("");
											
										}
											System.out.println("\nCARTAS CENTRO: " + Ctcentro[0].getCp() + "  " + Ctcentro[1].getCp() + "  " + Ctcentro[2].getCp() + "  " + Ctcentro[3].getCp() + "  " + Ctcentro[4].getCp() + "  \n");
									
										for(int i = 0; i<j.length; ++i) {
											if(j[i].getTipo().equals("us")) {
												Cartas[] cUs = j[i].gUs().getCartas();
												cUs[0].setOculto(false);
												cUs[1].setOculto(false);
												System.out.print("MANO DE " + j[i].gUs().getNombre() + ":  "  + cUs[0].getCp() + "  " + cUs[1].getCp());
											} else if(j[i].getTipo().equals("ai")) {
												Cartas[] cUs = j[i].gAi().getCartas();
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

										//Repartir pots
										System.out.println("------!REPARTO DE LOS POTS|------");
										for(int i = 0; i<pots.size(); ++i) {
											Pot pot = pots.get(i);

											if(pot.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
												continue;
											}
											
											//int x = calcularGanadorEntre(pot.getJugador(), Ctcentro);
											List<Integer> liGanador = calcularGanadoresEntre(pot.getJugador(), Ctcentro);
											switch(i) {
											case(0):
												System.out.print("Ganador del Pot Principal: ");
											break;
											case(1):
												System.out.print("Ganador del Pot Segundario: ");
											break;
											case(2):
												System.out.print("Ganador del Pot Terciario: ");
											break;
											case(3):
												System.out.print("Ganador del Pot Cuarternario: ");
											break;
											default:
												System.out.print("Ganador del Pot " + i + ": ");
											break;
											}
											for(int k = 0; k<liGanador.size(); ++k) {
												if(k != 0) {
													System.out.print(", ");
												}
												int x = liGanador.get(k);
												  BigDecimal premio = pot.getCantidad().divide(BigDecimal.valueOf(liGanador.size()), 0, RoundingMode.HALF_EVEN);		
												  if(j[x].getTipo().equals("us")) {
													System.out.print(j[x].gUs().getNombre());			
													j[x].gUs().setStack(j[x].gUs().getStack().add(premio));
												} else {
													System.out.print(j[x].gAi().getNombreAI());
													j[x].gAi().setDinero(j[x].gAi().getDinero().add(premio));
												}
												  System.out.println("  +" + premio + "$");
											}
											
											System.out.println("");
											}
										
										/*	public void repartirPots(List<Pot> pots){

	    for(Pot p : pots){	

	        int ganador = calcularGanadorEntre(p.jugador);

	        j[ganador].sumarStack(p.cantidad);
	    }
	}
	*/
										calleActiva = false;
									break;
										}
									
									} while (calleActiva);
									prCiega = (prCiega + 1) % j.length;	
									System.out.println("\n\n------!OPCIONES FIN DEL JUEGO!------");
									System.out.println("-Continuar con la Mesa (1)");
									System.out.println("-Crear nueva Mesa (2)");
									System.out.println("-Ingresar mas fichas (3)");
									System.out.println("-SALIR DEL JUEGO (4)");
									int respFin = sc.nextInt();
									
									switch(respFin) {
									
									}

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