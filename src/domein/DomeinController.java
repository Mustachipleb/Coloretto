package domein;

import java.util.*;

public class DomeinController {

	Spel spel;

	public DomeinController() 
	{
		
	}

	public void startNieuwSpel() {
		this.spel = new Spel();
	}

	public void maakSpelersAan(String[] namen) {
		spel.maakSpelersAan(namen);
	}
	
	public ArrayList<String> getKaartenSpeler(String naam) {
		ArrayList<Kaart> kaarten = this.spel.getKaartenSpeler(naam);
		ArrayList<String> kaartenKleur = new ArrayList<String>();
		for (Kaart k : kaarten) {
			kaartenKleur.add(k.getKleur());
		}
		return kaartenKleur;
	}

	public String getSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().getNaam();
	}

	public void speelSpel() {
		spel.speelSpel();
	}

	public void speelBeurt(String actie, int stapelNummer) {
		spel.speelBeurt(actie, stapelNummer);
	}
	
	public boolean isLaatsteBeurt() {
		return spel.isLaatsteBeurt();
	}
  
	public List<ArrayList<String>> getStapelsHuidigeRonde() {
		List<Deck> decks = spel.getStapelsHuidigeRonde();
		List<ArrayList<String>> decksOutput = new ArrayList<ArrayList<String>>();
		for (Deck d : decks) {
			List<Kaart> kaarten = d.getKaarten();
			ArrayList<String> kaartenOutput = new ArrayList<String>();
			for (Kaart k : kaarten) {
				kaartenOutput.add(k.getKleur());
			}
			decksOutput.add(kaartenOutput);
		}
		return decksOutput;
	}
}
