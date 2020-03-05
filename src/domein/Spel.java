package domein;

import java.util.*;

public class Spel 
{
	private List<Speler> spelers;
	private List<Kaart> spelDeck = new ArrayList<>();
	private Ronde huidigeRonde;
	private static final List<String> kleuren = new ArrayList<>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));

	private Speler spelerAanBeurt;

	/**
	 * Constructor
	 */
	public Spel() 
	{
		// Voegt per kleur 9 kaarten toe aan de lijst van kaarten en shuffelt ze erna.
		for (String kleur : getKleuren())
		{
			for (int i = 0; i < 9; i++)
			{
				getSpelDeck().add(new Kaart(kleur));
			}
		}
		
		for (int i = 0; i < 10; i++)
		{
			getSpelDeck().add(new Kaart("+2"));
		}
		
		for (int i = 0; i < 3; i++) 
		{
			getSpelDeck().add(new Kaart("joker"));
		}
		Collections.shuffle(getSpelDeck());
	}


	public List<Stapel> getStapelsHuidigeRonde() 
	{
		return huidigeRonde.getStapels();
	}
	
	public Kaart getKaartVanSpelDeck() 
	{
		return getSpelDeck().get(0);
	}

	public Speler getSpelerAanBeurt() 
	{
		return this.spelerAanBeurt;
	}

	private void setSpelerAanBeurt(Speler spelerAanBeurt) 
	{
		this.spelerAanBeurt = spelerAanBeurt;
	}

	private List<Kaart> getSpelDeck() 
	{
		return spelDeck;
	}

	private static List<String> getKleuren() 
	{
		return kleuren;
	}
	
	public List<Kaart> getKaartenSpeler(String naam) 
	{
		List<Kaart> kaarten = null; 
		for (Speler s : getSpelers()) 
		{
			if (s.getNaam().equals(naam)) 
			{
				kaarten = s.getKaarten();
			}
		}
		return kaarten;
	}
		
	public boolean isStapelVol(int stapelNummer) 
	{
		return huidigeRonde.isStapelVol(stapelNummer);
	}
	
	public boolean isStapelLeeg(int stapelNummer) 
	{
		return huidigeRonde.isStapelLeeg(stapelNummer);
	}

	public void maakSpelersAan(String[] namen) 
	{
		spelers = new ArrayList<>();
		Random rnd = new Random();
		List<String> unassignedKleuren = getKleuren();
		
		for (int i = 0; i < namen.length; i++)
		{
			int randomIndex = rnd.nextInt(unassignedKleuren.size());
			Speler s = new Speler(namen[i]);
			Kaart k = new Kaart(unassignedKleuren.get(randomIndex));
			s.getKaarten().add(k);
			spelDeck.remove(k);
			unassignedKleuren.remove(randomIndex);
			spelers.add(s);
		}
		setSpelerAanBeurt(spelers.get(rnd.nextInt(namen.length)));
	}

	public void speelSpel() 
	{
		this.huidigeRonde = new Ronde(spelers.size());
	}

	public void speelBeurt(String actie, int stapelNummer) 
	{
		if (actie.equals("leggen") || actie.equals("l"))
		{
			this.huidigeRonde.legKaartBijStapel(stapelNummer, spelDeck.get(0));
			spelDeck.remove(0);
		}
		else if (actie.equals("nemen") || actie.equals("n"))
		{
			getSpelerAanBeurt().voegDeckAanKaartenToe(huidigeRonde.neemStapel(stapelNummer));
		}
		
		for (int i = 0; i < spelers.size(); i++) 
		{
			boolean isZelfdeSpeler = spelers.get(i).equals(getSpelerAanBeurt());
			if (isZelfdeSpeler && i != (spelers.size() - 1)) 
			{
				setSpelerAanBeurt(spelers.get(i + 1));
				break;
			}
			else if (isZelfdeSpeler && i == (spelers.size() - 1)) 
			{
				setSpelerAanBeurt(spelers.get(0));
			}
		}
	}


	public boolean isLaatsteRonde() 
	{
		return getSpelDeck().size() <= 15;
	}


	public List<Integer> berekenScore() 
	{
		List<Integer> scores = new ArrayList<>();
		for (Speler s : getSpelers())
		{
			scores.add(s.berekenScore());
		}
		return scores;
	}


	public void assignJoker(String speler, String nieuweKleur) 
	{
		for (Speler s : getSpelers())
		{
			if (s.getNaam().equals(speler))
			{
				s.assignJoker(nieuweKleur);
			}
		}
	}


	public List<Speler> getSpelers()
	{
		return this.spelers;
	}
}
