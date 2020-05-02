package domein;

import java.util.*;

public class DomainController 
{

	private Session session;

	public void startNewSession() 
	{
		this.session = new Session();
	}

	public void createPlayers(List<String> namen) 
	{
		session.createPlayers(namen);
	}

	public Player getSpelerAanBeurt() 
	{
		return session.getSpelerAanBeurt();
	}

	public void startNieuweRonde()
	{
		session.startNieuweRonde();
	}

	public void legKaartBijStapel(int stapelnummer)
	{
		session.legKaartBijStapel(stapelnummer);
	}

	public void geefStapelinhoudAanSpeler(int stapelnummer)
	{
		session.geefStapelinhoudAanSpeler(stapelnummer);
	}
  
	public Round getHuidigeRonde()
	{
		return session.getHuidigeRonde();
	}
	
	public Card peekKaart()
	{
		return session.peekKaart();
	}
	
	public boolean isLaatsteRonde()
	{
		return session.isLaatsteRonde();
	}
	
	public void assignJoker(Player speler, String nieuweKleur)
	{
		session.assignJoker(speler, nieuweKleur);
	}
	
	public Player getNextJokerOwner()
	{
		return session.getNextJokerOwner();
	}
	
	public List<Player> getSpelers()
	{
		return session.getSpelers();
	}
	
	public List<Card> getSpelDeck()
	{
		return session.getSpelDeck();
	}
}
