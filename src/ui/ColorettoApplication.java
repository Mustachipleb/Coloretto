package ui;

import domein.*;

import java.util.*;

public class ColorettoApplication 
{
	private static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		//Scanner keyboard = new Scanner(System.in);
		DomeinController dc = new DomeinController();
		String[] namen;
		boolean inputCorrect = false;
		int aantalSpelers;
		
		//String[] namen = new String[]{"foo", "bar", "col", "fons", "thijs"}; //Array voorbeeldnamen
		
		dc.startNieuwSpel();
		System.out.print("Welkom bij Coloretto! Wil je met 4 of 5 spelers spelen?: ");
		aantalSpelers = nextIntAndValidate(4, 5);
		
		String[] namen = vraagNamen(aantalSpelers);
		
		dc.maakSpelersAan(namen);
		dc.speelSpel();
		while(!dc.getStapelsHuidigeRonde().isEmpty())
		{
				System.out.println(dc.getSpelerAanBeurt() + " is aan beurt, en dit zijn de kaarten van elke speler:");
				for (String naam : namen) 
				{
					System.out.printf(getFormatStringLangsteString(namen), naam)
					List<String> kaarten = dc.getKaartenSpeler(naam);
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
					List<String> kleuren = new ArrayList<>();
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
						if (stapelNummer <= stapels.size() && stapelNummer > 0) 
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
		for (int i = 0; i < namen.length; i++) 
		{
			System.out.println(namen[i] + ": ");
		}
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
		int number;
		while (!keyboard.hasNextInt())
		{
			System.err.print("Dit is geen nummer, probeer opnieuw: ");
			keyboard.next();
		}
		number = keyboard.nextInt();
		while (number < lowerBound || number > upperBound)
		{
			System.err.printf("Geef een nummer tussen %s en %s in: ", lowerBound, upperBound);
			number = nextIntAndValidate(lowerBound, upperBound);
		}
		return number;
	}
	
	private static String[] vraagNamen(int aantalSpelers)
	{
		String[] namen = new String[aantalSpelers];
		keyboard.nextLine();
		for (int i = 0; i < aantalSpelers; i++) 
		{
			System.out.print("Wat is de naam van speler " + (i + 1) + "?: ");
			namen[i] = keyboard.nextLine();
		}
		return namen;
	}
	
	private static String getFormatStringLangsteString(String[] strings)
	{
		int maxLengte = 0;
		for (String s : strings)
		{
			maxLengte = s.length() > maxLengte ? s.length() : maxLengte;
		}
		maxLengte++;
		return ("%-" + maxLengte + "s");
	}
	
}