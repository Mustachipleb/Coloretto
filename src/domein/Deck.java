package domein;

import java.util.*;

public class Deck {

	private List<Kaart> kaarten;
	
	public Deck() {
		this.kaarten = new ArrayList<Kaart>();
	}

	public List<Kaart> getKaarten() {
		return kaarten;
	}

	public boolean isVol() {
		boolean isVol = false;
		if (getKaarten().size() == 3)
		{
			isVol = true;
		}
		return isVol;
	}
	
	public boolean isLeeg() {
		boolean isLeeg = false;
		if (getKaarten().size() == 0)
		{
			isLeeg = true;
		}
		return isLeeg;
	}
	
	public void voegKaartToe(Kaart k) {
		getKaarten().add(k);
	}
}
