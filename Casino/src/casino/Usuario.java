package casino;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Usuario {
	
	private String nombre;
	private BigDecimal dinero;
	private BigDecimal deuda;
	private boolean usoPromocion;
	private int usoBanco;
	private int tmEnDeuda;
	
	public Usuario() {
	}
	
	final BigDecimal CERO = new BigDecimal("0");
	
	/*public Usuario(String nombre, BigDecimal dinero) {
		this.nombre = nombre;
		this.dinero = dinero.setScale(2, RoundingMode.HALF_EVEN);
	}*/

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getDinero() {
		return dinero;
	}

	public void setDinero(BigDecimal dinero) {
		if (tmEnDeuda == 0 && dinero.compareTo(CERO) < 0) {
			System.out.println("\n Se te acabo el tiempo para dar el dinero \n");
			this.dinero = dinero.subtract(deuda);
		} else if(dinero.compareTo(CERO) < 0) {
				dinero = CERO;	
		}
		this.dinero = dinero.setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public BigDecimal getDeuda() {
		return deuda;
	}

	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}

	public boolean isUsoPromocion() {
		return usoPromocion;
	}

	public void setUsoPromocion(boolean usoPromocion) {
		this.usoPromocion = usoPromocion;
	}

	public int getUsoBanco() {
		return usoBanco;
	}

	public void setUsoBanco(int usoBanco) {
		if(usoBanco < 0) {
			usoBanco = 0;
		}
		this.usoBanco = usoBanco;
	}

	public int getTmEnDeuda() {
		return tmEnDeuda;
	}

	public void setTmEnDeuda(int tmEnDeuda) {
		if(dinero.compareTo(CERO) < 0) {
			this.tmEnDeuda = 0;
			return;
		}  
		
		if (tmEnDeuda == 0) {
			this.tmEnDeuda = -1;
		} else {
			/*if(tmEnDeuda < 0) {
				tmEnDeuda = 0;
			}*/
			this.tmEnDeuda = tmEnDeuda;
		}
	}
	
   

}
/*BigDecimal value1 = new BigDecimal("4.5");
value1=value1.setScale(0, RoundingMode.HALF_EVEN);

BigDecimal value2 = new BigDecimal("6.5");
value2=value2.setScale(0, RoundingMode.HALF_EVEN);

System.out.println(value1+"\n"+value2);*/
