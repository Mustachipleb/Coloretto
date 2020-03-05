package domein;

import java.util.*;

public class DomeinController 
{

	Spel spel;

	public DomeinController() 
	{
		
	}

	public void startNieuwSpel() 
	{
		this.spel = new Spel();
	}

	public void maakSpelersAan(String[] namen) 
	{
		spel.maakSpelersAan(namen);
	}
	
	public List<String> getKaartenSpeler(String naam) 
	{
		List<Kaart> kaarten = this.spel.getKaartenSpeler(naam);
		List<String> kaartenKleur = new ArrayList<>();
		for (Kaart k : kaarten) {
			kaartenKleur.add(k.getKleur());
		}
		return kaartenKleur;
	}

	public String getSpelerAanBeurt() 
	{
		return spel.getSpelerAanBeurt().getNaam();
	}

	public void speelSpel() 
	{
		spel.speelSpel();
	}

	public void speelBeurt(String actie, int stapelNummer) 
	{
		spel.speelBeurt(actie, stapelNummer);
	}
  
	public List<Stapel> getStapelsHuidigeRonde() 
	{
		return spel.getStapelsHuidigeRonde();
	}
	
	public String getKaartVanSpelDeck() 
	{
		return spel.getKaartVanSpelDeck().getKleur();
	}
	
	public boolean isStapelVol(int stapelNummer) 
	{
		return spel.isStapelVol(stapelNummer);
	}
	
	public boolean isStapelLeeg(int stapelNummer) 
	{
		return spel.isStapelLeeg(stapelNummer);
	}
	
	public boolean isLaatsteRonde()
	{
		return spel.isLaatsteRonde();
	}
	
	public List<Integer> berekenScore()
	{
		return spel.berekenScore();
	}
	
	public void assignJoker(String speler, String nieuweKleur)
	{
		spel.assignJoker(speler, nieuweKleur);
	}
	
	public Speler[] getSpelers()
	{
		return spel.getSpelers().toArray(new Speler[0]);
	}
}
