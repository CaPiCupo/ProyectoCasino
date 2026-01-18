package casino;

import java.math.BigDecimal;

public class Poker {
	//JUEGO POKER MODALIDAD TEXAS HOLD'EM m
	//private Usuario us; 
	//private Usuario us2;
	private UsuarioPk us; 
	private UsuarioPk us2;
	private boolean multijugador;
	static final int maxct = 52;
	static Cartas[] ct = new Cartas[maxct];	
	
	public Poker (UsuarioPk us, UsuarioPk us2,/*Usuario us, Usuario us2,*/ boolean multijugador) {
		this.us = us;
		this.multijugador = multijugador;
		this.us2 = us2;
		
		generarJugadores();
		generarCarta();
		juegodePoker();
				
	}

	public void generarJugadores() {
		    us.setConDineroAun(true);
	        us.setValueF(us.getDinero());
	        us.setValueP(BigDecimal.ZERO); 
	        us.setCalleAct(0);
	        us.setAllIn(false);
	        us.setSidePot(false);
	        us.setCartas(new Cartas[2]);

	        if (multijugador && us2 != null) {
	            us2.setConDineroAun(true);
	            us2.setValueF(us2.getDinero());
	            us2.setValueP(BigDecimal.ZERO);
	            us2.setCalleAct(0);
	            us2.setAllIn(false);
	            us2.setSidePot(false);
	            us2.setCartas(new Cartas[2]);
	        }
	    
	}
	public void generarCarta() {	
		int i;
		int i2 = 0;
		int cnm = 1;
		BigDecimal vnm = new BigDecimal("0.05"); //Para el valor de las cartas
		BigDecimal pnm = new BigDecimal("0.05");
		for (i = 0; i < maxct; i++) {
        ct[i] = new Cartas();
        if(cnm == 1) { //MAS VALOR PARA CARTAS ALTAS (+0.05 2-10 +0.10 10-A)
        		ct[i].setNumero("A");
        		ct[i].setValor(BigDecimal.valueOf(1.00)); 
        } else if(cnm == 10) {
  			ct[i].setNumero("T");
      		ct[i].setValor(BigDecimal.valueOf(0.55));
        } else if(cnm == 11) {
  			ct[i].setNumero("J");
      		ct[i].setValor(BigDecimal.valueOf(0.65));
        } else if(cnm == 12) {
			ct[i].setNumero("Q");
      		ct[i].setValor(BigDecimal.valueOf(0.75));
        } else if(cnm == 13) {
			ct[i].setNumero("K");
      		ct[i].setValor(BigDecimal.valueOf(0.85));
        } else {
      		ct[i].setNumero(String.valueOf(cnm));
      		ct[i].setValor(vnm);
        }
        switch (i2) {
            case (0):
                ct[i].setColor("♥"); // C de Corazon
            break;
            case (1):
                ct[i].setColor("♦"); // D de Diamante
            break;
            case (2):
            		ct[i].setColor("♣"); // T de trebol
            break;
            case (3):
            		ct[i].setColor("♠"); // P de Pica
		    break;
        }
		    cnm++;
		    vnm = vnm.add(pnm);
	        if (cnm > 13) {
	      	cnm = 1;
	      	vnm = new BigDecimal("0.05");
	     	i2++;
	        }
		}				
	}
	public void juegodePoker() {
		BigDecimal dnAct = us.getDinero();
		for(int f = 0; f < maxct; ++f) {
			System.out.println(ct[f].getCp() + " = " + ct[f].getValor());
		}
		System.out.println(us.getDinero());
		System.out.println(us.getNombre());
		
		
		
		
		
		if(us.getUsuario().getTmEnDeuda() != -1){//us.getUsuario().setDeuda(us.getUsuario().getDeuda().subtract(new BigDecimal ("1")));
		us.getUsuario().setTmEnDeuda(us.getUsuario().getTmEnDeuda() -1);
		}
	}
}
