package casino;

import java.util.Scanner;
import java.math.BigDecimal;

public class Interfaz {
	//Cambiar cada lectura de un int de int ejem = sc.nextInt() a int ejem = Integer.parseInt(sc.next()); evita fallos
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		/*Usuario us2 = new Usuario();
		Usuario us3 = new Usuario();
		Usuario us4 = new Usuario();*/
		Met met= new Met();
		//PantallaCarga ptcg = new PantallaCarga();
		BigDecimal CERO = new BigDecimal("0");
		final int USMAX = 4; //4? 
		Usuario[] usArray = new Usuario[USMAX];
		int i;
		for (i = 0; i < USMAX; i++) {
		    usArray[i] = new Usuario();
		    usArray[i].setNombre("Vacio");
		    usArray[i].setDinero(new BigDecimal("0"));
		    usArray[i].setUsoPromocion(false);
		    usArray[i].setUsoBanco(3); //3?
		    }
		i = 0; //Inicie con el primer usuario de los 4
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
								"\n\nACTIVANDO PROMOCION GRATUITA PARA POBRES" 
						);
						usArray[i].setUsoPromocion(true);
						try {
						Thread.sleep(800);
						} catch (InterruptedException e) {	
						}
						usArray[i].setDinero(new BigDecimal ("100.00"));
						System.out.println("+100$ DE PROMOCION");
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
			playing = false;
			/*MENU
			 * -Usuarios (1)
			 * 	//		s = String.valueOf(ArrInt[i][j]);
				//		System.out.printf("%-10s", s);
			 * -Banco dame dinero (2)
			 * -Creditos (4)
			 * -JUEGOS (5)
			 * -Cerrar Sesion (6)
			 */
			if(!(usArray[i].getUsoBanco() == 0 && usArray[i].getDinero().compareTo(CERO) <= 0)) {
			met.limpiarConsola();
			System.out.println("\n\n -------|MENU|-------" +
			 "\n Nombre Usuario " + (i + 1) +": "+ usArray[i].getNombre() + "   Dinero: " + usArray[i].getDinero() + "$" + "   Usos del Banco: " + usArray[i].getUsoBanco() +
			 "\n   -Usuarios (1)" +
			 "\n   -Banco dame dinero (2)" +
			 "\n   -JUEGOS (3)" +
			 "\n   -Creditos (4)" +
			 "\n   -Cerrar Sesion (5)"
			 //Multijugador??
					);
			System.out.print("\nIntroduzca numero correspondiente: ");
			int resp = sc.nextInt();
			System.out.println("\n");
				switch (resp) {
			    case (1): //Usuarios
			    	boolean booOp1;
			    	System.out.println("-----LISTA DE USUARIOS-----");
			        for (int f = 0; f < USMAX; f++) { //f ya que si no al darle directamente a salir cambiaria el usuario al 4
			            String nm = usArray[f].getNombre();
			            BigDecimal dn = usArray[f].getDinero();
			            int usB = usArray[f].getUsoBanco();
					    System.out.printf("Usuario " + (f + 1) + ": %-15s | Dinero: %10s $ | Usos del Banco: %3d%n", nm, dn, usB);
			        }
			        System.out.println("");
			     	do {
				    	booOp1 = true;
			        System.out.println("-Cambiar de Usuario (1)" + 
			        		"\n-Salir (2)"
			        		);
			        System.out.print("Introduzca numero correspondiente: ");
			        int respOp1 = sc.nextInt();
			        switch (respOp1) {
			        case (1):
			        	boolean booOp1Op;
			        		System.out.print("Introduzca numero del usuario " + "(" + (1) + "-" + (USMAX) + "): ");
			        		int respOp1Op1 = sc.nextInt() -1;
			        	 	do {
			        	 		booOp1Op = false;
			        		if(respOp1Op1 < USMAX && respOp1Op1 >= 0) {	
			        			i = respOp1Op1;
			        			if(usArray[i].getNombre().equals("Vacio")) {
			        				System.out.println("\nIDENTIFICADA CUENTA NUEVA");
			        				System.out.print("Introduzca nombre de usuario " + (i +1) + ": ");
			        				usArray[i].setNombre(sc.next());
								System.out.println("" +
										"\nANALIZANDO DINERO EN LA CUENTA"
											);
								try {
								Thread.sleep(800);
								} catch (InterruptedException e) {	
								}
								System.out.println("Dinero Actual: " + usArray[i].getDinero() + "$" + 
										"\n\nACTIVANDO PROMOCION GRATUITA PARA POBRES" 									
										);
								usArray[i].setUsoPromocion(true);
								try {
								Thread.sleep(800);
								} catch (InterruptedException e) {	
								}
								usArray[i].setDinero(new BigDecimal ("100.00"));
								System.out.println("+100$ DE PROMOCION");
			        				booOp1 = false;
			        			} else {
			        				System.out.println("Usuario " + (i+1) + " a nombre de " + usArray[i].getNombre() + " adquirida");
			        				System.out.println("¿Desea formatearla?  N/S"); //Quiza merece la pena quitar esta opcion
			        				String respNombreFm = sc.next().toUpperCase();
										if(respNombreFm.equals("N") || respNombreFm.equals("NO")) {
											System.out.println("¿Desea cambiar el nombre?  N/S");
					        					String respNombreCm = sc.next().toUpperCase();
					        						if(respNombreCm.equals("S") || respNombreCm.equals("SI")) {
					        							System.out.print("\nIntroduzca nuevo nombre: ");
					        							usArray[i].setNombre(sc.next());
					        							System.out.println("\nVolviendo al menu\n");
					        							booOp1 = false;
					        						} else if (!(respNombreCm.equals("S") || respNombreCm.equals("SI") || respNombreCm.equals("N") || respNombreCm.equals("NO") )){
													System.out.println("\nCARACTER NO ESPECIFICADO \n");
					        						}
										} else if(respNombreFm.equals("S") || respNombreFm.equals("SI")) {
											usArray[i].setNombre("Vacio");
										    usArray[i].setDinero(new BigDecimal("0"));
										    usArray[i].setUsoPromocion(false);
										    usArray[i].setUsoBanco(3);
										    System.out.println("Formateo Terminado \n");
										    booOp1Op = true;
										} else {
											System.out.println("\nCARACTER NO ESPECIFICADO \n");
										}
			        			}
			        		} else {
			        			System.out.println("Numero Fuera de los Limites");
			        		}
			        } while (booOp1Op);
			        		break;
				    case (2):	
				    		booOp1 = false;
				    break;
				    default: 
				    		System.out.println("\nCARACTER NO ESPECIFICADO \n");
				    	break;
			        }
			    	} while(booOp1);
			    break;
			    
			    case (2): //Banco (la idea es si esta en negativos pierda una vida    
			    	boolean booOp2;
			    if(!(usArray[i].getUsoBanco() == 0)) {
			    		do {
			    			booOp2 = true;
			    			BigDecimal dnAct = usArray[i].getDinero();
			  			    if (dnAct == null) {
			  			        dnAct = BigDecimal.ZERO;
			  			}
			    			System.out.println("\n -------|MENU DEL BANCO|-------" +
			    					 "\nDinero: " + usArray[i].getDinero() + "$" + "   Usos del Banco: " + usArray[i].getUsoBanco() +
			    					 "\n   -Sacar dinero (1)" +
			    					 "\n   -Como funciona (2)" +
			    					 "\n   -Salir (3)" 
			    							);
					    System.out.print("Introduzca numero correspondiente: ");
					    int respOp2 = sc.nextInt();
			    			switch (respOp2) {
			    			case (1):
			    				
			    			case (2):	
						    	System.out.println("La idea es endeudarte con el banco y tener que devolver \nel doble de dinero en un limitado tiempo de turnos");
						break;
			    			case (3):	
					    		booOp2 = false;
			    			break;
			    			case (69): //Para meeter dinero y probar
			    				System.out.println("\nOPCION DESARROLLADOR ACTIVADA");
			    				System.out.print("Introduzca el dinero que quieras meter: ");
			    				String str69 = sc.next().replace(",", ".");
			    				BigDecimal dnNw = new BigDecimal(str69);
			    				usArray[i].setDinero(dnAct.add(dnNw));
			    				/*if(dnNw.compareTo(CERO) >= 0) { 
			    				usArray[i].setDinero(dnAct.add(dnNw));
			    				} else {
			    				usArray[i].setDinero(dnAct.subtract(dnNw));	
			    				}*/
			    			break;
						default:
						    System.out.println("\nCARACTER NO ESPECIFICADO \n");
						break;
			    			}
			    		} while(booOp2);
			    } else {
			    		System.out.println("El banco no confia mas en ti" + "\n FUERA DE AQUI");
			    }
			    System.out.println("");
			    break;
			}
				
			while (playing) {
				playing  = true;
				/*JUEGOS
				 *  
				 */
				
			}
		} else {
			System.out.println("No mas dinero " + 
					"\n No mas confianza del banco"
					);
			System.out.println("Fin del Juego");
			running = false;
		}
		} while(running);
	}
	
}
