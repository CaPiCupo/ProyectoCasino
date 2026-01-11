package casino;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Usuario {
	
	private String nombre;
	private BigDecimal dinero;
	private boolean usoPromocion;
	private int usoBanco;
	
	public Usuario() {
	}
	
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
		this.dinero = dinero.setScale(2, RoundingMode.HALF_EVEN);
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
		this.usoBanco = usoBanco;
	}
	
   

}
/*BigDecimal value1 = new BigDecimal("4.5");
value1=value1.setScale(0, RoundingMode.HALF_EVEN);

BigDecimal value2 = new BigDecimal("6.5");
value2=value2.setScale(0, RoundingMode.HALF_EVEN);

System.out.println(value1+"\n"+value2);*/
