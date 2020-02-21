package domein;

import java.util.*;

public class Speler {

	private String naam;
	private ArrayList<Kaart> kaarten;
	private String startKleur;

	private Collection<Deck> decks;

	public Speler(String naam) {
		setNaam(naam);
	}

	public ArrayList<Kaart> getKaarten() {
		return this.kaarten;
	}

	public String getNaam() {
		return naam;
	}

	private void setNaam(String naam) {
		this.naam = naam;
	}

	public Speler(int naam) {
		throw new UnsupportedOperationException();
	}
}
