package domein;

public class Kaart {

	private String kleur;

	public Kaart(String kleur) {
		setKleur(kleur);
	}

	public String getKleur() {
		return kleur;
	}

	private void setKleur(String kleur) {
		this.kleur = kleur;
	}
}
