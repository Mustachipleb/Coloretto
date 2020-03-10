package domein;

import java.util.*;

public class Stapel 
{

	private List<Kaart> kaarten;
	private int nummer;
	private static final int MAX_SIZE = 3;
	
	public Stapel(int nummer) 
	{
		this.kaarten = new ArrayList<>();
		this.nummer = nummer;
	}

	public List<Kaart> getKaarten() 
	{
		return kaarten;
	}

	public boolean isVol() 
	{
		return getKaarten().size() == 3;
	}
	
	public boolean isLeeg() 
	{
		return getKaarten().isEmpty();
	}
	
	public void voegKaartToe(Kaart k) 
	{
		if (getKaarten().size() >= MAX_SIZE)
			throw new IllegalStateException("De stapel mag maar " + MAX_SIZE + " kaarten bevatten.");
		getKaarten().add(k);
	}

	public int getNummer() 
	{
		return nummer;
	}
}
