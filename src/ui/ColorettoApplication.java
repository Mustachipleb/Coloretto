package ui;

import domein.DomeinController;
import java.util.*;

public class ColorettoApplication {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		DomeinController dc = new DomeinController();
		boolean inputCorrect = false;
		int aantalSpelers;
		
		//String[] namen = new String[]{"foo", "bar", "col", "fons", "thijs"}; //Array voorbeeldnamen
		
		dc.startNieuwSpel();
		System.out.println("Welkom bij Coloretto!");
		do {
			System.out.print("Met hoeveel spelers wenst u te spelen?: ");
			aantalSpelers = keyboard.nextInt();
			if (aantalSpelers == 4 || aantalSpelers == 5) {
				inputCorrect = true;
			} else {
				System.err.println("Ongeldige hoeveelheid spelers, 4 of 5 nodig.");
			}
		} while(!inputCorrect);
		
		String[] namen = new String[aantalSpelers];
		for (int i = 0; i < aantalSpelers; i++) {
			System.out.print("Wat is de naam van speler " + (i + 1) + "?: ");
			namen[i] = keyboard.next();
			keyboard.nextLine();
		}
		
		dc.maakSpelersAan(namen);
		dc.speelSpel();
		while(dc.getStapelsHuidigeRonde().size() != 0) {
			System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en dit zijn de kaarten van elke speler:");
			for (String naam : namen) {
				System.out.printf("%-8s", naam);
				ArrayList<String> kaarten = dc.getKaartenSpeler(naam);
				Set<String> distinct = new HashSet<>(kaarten);
				for (String s : distinct) {
					System.out.printf("%-10s", s + ": " + Collections.frequency(kaarten, s));
				}
				System.out.println();
			}
			
			System.out.println("Stapels op het veld:");

			List<ArrayList<String>> stapels = dc.getStapelsHuidigeRonde();
			for(int i = 0; i < stapels.size(); i++)
			{
				System.out.print("Stapel " + (i + 1));
				ArrayList<String> kaarten = stapels.get(i);
				Set<String> distinct = new HashSet<>(kaarten);
				for (String s : distinct) {
					System.out.printf("%-10s", s + ": " + Collections.frequency(kaarten, s));
				}
				System.out.println();
			}
			
			String actie;
			int stapelNummer = 0;
			inputCorrect = false;
			do {
				System.out.print("Wat wil je doen? (nemen of leggen): ");
				actie = keyboard.nextLine();
				if (actie.equals("leggen")) {
					System.out.println("Je hebt een kaart met kleur " + dc.getKaartVanSpelDeck() + " genomen.");
					System.out.print("Op welke stapel wil je hem leggen?: ");
					stapelNummer = keyboard.nextInt();
					if (dc.isStapelVol(stapelNummer - 1)) {
						System.err.println("Er mogen geen kaarten meer bij deze stapel.");
					}
					else {
						inputCorrect = true;
					}
				}
				else if(actie.equals("nemen")) {
					System.out.print("Welke stapel wil je nemen? (nummer): ");
					stapelNummer = keyboard.nextInt();
					if (dc.isStapelLeeg(stapelNummer - 1)) {
						System.err.println("Je kan geen lege stapel nemen.");
					}
					else {
						inputCorrect = true;
					}
				}
			} while(!inputCorrect);
			dc.speelBeurt(actie, stapelNummer - 1);
		}
		System.out.println("Einde prototype");
	}
}