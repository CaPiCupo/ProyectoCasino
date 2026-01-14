package casino;

import java.math.BigDecimal;

public class PokerAI {
	//AI para los juegadores del poker aaaa
	//lo
	private String tipoAI;
	private BigDecimal valueF;
	private BigDecimal valueP;
	private BigDecimal eulerValue;
	private BigDecimal dinero;
	private int calleAct;
	private boolean multijugador;
	
	public PokerAI(){	
	}

	public String getTipoAI() {
		return tipoAI;
	}

	public void setTipoAI(String tipoAI) {
		this.tipoAI = tipoAI;
	}

	public BigDecimal getValueF() {
		return valueF;
	}

	public void setValueF(BigDecimal valueF) {
		this.valueF = valueF;
	}

	public BigDecimal getValueP() {
		return valueP;
	}

	public void setValueP(BigDecimal valueP) {
		this.valueP = valueP;
	}

	public BigDecimal getEulerValue() {
		return eulerValue;
	}

	public void setEulerValue(BigDecimal eulerValue) {
		this.eulerValue = eulerValue;
	}

	public BigDecimal getDinero() {
		return dinero;
	}

	public void setDinero(BigDecimal dinero) {
		this.dinero = dinero;
	}

	public int getCalleAct() {
		return calleAct;
	}

	public void setCalleAct(int calleAct) {
		this.calleAct = calleAct;
	}

	public boolean isMultijugador() {
		return multijugador;
	}

	public void setMultijugador(boolean multijugador) {
		this.multijugador = multijugador;
	}
	
}
