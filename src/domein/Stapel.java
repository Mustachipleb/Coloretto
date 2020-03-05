package domein;

import java.util.*;

public class Stapel 
{

	private List<Kaart> kaarten;
	private int nummer;
	
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
		getKaarten().add(k);
	}

	public int getNummer() 
	{
		return nummer;
	}
}
