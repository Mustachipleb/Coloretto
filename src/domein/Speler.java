package domein;

import java.util.*;

public class Speler 
{

	private String naam;
	private List<Kaart> kaarten = new ArrayList<>();

	public Speler(String naam) 
	{
		setNaam(naam);
	}

	public List<Kaart> getKaarten() 
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
	
	public void voegDeckAanKaartenToe(Stapel d) 
	{
		for (int i = 0; i < d.getKaarten().size(); i++) 
		{
			getKaarten().add(d.getKaarten().get(i));
		}
	}
	
	public int berekenScore()
	{
		int totaalScore = 0;
		int aantalJokers = 0;
		List<String> kleuren = new ArrayList<>();
		for (int i = 0; i < getKaarten().size(); i++) 
		{
			kleuren.add(getKaarten().get(i).getKleur());
		}

		/* Pseudocode:
		 * totalScore <- 0
		 * amountOfJokers <- 0
		 * For every unique card in the player's possession
		 *     score <- 0
		 *     amountOfCards <- amount of cards of the same color
		 *     If the card is a +2
		 *         score <- score + (2 * amountOfCards)
		 *     Else if the card is a joker
		 *         amountOfJokers <- amountOfJokers + 1
		 *     Else
		 *         For every value between 1 (inclusive) and amountOfJokers (inclusive)
		 *             score <- score + value
		 *         If score is higher than 21
		 *             score <- 21
		 *     totalScore <- totalScore += score
		 */
		
		/* TODO: telling jokers.
		 * brainstorm:
		 *     op einde spel vragen welke kleur de jokers moeten worden (moet niet allemaal zelfde zijn),
		 *     en met een nieuwe methode de jokers hun kleur reassignen. (Kaart.setKleur? -> validatie dat dit een joker is)
		 *     
		 */
		
		Set<String> distinct = new HashSet<>(kleuren);
		for (String s : distinct)
		{
			int scoreVanKleur = 0;
			int aantalKaartenVanKleur = Collections.frequency(kleuren, s);
			switch (s)
			{
				case "+2":
					scoreVanKleur += (2 * aantalKaartenVanKleur);
					break;
				case "joker":
					aantalJokers++;
					break;
				default:
					for (int i = 1; i <= aantalKaartenVanKleur; i++)
					{
						scoreVanKleur += i;
					}
					scoreVanKleur = scoreVanKleur > 21 ? 21 : scoreVanKleur;
					break;
			}
			totaalScore += scoreVanKleur;
		}
		return totaalScore;
	}
	
	public void assignJoker(String nieuweKleur)
	{
		int jokerIndex = getKaarten().indexOf(new Kaart("joker"));
		getKaarten().remove(jokerIndex);
		getKaarten().add(new Kaart(nieuweKleur));
	}
}
