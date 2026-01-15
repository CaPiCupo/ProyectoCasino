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
		final BigDecimal CERO = new BigDecimal("0");
		final int USMAX = 4; //4? 
		Usuario[] usArray = new Usuario[USMAX];
		int i;
		for (i = 0; i < USMAX; i++) {
		    usArray[i] = new Usuario();
		    usArray[i].setNombre("Vacio");
		    usArray[i].setDeuda(new BigDecimal("0"));
		    usArray[i].setDinero(new BigDecimal("0"));
		    usArray[i].setTmEnDeuda(-1);
		    usArray[i].setUsoPromocion(false);
		    usArray[i].setUsoBanco(3); //3?
		    }
		i = 0; //Inicie con el primer usuario de los 4
		boolean multiplayer = false; //Si es mucha tela se quita
		int i2 = -1; //Para probar si el multijugador es buena idea
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
			System.out.println("\n\n -------|MENU|-------");
			if(usArray[i].getTmEnDeuda() != -1) {
				System.out.println(" Nombre Usuario " + (i + 1) +": "+ usArray[i].getNombre() + "   Dinero: " + usArray[i].getDinero() + "$" + "   Deuda: -" + usArray[i].getDeuda() + "$" + "   Tiempo Deuda: " + usArray[i].getTmEnDeuda() + " turnos    ");

			} else {
				System.out.println(" Nombre Usuario " + (i + 1) +": "+ usArray[i].getNombre() + "   Dinero: " + usArray[i].getDinero() + "$" + "   Usos del Banco: " + usArray[i].getUsoBanco()); 

			}
			System.out.println(
					 "   -Usuarios (1)" +
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
											 	usArray[i] = new Usuario();
											    usArray[i].setNombre("Vacio");
											    usArray[i].setDeuda(new BigDecimal("0"));
											    usArray[i].setDinero(new BigDecimal("0"));
											    usArray[i].setTmEnDeuda(-1);
											    usArray[i].setUsoPromocion(false);
											    usArray[i].setUsoBanco(3); //3?
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
			  			/*    if (dnAct == null) {
			  			        dnAct = CERO;
			  			}*/
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
			    				boolean booOp2Op1;
			    				do {
			    					booOp2Op1 = true;
			    					System.out.println("\n -------|SACAR DINERO|-------" +
					    					 "\n   -Endeudarte (1)" +	//Dar dinero y luego devolver el doble;
					    					 "\n   -Financiarse (2)" +	//Dar dinero si menos de 0 = -1 uso de Usos banco
					    					 "\n   -Salir (3)" );
								    System.out.print("Introduzca numero correspondiente: ");
								    int respOp2Op1 = sc.nextInt();
								    switch (respOp2Op1) {
								    case (1): 
							    		if(usArray[i].getTmEnDeuda() == -1) {
							    			System.out.println("\nBienvenido a la opcion endeudarse");
									    	System.out.println("Se le permitira pedir dinero y en 5 turnos se cobrara el doble");
									    	System.out.println("¿Desea continuar con la operacion? N/S");
					        				String respDeuda1 = sc.next().toUpperCase(); 
										if(respDeuda1.equals("N") || respDeuda1.equals("NO")) {
										System.out.println("\n Volviendo al menu \n");
										} else if(respDeuda1.equals("S") || respDeuda1.equals("SI")) {
										double intereses = 2;
										System.out.print("Introduzca la cantidad: ");
										String str1 = sc.next().replace(",", ".");  //PONER LIMITE BASADO EN EL DINERO (dinero = 100 =>> MAX = 1000)
					    					BigDecimal dnNw = new BigDecimal(str1);
					    					usArray[i].setDinero(dnAct.add(dnNw));
					    					usArray[i].setDeuda(dnNw.multiply(new BigDecimal(intereses)));
					    					intereses += intereses * 0.75;
					    					usArray[i].setTmEnDeuda(5);
					    					booOp2Op1 = false;
					    					booOp2 = false;
										} else {
										System.out.println("CARACTER NO RECONOCIDO");
										}
							    		} else {
											System.out.println("No te puedes ENDEUDARSE estando ya endeudado");
									}


								    	break;
								    case (2): 
								    		if(usArray[i].getDinero().compareTo(CERO) == 0) {
								    			System.out.println("Bienvenido a la opcion financiarse");
								    			System.out.println("¿Desea USAR un punto de banco para obtener +100$ (no se pueden volver a obtener?  N/S"); //Quiza merece la pena quitar esta opcion
						        				String respDeuda2 = sc.next().toUpperCase();
											if(respDeuda2.equals("N") || respDeuda2.equals("NO")) {
											System.out.println("\n Volviendo al menu \n");
											} else if(respDeuda2.equals("S") || respDeuda2.equals("SI")) {
											System.out.println("Efectuando la suma...");
											usArray[i].setDinero(dnAct.add(new BigDecimal("100")));
											usArray[i].setUsoBanco(usArray[i].getUsoBanco() -1); 
											System.out.println("\n +100$ en la cuenta \n");
											booOp2Op1 = false;
					    						booOp2 = false;
											} else {
											System.out.println("CARACTER NO RECONOCIDO");
											}
								    		} else {
												System.out.println("Esta opcion solo es efectuada para pobre (Dinero = 0)");
										}
								    	break;
								    case (3):
								    		booOp2Op1 = false;
								    continue; // booOp2 = false; si se percata la profesora
								    } 
			    				} while (booOp2Op1);
			    				break;
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
			    				a
			    				}*/
			    			break;
						default:
						    System.out.println("\nCARACTER NO ESPECIFICADO \n");
						break;
			    			}
			    		} while(booOp2);
			    } else {
			    		System.out.println("El banco no confia mas en ti" + "\nFUERA DE AQUI");
			    }
			    System.out.println("");
			    break;
			    case (3):
			    	playing  = true;
			    while (playing) {
					playing  = true;
					System.out.println("\n -------|JUEGOS|-------" +
					 "\n   -Apuestas de Caballos (1)" +	
					 "\n   -Poker (2)" +	
					 "\n   -ACTIVAR MULTIJUGADOR (3)" +	
					 "\n   -Salir (4)" );
					//UmamusumePrettyDerby caballos = new UmamusumePrettyDerby(usArray[i]);
				UmamusumePrettyDerby caballos;
				if(!multiplayer) {
				caballos = new UmamusumePrettyDerby(usArray[i], null, multiplayer);	
				} else {
				caballos = new UmamusumePrettyDerby(usArray[i], usArray[i2], multiplayer);	
				}
				
				Poker pachigada;
				if(!multiplayer) {
					System.out.println(usArray[i].getNombre());
				pachigada = new Poker(usArray[i], null, multiplayer);	
				} else {
				pachigada = new Poker(usArray[i], usArray[i2], multiplayer);	
				}

				/*JUEGOS
					 * 
					 *  
					 */
				break;
				}
			}
				

		} else {
			System.out.println("No mas dinero " + 
					"\nNo mas confianza del banco"
					);
			System.out.println("Fin del Juego");
			running = false;
		}
		} while(running);
	}
	
}
