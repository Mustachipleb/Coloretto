package ui;

import domein.*;

import java.util.*;
import java.util.stream.Collectors;

public class ColorettoApplication 
{
	private static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		DomeinController dc = new DomeinController();
		boolean inputCorrect = false;
		int aantalSpelers;
		
		dc.startNieuwSpel();
		System.out.print("Welkom bij Coloretto! Wil je met 4 of 5 spelers spelen?: ");
		aantalSpelers = nextIntAndValidate(4, 5);
		
		List<String> namen = vraagNamen(aantalSpelers);
		
		dc.maakSpelersAan(namen);
		dc.speelSpel();
		while(!dc.getStapelsHuidigeRonde().isEmpty())
		{
			printTussenstatus(dc);

			List<Stapel> stapels = dc.getStapelsHuidigeRonde();
			String actie;
			int stapelNummer = 0;
			inputCorrect = false;
			do 
			{
				System.out.print("Wat wil je doen? (nemen n of leggen l): ");
				actie = keyboard.next().toLowerCase().trim();
				if ("leggen".equals(actie) || "l".equals(actie)) 
				{
					System.out.println("Je hebt een kaart met kleur " + dc.getKaartVanSpelDeck() + " genomen.");
					System.out.print("Op welke stapel wil je hem leggen?: ");
					stapelNummer = nextIntAndValidate(1, stapels.size());
					if (stapels.get(stapelNummer - 1).isVol())
					{
						System.err.println("Er mogen geen kaarten meer bij deze stapel.");
					}
					else 
					{
						inputCorrect = true;
					}
				}
				else if("nemen".equals(actie) || "n".equals(actie)) 
				{
					System.out.print("Welke stapel wil je nemen? (nummer): ");
					stapelNummer = keyboard.hasNextInt() ? keyboard.nextInt() : -1;
					if (stapelNummer <= stapels.size() && stapelNummer > 0) 
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
		for (Speler s : dc.getSpelers()) 
		{
			int aantalJokers = Collections.frequency(s.getKaarten(), new Kaart("joker"));
			if (aantalJokers > 0)
			{
				System.out.printf("%s, je hebt %s jokers:%n", s.getNaam(), aantalJokers);
				for (int i = 0; i < aantalJokers; i++) 
				{
					System.out.printf("Welke kleur wil ");
				}
			}
		}
		System.out.println("Einde prototype - Scores:");
		/**TODO: zie {@see domein.Speler#berekenScore()}*/
		List<Integer> scores = dc.berekenScore();
		/*for (int i = 0; i < namen.length; i++) 
		{
			System.out.println(namen[i] + ": ");
		}*/
	}
	
	private static int nextIntAndValidate()
	{
		int number;
		while (!keyboard.hasNextInt())
		{
			System.err.print("Dit is geen nummer, probeer opnieuw: ");
			keyboard.next();
		}
		number = keyboard.nextInt();
		return number;
	}
	
	private static int nextIntAndValidate(int lowerBound, int upperBound)
	{
		int number = nextIntAndValidate();
		while (number < lowerBound || number > upperBound)
		{
			System.err.printf("Geef een nummer tussen %s en %s in: ", lowerBound, upperBound);
			number = nextIntAndValidate(lowerBound, upperBound);
		}
		return number;
	}
	
	private static List<String> vraagNamen(int aantalSpelers)
	{
		List<String> namen = new ArrayList<>(aantalSpelers);
		keyboard.nextLine();
		for (int i = 0; i < aantalSpelers; i++) 
		{
			System.out.print("Wat is de naam van speler " + (i + 1) + "?: ");
			namen.add(keyboard.nextLine());
		}
		return namen;
	}
	
	private static String getFormatStringNamen(List<String> namen)
	{
		int maxLengte = 0;
		for (String s : namen)
		{
			maxLengte = s.length() > maxLengte ? s.length() : maxLengte;
		}
		maxLengte++;
		return ("%-" + maxLengte + "s");
	}
	
	private static void printTussenstatus(DomeinController dc)
	{
		List<Speler> spelers = new ArrayList<>(dc.getSpelers());
		List<String> namen = spelers.stream().map(s -> s.getNaam()).collect(Collectors.toList());
		System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en dit zijn de kaarten van elke speler:");
		for (Speler s : spelers)
		{
			System.out.printf(getFormatStringNamen(namen), s.getNaam());
			List<Kaart> kaarten = s.getKaarten();
			printKaarten(kaarten);
		}
		
		System.out.println("Stapels op het veld:");

		List<Stapel> stapels = dc.getStapelsHuidigeRonde();
		for(int i = 0; i < stapels.size(); i++) 
		{
			System.out.printf("%-10s",("Stapel " + (stapels.get(i).getNummer() + 1) + ":"));
			List<Kaart> kaarten = stapels.get(i).getKaarten();
			printKaarten(kaarten);
		}
	}
	
	private static void printKaarten(List<Kaart> kaarten)
	{
		Set<Kaart> distinct = new HashSet<>(kaarten);
		for (Kaart k : distinct)
		{
			System.out.printf("%-10s", k.getKleur() + ": " + Collections.frequency(kaarten, k));
		}
		System.out.println();
	}
}