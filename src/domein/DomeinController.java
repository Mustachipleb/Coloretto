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
	
	public ArrayList<String> getKaartenSpeler(String naam) 
	{
		ArrayList<Kaart> kaarten = this.spel.getKaartenSpeler(naam);
		ArrayList<String> kaartenKleur = new ArrayList<String>();
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
}
