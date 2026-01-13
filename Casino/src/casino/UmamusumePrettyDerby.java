package casino;

public class UmamusumePrettyDerby {
//Carreras de caballos
	
	private Usuario us; 
	private Usuario us2;
	private boolean multijugador;
	
	public UmamusumePrettyDerby (Usuario us, Usuario us2, boolean multijugador) {
		this.us = us;
		this.multijugador = multijugador;
		this.us2 = us2;
		
		juegodeCaballos();
		
	}
	
	public void juegodeCaballos() {
		System.out.println("Patata");

	}
	PantallaCarga pc = new PantallaCarga();
	
}
