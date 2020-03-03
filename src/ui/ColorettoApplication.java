package ui;

import domein.*;

import java.util.*;

public class ColorettoApplication 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		DomeinController dc = new DomeinController();
		boolean inputCorrect = false;
		int aantalSpelers;
		
		//String[] namen = new String[]{"foo", "bar", "col", "fons", "thijs"}; //Array voorbeeldnamen
		
		dc.startNieuwSpel();
		System.out.println("Welkom bij Coloretto!");
		do 
		{
			System.out.print("Met hoeveel spelers wenst u te spelen?: ");
			aantalSpelers = keyboard.nextInt();
			if (aantalSpelers == 4 || aantalSpelers == 5) 
			{
				inputCorrect = true;
			} 
			else 
			{
				System.err.println("Ongeldige hoeveelheid spelers, 4 of 5 nodig.");
			}
		} while(!inputCorrect);
		
		String[] namen = new String[aantalSpelers];
		for (int i = 0; i < aantalSpelers; i++) 
		{
			System.out.print("Wat is de naam van speler " + (i + 1) + "?: ");
			namen[i] = keyboard.next();
			keyboard.nextLine();
		}
		
		dc.maakSpelersAan(namen);
		dc.speelSpel();
		while(dc.getStapelsHuidigeRonde().size() != 0) 
		{
			System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en dit zijn de kaarten van elke speler:");
			for (String naam : namen) 
			{
				System.out.printf("%-10s", naam);
				ArrayList<String> kaarten = dc.getKaartenSpeler(naam);
				Set<String> distinct = new HashSet<>(kaarten);
				for (String s : distinct) 
				{
					System.out.printf("%-10s", s + ": " + Collections.frequency(kaarten, s));
				}
				System.out.println();
			}
			
			System.out.println("Stapels op het veld:");

			List<Stapel> stapels = dc.getStapelsHuidigeRonde();
			for(int i = 0; i < stapels.size(); i++) 
			{
				System.out.printf("%-10s",("Stapel " + (stapels.get(i).getNummer() + 1) + ":"));
				List<String> kleuren = new ArrayList<String>();
				for (int j = 0; j < stapels.get(i).getKaarten().size(); j++) 
				{
					kleuren.add(stapels.get(i).getKaarten().get(j).getKleur());
				}
				Set<String> distinct = new HashSet<>(kleuren);
				for (String s : distinct) 
				{
					System.out.printf("%-10s", s + ": " + Collections.frequency(kleuren, s));
				}
				System.out.println();
			}
			
			String actie;
			int stapelNummer = 0;
			inputCorrect = false;
			do 
			{
				System.out.print("Wat wil je doen? (nemen n of leggen l): ");
				actie = keyboard.next().toLowerCase().trim();
				if (actie.equals("leggen") || actie.equals("l")) 
				{
					System.out.println("Je hebt een kaart met kleur " + dc.getKaartVanSpelDeck() + " genomen.");
					System.out.print("Op welke stapel wil je hem leggen?: ");
					stapelNummer = keyboard.hasNextInt() ? keyboard.nextInt() : -1;
					if (stapelNummer < stapels.size() && stapelNummer > 0) 
					{
						if (dc.isStapelVol(stapelNummer - 1)) 
						{
							System.err.println("Er mogen geen kaarten meer bij deze stapel.");
						}
						else 
						{
							inputCorrect = true;
						}
					}
					else 
					{
						System.err.println("De stapel waarbij je een kaart wil leggen bestaat niet.");
						keyboard.next();
					}
				}
				else if(actie.equals("nemen") || actie.equals("n")) 
				{
					System.out.print("Welke stapel wil je nemen? (nummer): ");
					stapelNummer = keyboard.hasNextInt() ? keyboard.nextInt() : -1;
					if (stapelNummer < stapels.size() && stapelNummer > 0) 
					{
						if (dc.isStapelLeeg(stapelNummer - 1)) 
						{
							System.err.println("Je kan geen lege stapel nemen.");
						}
						else 
						{
							inputCorrect = true;
						}
					}
					else 
					{
						System.err.println("De stapel die je wilt nemen bestaat niet.");
						keyboard.next();
					}
				}
			} while(!inputCorrect);
			dc.speelBeurt(actie, stapelNummer - 1);
		}
		keyboard.close();
		System.out.println("Einde prototype");
	}
}