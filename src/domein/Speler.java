package domein;

import java.util.*;

public class Speler {

	private String naam;
	private ArrayList<Kaart> kaarten = new ArrayList<Kaart>();

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
	
	public void voegDeckAanKaartenToe(Deck d) {
		for (int i = 0; i < d.getKaarten().size(); i++) {
			getKaarten().add(d.getKaarten().get(i));
		}
	}
}
