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
		System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en de volgende kaarten zijn op het veld:");
		for (String naam : namen) {
			System.out.printf("%-8s", naam);
			ArrayList<String> kaarten = dc.getKaartenSpeler(naam);
			Set<String> distinct = new HashSet<>(kaarten);
			for (String s : distinct) {
				System.out.printf("%-10s", s + ": " + Collections.frequency(kaarten, s));
			}
			System.out.println();
		}
		List<List<String>> stapels = dc.getStapelsHuidigeRonde();
		
		for(int i = 0; i <5; i++)
		{
			System.out.println("Stapel " + i+1);
			
			int aantalKaarten = stapels.get(i).size();
				for(int j=0; j<aantalKaarten; j++)	
				{
					System.out.printf("%-10s", stapels.get(i).get(j));
					
					
				}
				
		}
		
		
		
		
	}
}