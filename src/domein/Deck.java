package domein;

import java.util.*;

public class Deck {

	private List<Kaart> kaarten;
	private int maxAantalKaarten;
	
	public Deck(int maxAantalKaarten, int initAantalKaarten) {
		this(initAantalKaarten);
		this.maxAantalKaarten = maxAantalKaarten;
	}
	
	public Deck(int initAantalKaarten) {
		if (initAantalKaarten != 0) {
			List<String> kleuren = new ArrayList<String>(Arrays.asList("oranje", "blauw", "bruin", "geel", "grijs", "groen", "roze"));
			kaarten = new ArrayList<Kaart>();
			// Voegt per kleur 9 kaarten toe aan de lijst van kaarten en shuffelt ze erna.
			for (String kleur : kleuren)
			{
				for (int i = 0; i < 9; i++)
				{
					Kaart k = new Kaart(kleur);
					getKaarten().add(k);
				}
			}
			Collections.shuffle(getKaarten());
		}
	}

	public List<Kaart> getKaarten() {
		return kaarten;
	}

	public boolean isVol() {
		boolean isVol = false;
		if (getKaarten().size() == maxAantalKaarten) 
		{
			isVol = true;
		}
		return isVol;
	}
	
	public void shuffle() {
		Collections.shuffle(getKaarten());
	}
	
	public void voegKaartToe(Kaart kaart) {
		getKaarten().add(kaart);
	}
	
	public Kaart popKaart() {
		int indexLaatsteKaart = getKaarten().size() - 1;
		Kaart k = getKaarten().get(indexLaatsteKaart);
		getKaarten().remove(indexLaatsteKaart);
		return k;
	}
}
