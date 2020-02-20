package domein;

import java.util.*;
import java.util.stream.Collectors;

public class DomeinController {

	Spel spel;

	public void startSpel(int aantalSpelers) {
		throw new UnsupportedOperationException();
	}

	public void startNieuwSpel() {
		this.spel = new Spel();
	}

	public void maakSpelersAan(int aantalSpelers, String[] namen) {
		spel.maakSpelersAan(aantalSpelers, namen);
	}

	public ArrayList<ArrayList<String>> getKaartenSpelers() {
		ArrayList<ArrayList<Kaart>> kaartenPerSpeler = spel.getKaartenSpelers();
		ArrayList<ArrayList<String>> kleurKaartenPerSpeler = new ArrayList<ArrayList<String>>();

		// Deze nested loop gaat over elke Kaart per Speler, en zet de kaarten om naar hun kleur als String.
		for (int i = 0; i < kaartenPerSpeler.size(); i++)
		{
			ArrayList<String> kleurKaarten = new ArrayList<String>();
			for (int j = 0; j < kaartenPerSpeler.get(i).size(); j++)
			{
				Kaart kaart = kaartenPerSpeler.get(i).get(j);
				kleurKaarten.add(kaart.getKleur());
			}
			kleurKaartenPerSpeler.add(kleurKaarten);
		}
		
		return kleurKaartenPerSpeler;
	}

	public String getSpelerAanBeurt() {
		return spel.getSpelerAanBeurt().getNaam();
	}

	public void speelSpel() {
		spel.speelSpel();
	}

	public void speelBeurt(String actie) {
		spel.speelBeurt(actie);
	}
}
