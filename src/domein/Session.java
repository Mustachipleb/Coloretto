package domein;

import java.util.*;

public class Session 
{
	private List<Player> spelers;
	private Player spelerAanBeurt;
	private List<Card> spelDeck = new ArrayList<>();
	private Round huidigeRonde;
	private static final List<String> KLEUREN = new ArrayList<>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));
	private static final int AANTAL_JOKERS = 3;
	private static final int AANTAL_PLUS2 = 10;
	private static final int AANTAL_KAARTEN_PER_KLEUR = 9;

	public Session()
	{
		for (String kleur : getKleuren())
			for (int i = 0; i < AANTAL_KAARTEN_PER_KLEUR; i++)
				spelDeck.add(new Card(kleur));
		for (int i = 0; i < AANTAL_PLUS2; i++)
			spelDeck.add(new Card("+2"));
		for (int i = 0; i < AANTAL_JOKERS; i++)
			spelDeck.add(new Card("joker"));
		Collections.shuffle(spelDeck);
	}

	public Round getHuidigeRonde()
	{
		return this.huidigeRonde;
	}
	
	public Card peekKaart()
	{
		return spelDeck.get(0);
	}

	public Player getSpelerAanBeurt() 
	{
		return this.spelerAanBeurt;
	}

	private void setSpelerAanBeurt(Player spelerAanBeurt) 
	{
		this.spelerAanBeurt = spelerAanBeurt;
	}

	private static List<String> getKleuren() 
	{
		return KLEUREN;
	}

	public void createPlayers(List<String> namen)
	{
		spelers = new ArrayList<>();
		Random rnd = new Random();
		List<String> unassignedKleuren = getKleuren();

		for (String naam : namen)
		{
			int randomIndex = rnd.nextInt(unassignedKleuren.size());
			Player s = new Player(naam);
			Card k = new Card(unassignedKleuren.get(randomIndex));
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
			this.huidigeRonde = new Round(spelers);
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
		boolean isRoundOver = true;
		for (Stack stapel : getHuidigeRonde().getStapels())
		{
			if (stapel != null)
				isRoundOver = false;
		}
		if (isRoundOver)
			startNieuweRonde();
	}

	public boolean isLaatsteRonde() 
	{
		return spelDeck.size() <= 15;
	}

	public void assignJoker(Player speler, String nieuweKleur)
	{
		speler.assignJoker(nieuweKleur);
	}
	
	public Player getNextJokerOwner()
	{
		for (Player speler : getSpelers())
		{
			if (speler.getKaarten().contains(new Card("joker")))
			{
				return speler;
			}
		}
		return null;
	}

	public List<Player> getSpelers()
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

	public List<Card> getSpelDeck() 
	{
		return spelDeck;
	}
}
