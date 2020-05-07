package domein;

import java.util.*;

public class DomeinController 
{

	private Spel spel;

	public void startNieuwSpel() 
	{
		this.spel = new Spel();
	}

	public void maakSpelersAan(List<String> namen) 
	{
		spel.maakSpelersAan(namen);
	}

	public Speler getSpelerAanBeurt() 
	{
		return spel.getSpelerAanBeurt();
	}

	public void startNieuweRonde()
	{
		spel.startNieuweRonde();
	}

	public void legKaartBijStapel(int stapelnummer)
	{
		spel.legKaartBijStapel(stapelnummer);
	}

	public void geefStapelinhoudAanSpeler(int stapelnummer)
	{
		spel.geefStapelinhoudAanSpeler(stapelnummer);
	}
  
	public Ronde getHuidigeRonde()
	{
		return spel.getHuidigeRonde();
	}
	
	public Kaart peekKaart()
	{
		return spel.peekKaart();
	}
	
	public boolean isLaatsteRonde()
	{
		return spel.isLaatsteRonde();
	}
	
	public void assignJoker(Speler speler, String nieuweKleur)
	{
		spel.assignJoker(speler, nieuweKleur);
	}
	
	public Speler getNextJokerOwner()
	{
		return spel.getNextJokerOwner();
	}
	
	public List<Speler> getSpelers()
	{
		return spel.getSpelers();
	}
	
	public List<Kaart> getSpelDeck()
	{
		return spel.getSpelDeck();
	}
	
	public void resumeGame(List<Speler> players, List<Stapel> stacks, Speler playerNext)
	{
		startNieuwSpel();
		spel.getSpelers().clear();
		spel.getSpelers().addAll(players);
		startNieuweRonde();
		spel.getHuidigeRonde().getStapels().clear();
		spel.getHuidigeRonde().getStapels().addAll(stacks);
		spel.setSpelerAanBeurt(playerNext);
		
		for (Speler player : players)
		{
			for (Kaart card : player.getKaarten())
			{
				spel.getSpelDeck().remove(card);
			}
		}
		
		for (Stapel stack : stacks)
		{
			for (Kaart card : stack.getKaarten())
			{
				if (card != null)
					spel.getSpelDeck().remove(card);
			}
		}
	}
}
