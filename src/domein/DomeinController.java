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

	public String getSpelerAanBeurt() 
	{
		return spel.getSpelerAanBeurt().getNaam();
	}

	public void startNieuweRonde()
	{
		spel.startNieuweRonde();
	}

	public void legKaartBijStapel(Stapel stapel)
	{
		spel.legKaartBijStapel(stapel);
	}

	public void geefStapelinhoudAanSpeler(Stapel stapel)
	{
		spel.geefStapelinhoudAanSpeler(stapel);
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
	
	public List<Speler> getSpelers()
	{
		return spel.getSpelers();
	}
	
}
