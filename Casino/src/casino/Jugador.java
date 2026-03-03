package casino;

public class Jugador {
	private UsuarioPk us;
	private PokerAI ai;
	private int posicion;
	private String tipo;
		
		
	public Jugador() {
	}

	public UsuarioPk gUs() {
		return us;
	}

	public void sUs(UsuarioPk us) {
		this.us = us;
	}

	public PokerAI gAi() {
		if()
		return ai;
	}

	public void sAi(PokerAI ai) {
		this.ai = ai;
	}
		
	public int gPos() {
		return posicion;
	}
		
	public void sPos(int posicion) {
		this.posicion = posicion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}


