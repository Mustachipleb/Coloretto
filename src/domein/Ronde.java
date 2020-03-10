package domein;

import java.util.*;

public class Ronde 
{
	private List<Stapel> stapels = new ArrayList<>();
	private List<Speler> spelersDieNogMogenSpelen = new ArrayList<>();
	
	public Ronde(List<Speler> spelers)
	{
		spelersDieNogMogenSpelen.addAll(spelers);
		for (int i = 0; i < spelers.size(); i++)
		{
			this.getStapels().add(new Stapel(i));
		}
	}
	
	public List<Stapel> getStapels() 
	{
		return stapels;
	}

	public Stapel neemStapel(Stapel stapel)
	{
		Stapel d = getStapels().get(getStapels().indexOf(stapel));
		getStapels().set(getStapels().indexOf(d), null);
		return d;
	}

	public void legKaartBijStapel(Stapel stapel, Kaart kaart)
	{
		getStapels().get(getStapels().indexOf(stapel)).voegKaartToe(kaart);
	}

	public List<Speler> getSpelersDieNogMogenSpelen()
	{
		return spelersDieNogMogenSpelen;
	}

	public boolean zijnAlleStapelsGenomen()
	{
		for (Stapel s : getStapels())
		{
			if (s != null)
				return false;
		}
		return true;
	}
}
