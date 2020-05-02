package domein;

import java.util.*;

public class Player 
{
	private String naam;
	private List<Card> kaarten = new ArrayList<>();

	public Player(String naam) 
	{
		setNaam(naam);
	}

	public List<Card> getKaarten() 
	{
		return this.kaarten;
	}

	public String getNaam() 
	{
		return naam;
	}

	private void setNaam(String naam) 
	{
		this.naam = naam;
	}
	
	public void geefStapelKaarten(Stack d)
	{
		for (int i = 0; i < d.getKaarten().size(); i++) 
		{
			getKaarten().add(d.getKaarten().get(i));
		}
	}
	
	public int berekenScore()
	{
		int totaalScore = 0;
		Set<Card> distinct = new HashSet<>(getKaarten());
		
		distinct.remove(new Card("joker"));
		
		List<Integer> aantalKaartenPerKleur = new ArrayList<>();
		for (Card k : distinct)
		{
			if ("+2".equals(k.getKleur()))
				totaalScore += (2 * Collections.frequency(getKaarten(), k));
			else
				aantalKaartenPerKleur.add(Collections.frequency(getKaarten(), k));
		}
		aantalKaartenPerKleur.sort(Collections.reverseOrder());

		for (int i = 0; i < Math.min(3, aantalKaartenPerKleur.size()); i++)
		{
			int scoreVanKleur = 0;
			for (int j = 1; j <= aantalKaartenPerKleur.get(i); j++)
			{
				scoreVanKleur += j;
			}
			totaalScore += Math.min(scoreVanKleur, 21);
		}

		for (int i = 3; i < aantalKaartenPerKleur.size(); i++)
		{
			int scoreVanKleur = 0;
			for (int j = 1; j <= aantalKaartenPerKleur.get(i); j++)
			{
				scoreVanKleur += j;
			}
			totaalScore -= Math.min(scoreVanKleur, 21);
		}
		return totaalScore;
	}
	
	public void assignJoker(String nieuweKleur)
	{
		getKaarten().remove(new Card("joker"));
		getKaarten().add(new Card(nieuweKleur));
	}
}
