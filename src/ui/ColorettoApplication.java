package ui;

import domein.DomeinController;
import java.util.*;

public class ColorettoApplication {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		DomeinController dc = new DomeinController();
		
		dc.startNieuwSpel();
		
		String[] namen = new String[]{"foo", "bar", "col", "fons", "thijs"};
		
		//TODO: lengte verwijderen want namen kent zijn eigen lengte toch.
		dc.maakSpelersAan(namen.length, namen);
		System.out.println(dc.getSpelerAanBeurt());
	}
}