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
        ia.setDeuda(new BigDecimal("0"));
        ia.setTmEnDeuda(-1);
        ia.setDinero(new BigDecimal("100")); 

        UsuarioSubasta j1 = new UsuarioSubasta(uB);
        UsuarioSubasta j2 = new UsuarioSubasta(ia);

        j1.setCartasGanadas(new ArrayList<>());
        j2.setCartasGanadas(new ArrayList<>());
        j1.setPoderesDisponibles(new ArrayList<>());
        j2.setPoderesDisponibles(new ArrayList<>());
        j1.setMultiplicador(1);
        j2.setMultiplicador(1);

        limpiarConsola();

        System.out.println("========= SUBASTA DE CARTAS =========");

        System.out.println("Bienvenido a la Gran Subasta de cartas!!!. En donde el mejor apostador gana.");
        System.out.println("REGLAS: \nSe realizaran 5 rondas de juego, "
                + "en donde cada jugador tendra que elegir una de 4 cartas y presentar una puja."
                + "\nLa puja mas alta sera la ganadora, pero CUIDADO! habran cartas especiales que podrian"
                + "darle ciertas ventajas al rival. MUCHA SUERTE... ");

        esperar(6000);

        // =========================
        // COMPRA DE FICHAS INICIAL
        // =========================
        System.out.println("\nTienes " + j1.getUs().getDinero() + " de dinero.");
        System.out.print("¿Cuánto dinero deseas convertir en fichas? (1 dinero = 10 fichas, máximo 300 fichas): ");

        int dineroConvertir = sc.nextInt();

        while (dineroConvertir <= 0 ||
               dineroConvertir > 30 ||
               dineroConvertir > j1.getUs().getDinero().intValue()) {

            System.out.print("Cantidad inválida. Ingresa un valor entre 1 y "
                    + Math.min(30, j1.getUs().getDinero().intValue()) + ": ");
            dineroConvertir = sc.nextInt();
        }

        int fichasJugador = dineroConvertir * 10;

        j1.getUs().setDinero(j1.getUs().getDinero().subtract(new BigDecimal(dineroConvertir)));

        System.out.println("Has comprado " + fichasJugador + " fichas.");
        System.out.println("Tu dinero restante es: " + j1.getUs().getDinero());
       
        int fichasIA = fichasJugador;

        // =========================
        // RONDAS
        // =========================
        for (int ronda = 1; ronda <= 5; ronda++) {

            limpiarConsola();

            System.out.println("========= RONDA " + ronda + " =========\n");
            List<Object> cartasRonda = generarCartasRonda();

            System.out.println("Cartas en puja:");
            mostrarCartas(cartasRonda);

            // ---------------- MENÚ ----------------
            int opcion;
            do {
                System.out.println("\n¿Qué deseas hacer?");
                System.out.println("1. Pujar");
                System.out.println("2. Ver cartas ganadas");
                if (!j1.getPoderesDisponibles().isEmpty() && !j1.isBloqueado()) {
                    System.out.println("3. Usar carta especial");
                    System.out.println("4. Salir de la subasta");
                } else {
                    System.out.println("3. Salir de la subasta ");
                }
                System.out.print("Opción: ");
                opcion = sc.nextInt();

                switch (opcion) {

                    case 2:
                        if (j1.getCartasGanadas().isEmpty()) {
                            System.out.println("> No tienes cartas ganadas...");
                            break;
                        } else {
                            System.out.print("[  ");
                            for (Cartas c : j1.getCartasGanadas()) {
                                System.out.print("|" + c.mostrar() + "|  ");
                            }
                            System.out.print("]");
                            break;
                        }

                    case 3:
                        if (j1.getPoderesDisponibles().isEmpty()) {
                            System.out.println("Saliendo...");
                            return;
                        } else if (j1.isBloqueado()) {
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
            System.out.print("\nElige la carta que deseas pujar (1-4): ");
            int eleccion = sc.nextInt() - 1;

            while (eleccion < 0 || eleccion >= cartasRonda.size()) {
                System.out.print("Opción inválida. Elige entre 1 y 4: ");
                eleccion = sc.nextInt() - 1;
            }

            Object cartaElegida = cartasRonda.get(eleccion);

            System.out.println("\nTus fichas: " + fichasJugador);
            System.out.print("Introduce tu apuesta en fichas: ");
            int apuestaJugador = sc.nextInt();

            if (j1.isDescuento()) {
                apuestaJugador = apuestaJugador / 2;
                System.out.println("DESCUENTO ACTIVADO → Pagas la mitad: " + apuestaJugador + " fichas");
                j1.setDescuento(false);
            }

            while (apuestaJugador <= 0 || apuestaJugador > fichasJugador) {
                System.out.print("Apuesta inválida. Ingresa una cantidad válida: ");
                apuestaJugador = sc.nextInt();
            }

            fichasJugador -= apuestaJugador;

            // ---------------- PUJA DE LA IA ----------------
            int eleccionIA = 0;

            // Prioriza las cartas especiales
            for (int i = 0; i < cartasRonda.size(); i++) {
                if (cartasRonda.get(i) instanceof CartasEspeciales) {
                    eleccionIA = i;
                    break;
                }
            }

            // Si no hay especiales, elige la carta normal más alta
            if (!(cartasRonda.get(eleccionIA) instanceof CartasEspeciales)) {
                int mejorValor = -1;
                for (int i = 0; i < cartasRonda.size(); i++) {
                    if (cartasRonda.get(i) instanceof Cartas) {
                        Cartas c = (Cartas) cartasRonda.get(i);
                        if (c.getNumero1() > mejorValor) {
                            mejorValor = c.getNumero1();
                            eleccionIA = i;
                        }
                    }
                }
            }

            Object cartaElegidaIA = cartasRonda.get(eleccionIA);

            System.out.println("La IA ha elegido la carta número " + (eleccionIA + 1) + ": " + formatearCarta(cartaElegidaIA));

            int valorCarta = (cartaElegidaIA instanceof Cartas)
                    ? ((Cartas)cartaElegidaIA).getNumero1()
                    : 15; 

            double agresividad = 0.25 + (valorCarta / 20.0); 
            // entre 0.25 y 0.9

            int apuestaIA = (int)(fichasIA * agresividad);

            // mínimo 10 fichas si tiene
            if (apuestaIA < 10 && fichasIA >= 10) apuestaIA = 10;

            // nunca más de lo que tiene
            if (apuestaIA > fichasIA) apuestaIA = fichasIA;

            if (j2.isDescuento()) {
                apuestaIA = apuestaIA / 2;
                j2.setDescuento(false);
            }
            if (apuestaIA > fichasIA) {
                apuestaIA = fichasIA;
            }
            if (apuestaIA <= 0 && fichasIA > 0) {
                apuestaIA = 1;
            }

            fichasIA -= apuestaIA;

            System.out.println("La IA apuesta: " + apuestaIA + " fichas");

            // ---------------- DETERMINAR GANADOR ----------------
            if (apuestaJugador > apuestaIA) {
                procesarGanador(j1, j2, cartaElegida);
            } else {
                procesarGanador(j2, j1, cartaElegidaIA);
            }

            esperar(5000);
        }

        // =========================
        // PREMIO FINAL
        // =========================
        int fichasGanadas = calcularValorCartas(j1);
        int dineroGanado = fichasGanadas / 10;

        j1.getUs().setDinero(j1.getUs().getDinero().add(new BigDecimal(dineroGanado)));

        System.out.println("\n=== FIN DE LA SUBASTA ===");
        System.out.println("Valor total de tus cartas: " + fichasGanadas + " fichas");
        System.out.println("Has ganado " + dineroGanado + " de dinero.");
        System.out.println("Tu nuevo saldo es: " + j1.getUs().getDinero());

    }

    private static void inicializarCartasNormales() {
        cartasNormales = new ArrayList<>();

        String[] palos = {"♣", "♦", "♥", "♠"};
        String[] simbolos = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String palo : palos) {
            for (int i = 0; i < simbolos.length; i++) {

                int numero = i + 1;
                String simbolo = simbolos[i];

                cartasNormales.add(new Cartas(numero, simbolo, palo));
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

        if (random.nextDouble() < 0.45) {
            cartasRonda.add(cartasEspeciales.get(random.nextInt(cartasEspeciales.size())));
        } else {
            cartasRonda.add(cartasNormales.get(random.nextInt(cartasNormales.size())));
        }

        Collections.shuffle(cartasRonda);
        return cartasRonda;
    }

    private static String formatearCarta(Object obj) {
        if (obj instanceof Cartas) {
            return ((Cartas) obj).mostrar();
        }
        if (obj instanceof CartasEspeciales) {
            CartasEspeciales esp = (CartasEspeciales) obj;
            return "" + esp.getNombre() + "";
        }
        return obj.toString();
    }

    private static void mostrarCartas(List<Object> cartasRonda) {
        System.out.println();
        for (int i = 0; i < cartasRonda.size(); i++) {
            Object obj = cartasRonda.get(i);
            System.out.println((i + 1) + ". " + formatearCarta(obj));
        }
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

    private static int calcularValorCartas(UsuarioSubasta jugador) {
        int total = 0;

        for (Cartas c : jugador.getCartasGanadas()) {
            total += c.getNumero1() * 5;
        }

        for (CartasEspeciales esp : jugador.getPoderesDisponibles()) {
            total += valorEspecial(esp);
        }

        return total;
    }

    private static int valorEspecial(CartasEspeciales esp) {
        switch (esp.getHabilidad()) {
            case ROBO: return 50;
            case BLOQUEO: return 40;
            case DESCUENTO: return 30;
            case MULTIPLICADOR: return 60;
            case REVELACION: return 35;
            default: return 20;
        }
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