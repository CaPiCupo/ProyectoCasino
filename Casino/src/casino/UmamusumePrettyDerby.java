package casino;


import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;

public class UmamusumePrettyDerby extends Frame {

    private Usuario us;
    private int[] progreso;
    private boolean hayGanador = false;
    private int ganador = -1;
    private int numCaballos = 4;

    private int CaballoElegida;
    private BigDecimal apuesta;

    public UmamusumePrettyDerby(Usuario us) {

        this.us = us;

        Scanner sc = new Scanner(System.in);

        Met.empujarMucho();
        System.out.println("------ APUESTAS CARRERA ------");
        System.out.println("Dinero disponible: " + us.getDinero());

        System.out.print("\nElige una Caballo (1-4): ");
        CaballoElegida = sc.nextInt() - 1;

        System.out.print("\nCantidad a apostar: ");
        apuesta = sc.nextBigDecimal();

        // restar dinero apostado
        us.setDinero(us.getDinero().subtract(apuesta));

        progreso = new int[numCaballos];

        setTitle("Carrera de Caballos");
        setSize(600, 300);
        setVisible(true);

        for (int i = 0; i < numCaballos; i++) {
            final int index = i;
            new Thread(() -> correr(index)).start();
        }
    }

    private void correr(int index) {

        Random random = new Random();

        while (progreso[index] < 100 && !hayGanador) {

            try {
                Thread.sleep(random.nextInt(200) + 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progreso[index] += random.nextInt(10) + 1;

            synchronized (this) {

                if (progreso[index] >= 100 && !hayGanador) {

                    progreso[index] = 100;
                    hayGanador = true;
                    ganador = index;

                    System.out.println("\nGANADOR: Caballo " + (ganador + 1));

                    if (ganador == CaballoElegida) {

                        BigDecimal premio = apuesta.multiply(new BigDecimal("4"));
                        us.setDinero(us.getDinero().add(premio));

                        System.out.println("HAS GANADO! Premio: " + premio);

                    } else {

                        System.out.println("Has perdido la apuesta.");

                    }

                    System.out.println("Dinero actual: " + us.getDinero());
                }
            }

            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {

        for (int i = 0; i < numCaballos; i++) {

            int y = 60 + i * 40;

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(150, y, 300, 20);

            g.setColor(i == ganador ? Color.GREEN : Color.BLUE);
            g.fillRect(150, y, progreso[i] * 3, 20);

            g.setColor(Color.BLACK);
            g.drawRect(150, y, 300, 20);
            g.drawString("Caballo " + (i + 1) + " - " + progreso[i] + "%", 20, y + 15);
        }

        if (ganador != -1) {
            g.setColor(Color.RED);
            g.drawString("🏆 Caballo " + (ganador + 1) + " HA GANADO!", 200, 50);
        }
    }
}