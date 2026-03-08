package casino;

import java.util.List;
import java.math.BigDecimal;

public class Pot {
	
	    BigDecimal cantidad;
	    List<Integer> jugador;
	    
	    public Pot(BigDecimal cantidad, List<Integer> jugador){
	        this.cantidad = cantidad;
	        this.jugador = jugador;
	    
	}

		public BigDecimal getCantidad() {
			return cantidad;
		}

		public void setCantidad(BigDecimal cantidad) {
			this.cantidad = cantidad;
		}

		public List<Integer> getJugador() {
			return jugador;
		}

		public void setJugador(List<Integer> jugador) {
			this.jugador = jugador;
		}
	    

}
