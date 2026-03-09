package casino;

public class Jugador{
	private UsuarioPk us;
	private PokerAI ai;
	private int apuestaJugador;
	//private int apuestaTotalJugador;
	private String tipo;
	private boolean actionFoldeo;
	private boolean actionCheckIgualar;	
	private boolean actionAllIn;
	private boolean actionPagoCiegaGrande;
	//private Cartas[] cartas;
	
	public Jugador() {
		//this.cartas = new Cartas[2];
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
	
	/*public Cartas[] getCartas() {
		return cartas;
	}

	public void setCartas(Cartas[] cartas) {
		this.cartas = cartas;
	}
	 */
	public int getApuesta() {
		return apuestaJugador;
	}
		
	public void setApuesta(int apuestaJugador) {
		this.apuestaJugador = apuestaJugador;
	}

	/*public int getApuestaTotal() {
		return apuestaTotalJugador;
	}
		
	public void setApuestaTotal(int apuestaTotalJugador) {
		this.apuestaJugador = apuestaTotalJugador;
	}
	*/

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

	public boolean isActionPagoCiegaGrande() {
		return actionPagoCiegaGrande;
	}

	public void setActionPagoCiegaGrande(boolean actionPagoCiegaGrande) {
		this.actionPagoCiegaGrande = actionPagoCiegaGrande;
	}
	
	
}


