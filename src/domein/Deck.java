package domein;

import java.util.*;

public class Deck {

	private List<Kaart> kaarten;
	
	public Deck() {
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
}
