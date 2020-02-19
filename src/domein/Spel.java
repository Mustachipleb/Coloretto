package domein;

import java.util.*;

public class Spel {

	private List<Speler> spelers;
	private Deck startDeck;
	private Ronde huidigeRonde;

	private Speler spelerAanBeurt;

	/**
	 * Constructor
	 */
	public Spel() {
		this.startDeck = new Deck(63);
	}

	public void maakSpelersAan(int aantalSpelers, String[] namen) {
		spelers = new ArrayList<Speler>();
		for (int i = 0; i < aantalSpelers; i++)
		{
			Speler s = new Speler(namen[i]);
			spelers.add(s);
		}
	}

	public ArrayList<ArrayList<Kaart>> getKaartenSpelers() {
		ArrayList<ArrayList<Kaart>> kaartenSpelers = new ArrayList<ArrayList<Kaart>>();
		for (int i = 0; i < spelers.size(); i++)
		{
			for (int j = 0; j < spelers.get(i).getKaarten().size(); j++)
			{
				kaartenSpelers.add(spelers.get(i).getKaarten());
			}
		}
		return kaartenSpelers;
	}

	public Speler getSpelerAanBeurt() {
		return this.spelerAanBeurt;
	}

	public void speelSpel() {
		throw new UnsupportedOperationException();
	}

	public void speelBeurt(String actie) {
		throw new UnsupportedOperationException();
	}
}
