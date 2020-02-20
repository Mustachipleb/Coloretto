package domein;

import java.util.*;

public class Ronde {

	private List<Deck> decks;
	
	public Ronde() {
		this.setDecks(new ArrayList<Deck>());
		
		for (int i = 0; i < 5; i++)
		{
			this.getDecks().add(new Deck(3, 0));
		}
	}
	
	private List<Deck> getDecks() {
		return decks;
	}

	private void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public Deck neemStapel(int stapelNummer) {
		throw new UnsupportedOperationException();
	}
	
	public void legKaartBijStapel(int stapelNummer) {
		throw new UnsupportedOperationException();
	}
}
