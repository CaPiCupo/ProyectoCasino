package casino;

import java.math.BigDecimal;

public class UsuarioPk {

    private Usuario usuario;
    private Cartas[] cartas;
	private BigDecimal valueF;
	private BigDecimal valueP;
	private boolean allIn;
	private boolean sidePot;
	private boolean conDineroAun;
	private int posicion;
	private int calleAct;

	final BigDecimal CERO = new BigDecimal("0");
	

    public UsuarioPk(Usuario usuario) {
        this.usuario = usuario;
        this.cartas = new Cartas[2];
        this.valueF = CERO;
        this.valueP = CERO;
        this.allIn = false;
        this.sidePot = false;
        this.conDineroAun = true;
        this.posicion = 0;
        this.calleAct = 0;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return usuario.getNombre();
    }

    public BigDecimal getDinero() {
        return usuario.getDinero();
    }

    public void setDinero(BigDecimal dinero) {
        usuario.setDinero(dinero);
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

	public boolean isAllIn() {
		return allIn;
	}

	public void setAllIn(boolean allIn) {
		this.allIn = allIn;
	}

	public boolean isSidePot() {
		return sidePot;
	}

	public void setSidePot(boolean sidePot) {
		this.sidePot = sidePot;
	}

	public int getCalleAct() {
		return calleAct;
	}

	public void setCalleAct(int calleAct) {
		this.calleAct = calleAct;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cartas[] getCartas() {
		return cartas;
	}

	public void setCartas(Cartas[] cartas) {
		this.cartas = cartas;
	}

	public boolean isConDineroAun() {
		return conDineroAun;
	}

	public void setConDineroAun(boolean conDineroAun) {
		this.conDineroAun = conDineroAun;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
}
