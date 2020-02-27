package domein;

import java.util.*;

public class Spel {

	private List<Speler> spelers;
	private List<Kaart> spelDeck = new ArrayList<Kaart>();
	private Ronde huidigeRonde;
	private static final List<String> kleuren = new ArrayList<String>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));

	private Speler spelerAanBeurt;

	/**
	 * Constructor
	 */
	public Spel() {
		
		// Voegt per kleur 9 kaarten toe aan de lijst van kaarten en shuffelt ze erna.
		for (String kleur : getKleuren())
		{
			for (int i = 0; i < 9; i++)
			{
				Kaart k = new Kaart(kleur);
				getSpelDeck().add(k);
			}
		}
		Collections.shuffle(getSpelDeck());
	}

	private List<Kaart> getSpelDeck() {
		return spelDeck;
	}

	private static List<String> getKleuren() {
		return kleuren;
	}

	public void maakSpelersAan(String[] namen) {
		spelers = new ArrayList<Speler>();
		Random rnd = new Random();
		List<String> unassignedKleuren = getKleuren();
		
		for (int i = 0; i < namen.length; i++)
		{
			int randomIndex = rnd.nextInt(unassignedKleuren.size());
			Speler s = new Speler(namen[i]);
			s.getKaarten().add(new Kaart(unassignedKleuren.get(randomIndex)));
			unassignedKleuren.remove(randomIndex);
			spelers.add(s);
		}
		setSpelerAanBeurt(spelers.get(rnd.nextInt(namen.length)));
	}
	
	public ArrayList<Kaart> getKaartenSpeler(String naam) {
		ArrayList<Kaart> kaarten = null; 
		for (Speler s : this.spelers) {
			if (s.getNaam().equals(naam)) {
				kaarten = s.getKaarten();
			}
		}
		return kaarten;
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
