package casino;

public class Jugador {
	private UsuarioPk us;
	private PokerAI ai;
	private int apuestaJugador;
	private String tipo;
	private boolean actionFoldeo;
	private boolean actionCheckIgualar;	
	private boolean actionAllIn;
	
	public Jugador() {
	}

	public UsuarioPk gUs() {
		return us;
	}

	public void sUs(UsuarioPk us) {
		this.us = us;
	}

	public PokerAI gAi() {
		return ai;
	}

	public void sAi(PokerAI ai) {
		this.ai = ai;
	}
		
	public int getApuesta() {
		return apuestaJugador;
	}
		
	public void setApuesta(int apuestaJugador) {
		this.apuestaJugador = apuestaJugador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isActionFoldeo() {
		return actionFoldeo;
	}

	public void setActionFoldeo(boolean actionFoldeo) {
		this.actionFoldeo = actionFoldeo;
	}

	public boolean isActionCheckIgualar() {
		return actionCheckIgualar;
	}

	public void setActionCheckIgualar(boolean actionCheckIgualar) {
		this.actionCheckIgualar = actionCheckIgualar;
	}

	public boolean isActionAllIn() {
		return actionAllIn;
	}

	public void setActionAllIn(boolean actionAllIn) {
		this.actionAllIn = actionAllIn;
	}
	
	
}


