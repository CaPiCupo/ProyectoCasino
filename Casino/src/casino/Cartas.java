package casino;

import java.math.BigDecimal; //Puto double

public class Cartas {

	private 	String numero;
	private String color;
	//private String cp; Carta Completa
	private BigDecimal valor;
	private boolean pokerBlackjack;
	
	public Cartas() {
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
		return numero + color;
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
	
	
	
	
}
