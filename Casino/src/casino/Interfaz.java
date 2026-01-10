package casino;

import java.util.Scanner;
import java.math.BigDecimal;

public class Interfaz {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		/*Usuario us2 = new Usuario();
		Usuario us3 = new Usuario();
		Usuario us4 = new Usuario();*/
		Met met= new Met();
		//PantallaCarga ptcg = new PantallaCarga();
		final int USMAX = 4; //4? 
		Usuario[] usArray = new Usuario[USMAX];
		int i;
		for (i = 0; i < USMAX; i++) {
		    usArray[i] = new Usuario();
		    usArray[i].setNombre("Vacio");
		    usArray[i].setDinero(new BigDecimal("0"));
		    }
		i = 0; //Que inicie con el primer usuario de los 4
		boolean running;
		boolean playing;
		boolean boo;
		System.out.println("-----/CASINO/-----" +
				"\nBienvenido al casino de sus sueños :)" +
				"\n"
				);

		do {
			boo = false;
			System.out.print("INTRODUZCA NOMBRE: ");
		    usArray[i].setNombre(sc.next());
				boolean respNombreIgn;
				do {
					respNombreIgn = false;
			System.out.println("¿Nombre Introducido (" + usArray[i].getNombre() + ") es correcto?  N/S");
				String respNombre = sc.next().toUpperCase();
					if(respNombre.equals("N") || respNombre.equals("NO")) {
						System.out.println("REANUDAR" + "\n");
						boo = true;
					} else if(respNombre.equals("S") || respNombre.equals("SI")) {
						System.out.println("NOMBRE USUARIO 1: " + usArray[i].getNombre());
						met.limpiarConsola(); //Esta mierda solo funciona si el casino es ejecutado en CMD o la consola de Linux, solo es estetico (se puede borrar)
						System.out.println("" +
								"\nANALIZANDO DINERO EN LA CUENTA"
								);
						//Esto de aqui le da delay a la aplicacion (el try y el catch por que a veces da fallos) se puede borrar
						try {
						Thread.sleep(800);
						} catch (InterruptedException e) {	
						}
						System.out.println("Dinero Actual: " + usArray[i].getDinero() + "$" + 
								"\nACTIVANDO PROMOCION GRATUITA PARA POBRES" 
						);
						try {
						Thread.sleep(800);
						} catch (InterruptedException e) {	
						}
						usArray[i].setDinero(new BigDecimal ("100.00"));
						System.out.println("\n+100$ DE PROMOCION" + "\nDinero actual: " + usArray[i].getDinero() + "$");
					} else {
						System.out.println("\n--ERROR--");
						System.out.println("CARACTER NO EXPECIFICADO\n");
						respNombreIgn = true;
					}
				} while(respNombreIgn);
			
			/*try {
			Thread.sleep(800);
			} catch (InterruptedException e) {	
			}	
			met.limpiarConsola();
			System.out.print("Iniciando CASINO"); 
			try {Thread.sleep(100);} catch (InterruptedException e) {	}
			ptcg.main(args);*/
			
				
		} while(boo);
		do {
			running = true;
			/*MENU
			 * -Cambio de usuario (1)
			 * 	//		s = String.valueOf(ArrInt[i][j]);
				//		System.out.printf("%-10s", s);
			 * -Dinero de cada usuario (2)
			 * -Banco dame dinero (3)
			 * -JUEGOS (4)
			 * -Cerrar Sesion (5)
			 */
			do {
				playing  = true;
				/*JUEGOS
				 *  
				 */
				
			} while(playing); 
		} while(running);
	}
	
}
