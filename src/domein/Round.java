package domein;

import java.util.*;

public class Round 
{
	private List<Stack> stapels = new ArrayList<>();
	private List<Player> spelersDieNogMogenSpelen = new ArrayList<>();
	
	public Round(List<Player> spelers)
	{
		spelersDieNogMogenSpelen.addAll(spelers);
		for (int i = 0; i < spelers.size(); i++)
		{
			this.getStapels().add(new Stack(i));
		}
	}
	
	public List<Stack> getStapels() 
	{
		return stapels;
	}

	public Stack neemStapel(Stack stapel)
	{
		Stack d = getStapels().get(getStapels().indexOf(stapel));
		getStapels().set(getStapels().indexOf(d), null);
		return d;
	}

	public void legKaartBijStapel(Stack stapel, Card kaart)
	{
		getStapels().get(getStapels().indexOf(stapel)).voegKaartToe(kaart);
	}

	public List<Player> getSpelersDieNogMogenSpelen()
	{
		return spelersDieNogMogenSpelen;
	}

	public boolean zijnAlleStapelsGenomen()
	{
		for (Stack s : getStapels())
		{
			if (s != null)
				return false;
		}
		return true;
	}
}
