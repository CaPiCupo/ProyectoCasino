package casino;

import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class NegroJack {
	//Codigo hecho por Pedro Manuel Maqueda
    static Random random = new Random();
    private Usuario us; 
    		
    /*public NegroJack (Usuario us) {
    	
    	System.out.println("Caca");
    }*/
    
    public static int sacarCarta() {
        int carta = random.nextInt(13) + 1;
        if (carta > 10) return 10; 
        if (carta == 1) return 11;
        return carta;
    }


    public static int ajustarAses(int total, int ases) {
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    public static void eventoInicio() {
        int r = random.nextInt(8);
        if (r == 0) System.out.println("Hoy tienes suerte.");
        if (r == 1) System.out.println("La mesa está caliente.");
    }

    public NegroJack (Usuario us) {
    	this.us = us;
 
    	Met.empujarMucho();
    	Scanner sc = new Scanner(System.in);
    	boolean booJack;
    	boolean oncelittlehelp = true;
    	do {
    	booJack = true;
    	
    	System.out.println("-------|MENU BLACKJACK|-------");
    	if(us.getTmEnDeuda() != -1) {
			System.out.println(" Nombre Usuario " + ": "+ us.getNombre() + "   Dinero: " + us.getDinero() + "$" + "   Deuda: -" + us.getDeuda() + "$" + "   Tiempo Deuda: " + us.getTmEnDeuda() + " turnos    ");

		} else {
			System.out.println(" Nombre Usuario " + ": "+ us.getNombre() + "   Dinero: " + us.getDinero() + "$" + "   Usos del Banco: " + us.getUsoBanco()); 
		}	
    	System.out.println(
    			"\n -Apostar (1)" +
    			"\n -SALIR (2)"		
    			);
    	
		System.out.print("\nIntroduzca numero correspondiente: ");
		int resp = sc.nextInt();
		System.out.println("\n");
		
		switch(resp) {
		
		case(1):
		if (oncelittlehelp) {
			System.out.println("(Si apuestas cero sales del la apuesta)");
			oncelittlehelp = false;
		}
		System.out.print("\nDINERO APUESTA: ");
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
		}
        eventoInicio();
        int jugadorTotal = 0;
        int crupierTotal = 0;
        int asesJugador = 0;
        int asesCrupier = 0;


        int cartaVisibleCrupier = 0;

        for (int i = 0; i < 2; i++) {

            int c = sacarCarta();
            jugadorTotal += c;
            if (c == 11) asesJugador++;

        c = sacarCarta();
            crupierTotal += c;
            if (c == 11) asesCrupier++;

            if (i == 0) cartaVisibleCrupier = c;
        }

        jugadorTotal = ajustarAses(jugadorTotal, asesJugador);
        crupierTotal = ajustarAses(crupierTotal, asesCrupier);

        System.out.println("Tu total: " + jugadorTotal);
        System.out.println("Carta visible del crupier: " + cartaVisibleCrupier);


        while (jugadorTotal < 21) {
            System.out.println("¿Pedir carta? (s/n)");
            String op = sc.next();

            if (!op.equalsIgnoreCase("s")) break;

            int c = sacarCarta();
            System.out.println("Sacaste: " + c);

            jugadorTotal += c;
            if (c == 11) asesJugador++;

            jugadorTotal = ajustarAses(jugadorTotal, asesJugador);

            System.out.println("Tu total: " + jugadorTotal);

            if (jugadorTotal > 21) {
                System.out.println("Te pasaste. Pierdes.");
                System.out.println("-" + apuesta);
                us.setDinero(us.getDinero().subtract(ap));
                return;
            }
        }


        System.out.println("\nTurno del crupier...");
        System.out.println("Total inicial crupier: " + crupierTotal);

        while (crupierTotal < 17) {
            int c = sacarCarta();
            System.out.println("Crupier saca: " + c);

            crupierTotal += c;
            if (c == 11) asesCrupier++;

            crupierTotal = ajustarAses(crupierTotal, asesCrupier);
        }

        System.out.println("Total final crupier: " + crupierTotal);


        if (crupierTotal > 21 || jugadorTotal > crupierTotal) {
            System.out.println("¡Ganaste!");
            System.out.println("+" + (apuesta*2));
            us.setDinero(us.getDinero().add(ap.multiply(new BigDecimal("2"))));
            
        } else if (jugadorTotal == crupierTotal) {
            System.out.println("Empate.");
            System.out.println("Se te devuelve el dinero");
        } else {
            System.out.println("Pierdes.");
            System.out.println("-" + apuesta);
            us.setDinero(us.getDinero().subtract(ap));
        }
        System.out.println("");
        break;
        
		}
		case(2):
			booJack = false;
		break;
		default:
			System.out.println("Numero no valido");
		break;
		}
    	} while (booJack);
    	
    }
}

