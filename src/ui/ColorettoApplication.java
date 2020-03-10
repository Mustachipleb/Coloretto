package ui;

import domein.*;

import java.util.*;
import java.util.stream.Collectors;

public class ColorettoApplication 
{
	private static Scanner keyboardInt = new Scanner(System.in);
	private static Scanner keyboardString = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		DomeinController dc = new DomeinController();
		int aantalSpelers;
		
		dc.startNieuwSpel();
		System.out.print("Welkom bij Coloretto! Wil je met 4 of 5 spelers spelen?: ");
		aantalSpelers = nextIntAndValidate(4, 5);
		
		List<String> namen = vraagNamen(aantalSpelers);
		
		dc.maakSpelersAan(namen);
		while (!dc.isLaatsteRonde())
		{
			dc.startNieuweRonde();
			while(!dc.getHuidigeRonde().zijnAlleStapelsGenomen())
			{
				printTussenstatus(dc);
				beurtLoop(dc);
			}
		}

		printTussenstatus(dc);
		for (Speler s : dc.getSpelers()) 
		{
			int aantalJokers = Collections.frequency(s.getKaarten(), new Kaart("joker"));
			if (aantalJokers > 0)
			{
				System.out.printf("%s, je hebt %s jokers:%n", s.getNaam(), aantalJokers);
				for (int i = 0; i < aantalJokers; i++) 
				{
					System.out.print("Welke kleur wil je dat joker " + (i + 1) + " wordt?: ");
					String nieuweKleur = keyboardString.nextLine().trim().toLowerCase();
					dc.assignJoker(s, nieuweKleur);
				}
			}
		}
		System.out.println("Einde prototype - Scores:");
		for (Speler s : dc.getSpelers())
		{
			System.out.printf(getFormatStringNamen(namen) + ": %s", s.getNaam(), s.berekenScore());
		}
	}
	
	private static int nextIntAndValidate()
	{
		int number;
		while (!keyboardInt.hasNextInt())
		{
			System.err.print("Dit is geen nummer, probeer opnieuw: ");
			keyboardInt.next();
		}
		number = keyboardInt.nextInt();
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
		for (int i = 0; i < aantalSpelers; i++) 
		{
			System.out.print("Wat is de naam van speler " + (i + 1) + "?: ");
			namen.add(keyboardString.nextLine());
		}
		return namen;
	}
	
	private static String getFormatStringNamen(List<String> namen)
	{
		int maxLengte = 0;
		for (String s : namen)
		{
			maxLengte = Math.max(s.length(), maxLengte);
		}
		maxLengte++;
		return ("%-" + maxLengte + "s");
	}
	
	private static void printTussenstatus(DomeinController dc)
	{
		List<Speler> spelers = new ArrayList<>(dc.getSpelers());
		List<String> namen = spelers.stream().map(Speler::getNaam).collect(Collectors.toList());
		System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en dit zijn de kaarten van elke speler:");
		for (Speler s : spelers)
		{
			System.out.printf(getFormatStringNamen(namen), s.getNaam());
			List<Kaart> kaarten = s.getKaarten();
			printKaarten(kaarten);
		}
		
		System.out.println("Stapels op het veld:");

		List<Stapel> stapels = dc.getHuidigeRonde().getStapels();
		for (Stapel stapel : stapels)
		{
			if (stapel != null)
			{
				System.out.printf("%-10s", ("Stapel " + (stapel.getNummer() + 1) + ":"));
				List<Kaart> kaarten = stapel.getKaarten();
				printKaarten(kaarten);
			}
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

	private static void beurtLoop(DomeinController dc)
	{
		List<Stapel> stapels = dc.getHuidigeRonde().getStapels();
		String actie;
		int stapelNummer;
		boolean inputCorrect = false;
		do
		{
			System.out.print("Wat wil je doen? (nemen n of leggen l): ");
			actie = keyboardString.nextLine().trim().toLowerCase();
			if ("leggen".equals(actie) || "l".equals(actie))
			{
				try
				{
					System.out.println("Je hebt een kaart met kleur " + dc.peekKaart().getKleur() + " genomen.");
					System.out.print("Op welke stapel wil je hem leggen?: ");
					stapelNummer = nextIntAndValidate(1, stapels.size());
					if (stapels.get(stapelNummer - 1).isVol())
					{
						System.err.println("Er mogen geen kaarten meer bij deze stapel.");
					}
					else
					{
						inputCorrect = true;
						dc.legKaartBijStapel(stapels.get(stapelNummer - 1));
					}
				}
				catch(NullPointerException e)
				{
					System.out.println("De stapel is al weggenomen, kies een andere stapel.");
				}
			}
			else if("nemen".equals(actie) || "n".equals(actie))
			{
				try 
				{
					System.out.print("Welke stapel wil je nemen? (nummer): ");
					stapelNummer = keyboardInt.hasNextInt() ? keyboardInt.nextInt() : -1;
					if (stapelNummer <= stapels.size() && stapelNummer > 0)
					{
						if (stapels.get(stapelNummer - 1).isLeeg())
						{
							System.err.println("Je kan geen lege stapel nemen.");
						}
						else
						{
							inputCorrect = true;
							dc.geefStapelinhoudAanSpeler(stapels.get(stapelNummer - 1));
						}
					}
					else
					{
						System.err.println("De stapel die je wilt nemen bestaat niet.");
					}
				}
				catch (NullPointerException e)
				{
					System.out.println("De stapels is er niet meer, kies een andere stapel.");
				}
			}
			else
			{
				System.err.println("Je moet ofwel leggen, l, nemen of n typen.");
			}
		} while(!inputCorrect);
	}
}