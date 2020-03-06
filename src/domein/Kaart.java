package domein;

import java.util.List;

public class Kaart 
{

	private String kleur;

	public Kaart(String kleur) 
	{
		setKleur(kleur);
	}

	public String getKleur() 
	{
		return kleur;
	}

	private void setKleur(String kleur) 
	{
		this.kleur = kleur;
	}
	
	/* De methodes equals en hashCode worden hier geoverride, omdat de Set collectie (HashSet specifiek)
	 * geen duplicate objecten mag bevatten. Het probleem hierbij is dan dat twee Kaart objecten met dezelfde kleur,
	 * nog steeds verschillende instanties zijn, en desgevolgens beiden in de HashSet komen, wat we niet willen.
	 * 
	 * voorbeeld:
	 *     speler a heeft 2 rode kaarten, en dankzij deze overrides staat er rood:2 ipv rood:1, rood:1.
	 * 
	 * De implementatie bevindt zich in ColorettoApplicatie.printKaarten(List<Kaart> kaarten).
	 * */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null ||!(obj instanceof Kaart)) return false;
		if (obj == this) return true;
		return this.getKleur() == ((Kaart) obj).getKleur();
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 0;
		String kleur = getKleur();
		for (int i = 0; i < kleur.length(); i++) 
		{
			hashCode += Character.getNumericValue(kleur.charAt(i));
		}
		return hashCode;
	}
}
