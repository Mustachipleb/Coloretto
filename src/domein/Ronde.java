package domein;

import java.util.*;

public class Ronde 
{

	private List<Stapel> stapels;
	
	public Ronde(int aantalSpelers) 
	{
		this.setDecks(new ArrayList<Stapel>());
		
		for (int i = 0; i < aantalSpelers; i++)
		{
			this.getStapels().add(new Stapel(i));
		}
	}
	
	public List<Stapel> getStapels() 
	{
		return stapels;
	}

	private void setDecks(List<Stapel> stapels) 
	{
		this.stapels = stapels;
	}

	public Stapel neemStapel(int stapelNummer) 
	{
		Stapel d = getStapels().get(stapelNummer);
		getStapels().remove(stapelNummer);
		return d;
	}
	
	public void legKaartBijStapel(int stapelNummer, Kaart k) 
	{
		getStapels().get(stapelNummer).voegKaartToe(k);
	}
	
	public boolean isStapelVol(int stapelNummer) 
	{
		return getStapels().get(stapelNummer).isVol();
	}
	
	public boolean isStapelLeeg(int stapelNummer) 
	{
		return getStapels().get(stapelNummer).isLeeg();
	}
}
