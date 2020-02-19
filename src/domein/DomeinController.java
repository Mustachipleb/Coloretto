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
		for (ArrayList<Kaart> kaarten : kaartenPerSpeler)
		{
			List<String> kleurKaarten = kaarten.stream()
				    .map(Kaart::getKleur)
				    .collect(Collectors.toList());
			kleurKaartenPerSpeler.add((ArrayList<String>)kleurKaarten);
		}
		return kleurKaartenPerSpeler;
	}

	public String getSpelerAanBeurt() {
		throw new UnsupportedOperationException();
	}

	public void speelSpel() {
		throw new UnsupportedOperationException();
	}

	public void speelBeurt(String actie) {
		throw new UnsupportedOperationException();
	}
}
