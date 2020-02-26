package domein;

import java.util.*;

public class Deck {

	private List<Kaart> kaarten;
	private int maxAantalKaarten;
	
	public Deck(int maxAantalKaarten, int initAantalKaarten) {
		throw new UnsupportedOperationException();
	}
	
	public Deck(int initAantalKaarten) {
		List<String> kleuren = new ArrayList<String>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));
		kaarten = new ArrayList<Kaart>();
		// Voegt per kleur 9 kaarten toe aan de lijst van kaarten en shuffelt ze erna.
		for (String kleur : kleuren)
		{
			for (int i = 0; i < 9; i++)
			{
				Kaart k = new Kaart(kleur);
				kaarten.add(k);
			}
		}
		Collections.shuffle(kaarten);
	}
	
	public boolean isVol() {
		boolean isVol = false;
		if (kaarten.size() == maxAantalKaarten) 
		{
			isVol = true;
		}
		return isVol;
	}
}
