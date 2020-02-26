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
		//TODO: startdeck shuffelen
		this.startDeck = new Deck(63);
	}

	public void maakSpelersAan(int aantalSpelers, String[] namen) {
		//TODO: spelers unieke kaart geven
		spelers = new ArrayList<Speler>();
		Random rnd = new Random();
		
		for (int i = 0; i < aantalSpelers; i++)
		{
			Speler s = new Speler(namen[i]);
			spelers.add(s);
		}
		setSpelerAanBeurt(spelers.get(rnd.nextInt(aantalSpelers)));
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

	private void setSpelerAanBeurt(Speler spelerAanBeurt) {
		this.spelerAanBeurt = spelerAanBeurt;
	}

	public void speelSpel() {
		this.huidigeRonde = new Ronde();
	}

	public void speelBeurt(String actie, int stapelNummer) {
		if (actie.equals("leggen"))
		{
			this.huidigeRonde.legKaartBijStapel(stapelNummer);
		}
		else if (actie.equals("nemen"))
		{
			this.huidigeRonde.neemStapel(stapelNummer);
		}
	}

	public boolean isLaatsteBeurt() {
		return huidigeRonde.isLaatsteBeurt();
	}
}
