package casino;

public class Poker {
	//JUEGO POKER MODALIDAD TEXAS HOLD'EM m
	private Usuario us; 
	private Usuario us2;
	private boolean multijugador;
	
	public Poker (Usuario us, Usuario us2, boolean multijugador) {
		this.us = us;
		this.multijugador = multijugador;
		this.us2 = us2;
		
		juegodePoker();
		
	}
	
	public void juegodePoker() {
		System.out.println("Batata");
	}


}
