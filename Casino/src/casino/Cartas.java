package casino;

import java.math.BigDecimal; //Puto double

public class Cartas {

	private String numero;
	private int numero1;
	private String color;
	//private String cp; Carta Completa
	private BigDecimal valor;
	private boolean oculto;
	private boolean pokerBlackjack;
	
	public Cartas() {
	}
	
	public Cartas(int numero1, String color) {
		this.numero1 = numero1;
		this.color = color;			
	}
	
	
	public int getNumero1() {
		return numero1;
	}

	public void setNumero1(int numero1) {
		this.numero1 = numero1;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getCp() { //Para cuando me pidan la carta completa
		if(!oculto) {
		return numero + color;
		} else {
		return "▓▓";
		}
		
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isPokerBlackjack() {
		return pokerBlackjack;
	}

	public void setPokerBlackjack(boolean pokerBlackjack) {
		this.pokerBlackjack = pokerBlackjack;
	}

	public boolean isOculto() {
		return oculto;
	}

	public void setOculto(boolean oculto) {
		this.oculto = oculto;
	}
	
	
	
	
}
