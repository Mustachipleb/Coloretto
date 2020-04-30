package domein;

import java.util.*;

public class Spel 
{
	private List<Speler> spelers;
	private Speler spelerAanBeurt;
	private List<Kaart> spelDeck = new ArrayList<>();
	private Ronde huidigeRonde;
	private static final List<String> KLEUREN = new ArrayList<>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));
	private static final int AANTAL_JOKERS = 3;
	private static final int AANTAL_PLUS2 = 10;
	private static final int AANTAL_KAARTEN_PER_KLEUR = 9;

	public Spel()
	{
		for (String kleur : getKleuren())
			for (int i = 0; i < AANTAL_KAARTEN_PER_KLEUR; i++)
				spelDeck.add(new Kaart(kleur));
		for (int i = 0; i < AANTAL_PLUS2; i++)
			spelDeck.add(new Kaart("+2"));
		for (int i = 0; i < AANTAL_JOKERS; i++)
			spelDeck.add(new Kaart("joker"));
		Collections.shuffle(spelDeck);
	}

	public Ronde getHuidigeRonde()
	{
		return this.huidigeRonde;
	}
	
	public Kaart peekKaart()
	{
		return spelDeck.get(0);
	}

	public Speler getSpelerAanBeurt() 
	{
		return this.spelerAanBeurt;
	}

	private void setSpelerAanBeurt(Speler spelerAanBeurt) 
	{
		this.spelerAanBeurt = spelerAanBeurt;
	}

	private static List<String> getKleuren() 
	{
		return KLEUREN;
	}

	public void maakSpelersAan(List<String> namen)
	{
		spelers = new ArrayList<>();
		Random rnd = new Random();
		List<String> unassignedKleuren = getKleuren();

		for (String naam : namen)
		{
			int randomIndex = rnd.nextInt(unassignedKleuren.size());
			Speler s = new Speler(naam);
			Kaart k = new Kaart(unassignedKleuren.get(randomIndex));
			s.getKaarten().add(k);
			spelDeck.remove(k);
			unassignedKleuren.remove(randomIndex);
			spelers.add(s);
		}
		setSpelerAanBeurt(spelers.get(rnd.nextInt(namen.size())));
	}

	public void startNieuweRonde()
	{
		if (spelDeck.size() >= 15)
		{
			this.huidigeRonde = new Ronde(spelers);
		}
		else
		{
			throw new IllegalStateException("Er kan geen nieuwe ronde gestart worden.");
		}
	}

	public void legKaartBijStapel(int stapelnummer)
	{
		huidigeRonde.legKaartBijStapel(huidigeRonde.getStapels().get(stapelnummer), spelDeck.get(0));
		spelDeck.remove(0);
		volgendeSpelerAanBeurt();
	}

	public void geefStapelinhoudAanSpeler(int stapelnummer)
	{
		getSpelerAanBeurt().geefStapelKaarten(huidigeRonde.neemStapel(huidigeRonde.getStapels().get(stapelnummer)));
		huidigeRonde.getSpelersDieNogMogenSpelen().remove(getSpelerAanBeurt());
		volgendeSpelerAanBeurt();
	}

	public boolean isLaatsteRonde() 
	{
		return spelDeck.size() <= 15;
	}

	public void assignJoker(Speler speler, String nieuweKleur)
	{
		speler.assignJoker(nieuweKleur);
	}

	public List<Speler> getSpelers()
	{
		return this.spelers;
	}

	private void volgendeSpelerAanBeurt()
	{
		for (int i = 0; i < spelers.size(); i++)
		{
			boolean isZelfdeSpeler = spelers.get(i).equals(getSpelerAanBeurt());
			if (isZelfdeSpeler && i == (spelers.size() - 1))
			{
				setSpelerAanBeurt(spelers.get(0));
			}
			else if (isZelfdeSpeler)
			{
				setSpelerAanBeurt(spelers.get(i + 1));
				break;
			}
		}
		if (!huidigeRonde.getSpelersDieNogMogenSpelen().contains(getSpelerAanBeurt()) && !huidigeRonde.getSpelersDieNogMogenSpelen().isEmpty())
			volgendeSpelerAanBeurt();
	}

	public List<Kaart> getSpelDeck() 
	{
		return spelDeck;
	}
}
