package casino;

import java.util.List;
import java.util.Scanner;

public class UsuarioSubasta {
	
	private Usuario us; 
    private List<Cartas> cartasGanadas;
    private List<CartasEspeciales> poderesDisponibles;
    private boolean bloqueado; 
    private boolean descuento;
    private boolean puedeVerPuja; 
    private int multiplicador; 

    public UsuarioSubasta(Usuario us) {
        this.us = us;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    public List<Cartas> getCartasGanadas() {
        return cartasGanadas;
    }

    public void setCartasGanadas(List<Cartas> cartasGanadas) {
        this.cartasGanadas = cartasGanadas;
    }

    public void addCartasGanadas(Cartas carta) {
        cartasGanadas.add(carta);
    }

    public List<CartasEspeciales> getPoderesDisponibles() {
        return poderesDisponibles;
    }

    public void setPoderesDisponibles(List<CartasEspeciales> poderesDisponibles) {
        this.poderesDisponibles = poderesDisponibles;
    }

    public void addCartaEspecial(CartasEspeciales poder) {
        poderesDisponibles.add(poder);
    }
    
    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public boolean isPuedeVerPuja() {
        return puedeVerPuja;
    }

    public void setPuedeVerPuja(boolean puedeVerPuja) {
        this.puedeVerPuja = puedeVerPuja;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }
    
    public void aplicarHabilidad(CartasEspeciales cartaEsp, UsuarioSubasta rival) {
        switch(cartaEsp.getHabilidad()) {
            case ROBO:
                if(rival.getCartasGanadas().isEmpty()) {
                    System.out.println("El rival no tiene cartas para robar.Intenta luego: ");
                    break;
                } else {
                    robarCarta(rival);
                    break;
                }
            case BLOQUEO:
                rival.setBloqueado(true);
                System.out.println(us.getNombre() + " bloquea al rival.");
                break;

            case DESCUENTO:
                setDescuento(true);
                System.out.println(us.getNombre() + " obtiene descuento. Proxima puja a la mitad de precio.");
                break;

            case MULTIPLICADOR:
                setMultiplicador(2);
                System.out.println(us.getNombre() + " obtiene multiplicador x2.");
                break;

            case REVELACION:
                setPuedeVerPuja(true);
                System.out.println(us.getNombre() + " podrá ver la puja de la rival.");
                break;

        }       
    }
    
    public Cartas robarCarta(UsuarioSubasta rival) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cartas del rival: \n[  ");
        for(Cartas a : rival.getCartasGanadas()) {
            System.out.print("|*|  ");
        }
        System.out.print("]\n");

        System.out.print("¿Que carta deseas robar?: ");
        int posicion = sc.nextInt() - 1;
        do {
            System.out.println("Posicion de carta invalida. Vuelva a intentar.");
            System.out.print("¿Que carta deseas robar?: ");
            posicion = sc.nextInt() - 1;
        } while(posicion > rival.getCartasGanadas().size() || posicion < 0);

        Cartas robada = rival.getCartasGanadas().remove(posicion);
        System.out.println("Robo completado!!!... Carta guardada en tu mazo.");
        return robada;
    }
}
