package casino;

public abstract class BotPokerBase extends PokerAI{
	
	public BotPokerBase(int jugador) {
        super();
    }

    public abstract String decidir(Poker poker);
}
