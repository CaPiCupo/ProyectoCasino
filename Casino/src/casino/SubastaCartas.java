package casino;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SubastaCartas {

		private static Random random = new Random();
		private static List<Cartas> cartasNormales = new ArrayList<Cartas>();
		private static List<CartasEspeciales> cartasEspeciales = new ArrayList<CartasEspeciales>();
		
		public static void jugarSubasta(Usuario uB) {
			
			Scanner sc = new Scanner(System.in);
			
			inicializarCartasNormales();
	        inicializarCartasEspeciales();

			Usuario ia = new Usuario();
			ia.setNombre("RivalIA");
			ia.setDinero(new BigDecimal("100"));
			UsuarioSubasta j1 = new UsuarioSubasta(uB);
			UsuarioSubasta j2 = new UsuarioSubasta(ia);
			
			j1.setCartasGanadas(new ArrayList<>());
	        j2.setCartasGanadas(new ArrayList<>());
	        j1.setPoderesDisponibles(new ArrayList<>());
	        j2.setPoderesDisponibles(new ArrayList<>());
	        j1.setMultiplicador(1);
	        j2.setMultiplicador(1);


			
			System.out.println("====== SUBASTA DE CARTAS ======");

			
			System.out.println("Bienvenido a la Gran Subasta de cartas!!!. En donde el mejor apostador gana.");
			System.out.println("REGLAS: \nSe realizaran 5 rondas de juego, "
					+ "en donde cada jugador tendra que elegir una de 4 cartas y presentar una puja."
					+ "\nLa puja mas alta sera la ganadora, pero CUIDADO! habran cartas especiales que podrian"
					+ "darle ciertas ventajas al rival. MUCHA SUERTE... ");

			esperar(10000);
			
			for (int ronda = 1; ronda <= 5; ronda++) {

	            limpiarConsola();

	            System.out.println("=== RONDA " + ronda + " ===\n");
	            List<Object> cartasRonda = generarCartasRonda();

	            System.out.println("\nCartas en puja:");
	            mostrarCartas(cartasRonda);
	            
	         // ---------------- MENÚ ----------------
	            int opcion;
	            do {
	                System.out.println("\n¿Qué deseas hacer?");
	                System.out.println("1. Pujar");
	                System.out.println("2. Ver cartas ganadas");
	                // Mostrar opción solo si se tiene cartas especiales y no está bloqueado
	                if (!j1.getPoderesDisponibles().isEmpty() && !j1.isBloqueado()) {
	                    System.out.println("3. Usar carta especial");
	                    System.out.println("4. Salir de la subasta");
	                } else {
	                		System.out.println("3. Salir de la subasta ");
	                }
	;
	                System.out.print("Opción: ");
	                opcion = sc.nextInt();

	                switch (opcion) {

	                    case 2:
	                    		if(j1.getCartasGanadas().isEmpty()) {
	                    			System.out.println("> No tienes cartas ganadas...");
	                    			break;
	                    		} else {
	                    			System.out.print("[  ");
	                    			for(Cartas c : j1.getCartasGanadas()) {
	                    				System.out.print("|" + c.mostrar() + "|  ");
	                    			}
	                    			System.out.print("]");
	                    			break;
	                    		}

	                    case 3:
	                        if (j1.getPoderesDisponibles().isEmpty()) {
	                            System.out.println("Saliendo...");
	                            return;
	                        } else if(j1.isBloqueado()){
	                        	    System.out.println("No puedes usar cartas especiales ahora. Estas bloqueado.");
	                        	    j1.setBloqueado(false);
	                        	    break;
	                        } else {
	                        	    usarPoder(j1, j2, sc);
	                        	    break;
	                        }
	                        
	                    case 4:
	                        System.out.println("Saliendo...");
	                        return;
	                }

	            } while (opcion != 1);


	         // ---------------- PUJA DEL JUGADOR ----------------
	            System.out.println("\nTu dinero: " + j1.getUs().getDinero());
	            System.out.print("Introduce tu apuesta: ");
	            BigDecimal apuestaJugador = sc.nextBigDecimal();

	            if (j1.isDescuento()) {
	                apuestaJugador = apuestaJugador.divide(new BigDecimal("2"));
	                System.out.println("DESCUENTO ACTIVADO → Pagas la mitad: " + apuestaJugador);
	                j1.setDescuento(false);
	            }

	            if (!j1.getUs().tieneDineroSuficiente(apuestaJugador)) {
	                System.out.println("\nNo tienes suficiente dinero. La IA gana automáticamente la carta.");
	                procesarGanador(j2, j1, cartasRonda.get(0));
	                esperar(5000);
	                continue;
	            }

	            j1.getUs().setDinero(j1.getUs().getDinero().subtract(apuestaJugador));

	            // ---------------- PUJA DE LA IA ----------------
	            BigDecimal apuestaIA = new BigDecimal(random.nextInt(20) + 1);

	            if (j2.isDescuento()) {
	                apuestaIA = apuestaIA.divide(new BigDecimal("2"));
	                j2.setDescuento(false);
	            }

	            if (!j2.getUs().tieneDineroSuficiente(apuestaIA)) {
	                System.out.println("\nLa IA no tiene suficiente dinero. Ganas la carta automáticamente.");
	                procesarGanador(j1, j2, cartasRonda.get(0));
	                esperar(5000);
	                continue;
	            }

	            j2.getUs().setDinero(j2.getUs().getDinero().subtract(apuestaIA));

	            System.out.println("La IA apuesta: " + apuestaIA);

	            // ---------------- DETERMINAR GANADOR ----------------
	            if (apuestaJugador.compareTo(apuestaIA) > 0) {
	                procesarGanador(j1, j2, cartasRonda.get(0));
	            } else {
	                procesarGanador(j2, j1, cartasRonda.get(0));
	            }

	            esperar(5000);
	        }

	        sc.close();
	    }


		
		
		private static void inicializarCartasNormales() {
			List<Cartas> mazoCompleto = new ArrayList<>();

		    String[] palos = {"♣", "♦", "♥", "♠"};
		    String[] simbolos = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

		    for (String palo : palos) {
		        for (int i = 0; i < simbolos.length; i++) {

		            int numero = i + 1; 
		            String simbolo = simbolos[i];

		            mazoCompleto.add(new Cartas(numero, simbolo, palo));
		        }
		    }
	    }
		
		private static void inicializarCartasEspeciales() {
	        cartasEspeciales.add(new CartasEspeciales("Robo", Habilidades.ROBO));
	        cartasEspeciales.add(new CartasEspeciales("Bloqueo", Habilidades.BLOQUEO));
	        cartasEspeciales.add(new CartasEspeciales("Descuento", Habilidades.DESCUENTO));
	        cartasEspeciales.add(new CartasEspeciales("Multiplicador", Habilidades.MULTIPLICADOR));
	        cartasEspeciales.add(new CartasEspeciales("Revelación", Habilidades.REVELACION));
	        
		}
		
		private static List<Object> generarCartasRonda() {
	        List<Object> cartasRonda = new ArrayList<>();

	        for (int i = 0; i < 3; i++) {
	            cartasRonda.add(cartasNormales.get(random.nextInt(cartasNormales.size())));
	        }

	        /* Agrego una condicion para que en cada ronda haya 4 cartas posibles para subastar,
	         * pero solo una de las cartas podra ser una carta especial con un 45% de probabilidad
	         * de aparecer
	         */
	        if (random.nextDouble() < 0.45) {
	            cartasRonda.add(cartasEspeciales.get(random.nextInt(cartasEspeciales.size())));
	        } else {
	            cartasRonda.add(cartasNormales.get(random.nextInt(cartasNormales.size())));
	        }

	        /* Utilizo el metodo 'shuffle' de la clase Collections de Java 
	         * para barajar las cartas seleccionadas para la ronda 
	         */
	        Collections.shuffle(cartasRonda);
	        return cartasRonda;
	        
	    }
		
		// Metodo para no tener conflictos al mostrar las cartas normales y las especiales
		private static String formatearCarta(Object obj) {
		    if (obj instanceof Cartas) {
		        return ((Cartas) obj).mostrar();
		    }
		    return obj.toString(); 
		}
		
		private static void mostrarCartas(List<Object> cartasRonda) {
		    System.out.print("[  ");

		    for (Object obj : cartasRonda) {
		    	// Pequeña animacion de reparto de cartas	
		    	try { Thread.sleep(500); } catch (InterruptedException e) {}
		    		System.out.print("|" + formatearCarta(obj) + "|  ");
		    }

		    System.out.println("]");
		}
		
		private static void procesarGanador(UsuarioSubasta ganador, UsuarioSubasta perdedor, Object carta) {

	        System.out.println("\nCarta ganada: " + formatearCarta(carta));

	        if (carta instanceof Cartas) {
	            Cartas c = (Cartas) carta;

	            if (ganador.getMultiplicador() == 2) {
	                System.out.println("MULTIPLICADOR ACTIVADO → Ganas 2 copias.");
	                ganador.getCartasGanadas().add(c);
	                ganador.getCartasGanadas().add(c);
	                ganador.setMultiplicador(1);
	            } else {
	                ganador.getCartasGanadas().add(c);
	            }

	        } else if (carta instanceof CartasEspeciales) {

	            CartasEspeciales esp = (CartasEspeciales) carta;

	            if (ganador.isBloqueado()) {
	                System.out.println("Tu poder fue BLOQUEADO. No tiene efecto.");
	                ganador.setBloqueado(false);
	                return;
	            }

	            ganador.getPoderesDisponibles().add(esp);
	            System.out.println("Has ganado una carta especial: " + esp.getNombre());
	        }
	    }

		
		private static void mostrarPoderes(UsuarioSubasta j) {
	        System.out.println("\nPoderes disponibles:");
	        int i = 1;
	        for (CartasEspeciales c : j.getPoderesDisponibles()) {
	            System.out.println(i + ". " + c.getNombre());
	            i++;
	        }
	    }

		
		private static void usarPoder(UsuarioSubasta j1, UsuarioSubasta j2, Scanner sc) {

	        mostrarPoderes(j1);

	        System.out.print("¿Qué poder deseas usar?: ");
	        int pos = sc.nextInt() - 1;

	        if (pos < 0 || pos >= j1.getPoderesDisponibles().size()) {
	            System.out.println("Poder inválido.");
	            return;
	        }

	        CartasEspeciales poder = j1.getPoderesDisponibles().remove(pos);

	        j1.aplicarHabilidad(poder, j2);
	    }

		
		private static void limpiarConsola() {
		    for (int i = 0; i < 40; i++) System.out.println();
		}
		
		private static void esperar(int ms) {
		    try {
		        Thread.sleep(ms);
		    } catch (InterruptedException e) {}
		}
	}

