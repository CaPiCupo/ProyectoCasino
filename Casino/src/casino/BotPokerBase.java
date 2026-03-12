package casino;

public class BotPokerBase{
	static private PokerAI pokerai;
	private String inxDinero;
	private String inxEleccion;
	static Poker poker;

	public BotPokerBase(PokerAI pokerai) {
		this.pokerai = pokerai;
		this.inxDinero = "";
		this.inxEleccion = "";
		
	}
	
	public String getInxDinero() {
		//"EarlyH2", "EarlyH1", "EarlyHB", "EarlyB1" ,"EarlyB2","TrueHB", "LateH2", "LateH1", "LateHB", "LateB1" ,"LateB2", "JCK"
		String[] tipoArray = pokerai.getTipoAI().split("\\|");
		String tipo = tipoArray[1];
		switch(tipo) {
		case "H2":
			return "80/100";
		case "H1":
			return "60/100";
		case "HB":
			return "40/65";
		case "B1":
			return "20/60";
		case "B2":
			return "60/100";
		default:
			return "-1/-1";
		}
	}
	
	public String getInxEleccion() {
		String[] tipoArray = pokerai.getTipoAI().split("\\|");
		String tipo = tipoArray[1];
		switch(tipo) {
		case "H2":
			return "35/60";
		case "H1":
			return "30/55";
		case "HB":
			return "25/50";
		case "B1":
			return "20/45";
		case "B2":
			return "15/40";
		default:
			return "-1/-1";
		}
	}
	public String getBonusCalle() {
		//"EarlyH2", "EarlyH1", "EarlyHB", "EarlyB1" ,"EarlyB2","TrueHB", "LateH2", "LateH1", "LateHB", "LateB1" ,"LateB2", "JCK"
		String[] tipoArray = pokerai.getTipoAI().split("\\|");
		String tipo = tipoArray[0];
		switch(tipo) {
		case "Early":
			return "15/-5";
		case "Late":
			return "-5/15";
		case "True":
			return "0/0";
		default:
			return "-1/-1/-1";
		}
	}
	
	public String getInxTenerCuenta() {
		//MAS FACTORFUERZAMANO // MAS POT EN LA MESA (apuesta total: apuestaCalle + pot)
		String[] tipoArray = pokerai.getTipoAI().split("\\|");
		String tipo = tipoArray[1];
		switch(tipo) {
		case "H2":
			return "80/20";
		case "H1":
			return "70/30";
		case "HB":
			return "60/40";
		case "B1":
			return "45/55";
		case "B2":
			return "30/70";
		default:
			return "-1/-1";
		}
		
	}
	
	public String getBonoFarol() {
		String[] tipoArray = pokerai.getTipoAI().split("\\|");
		String tipo = tipoArray[1];
		switch(tipo) {
		case "H2":
			return "-20";
		case "H1":
			return "-10";
		case "HB":
			return "5";
		//TODO ESTOS MULTIPLICAN
		case "B1":
			return "1.2";
		case "B2":
			return "1.5";
		default:
			return "-1";
		}
		
		
	}
}
