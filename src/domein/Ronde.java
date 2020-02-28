package domein;

import java.util.*;

public class Ronde {

	private List<Deck> decks;
	
	public Ronde(int aantalSpelers) {
		this.setDecks(new ArrayList<Deck>());
		
		for (int i = 0; i < aantalSpelers; i++)
		{
			this.getDecks().add(new Deck());
		}
	}
	
	public List<Deck> getDecks() {
		return decks;
	}

	private void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public Deck neemStapel(int stapelNummer) {
		Deck d = getDecks().get(stapelNummer);
		getDecks().remove(stapelNummer);
		return d;
	}
	
	public void legKaartBijStapel(int stapelNummer, Kaart k) {
		getDecks().get(stapelNummer).voegKaartToe(k);
	}
	
	public boolean isStapelVol(int stapelNummer) {
		return getDecks().get(stapelNummer).isVol();
	}
	
	public boolean isStapelLeeg(int stapelNummer) {
		return getDecks().get(stapelNummer).isLeeg();
	}
}
