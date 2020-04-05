package gui;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import domein.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
public class SpelScherm extends GridPane 
{
	private DomeinController dc = new DomeinController();
	private List<String> namen;
	private List<Speler> spelers;
	private GridPane grdSpelerInformatie;
	
	public SpelScherm(List<String> namen)
	{
		this.namen = namen;
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		this.spelers = dc.getSpelers();
		setUpWindow();
		updateSpelerKaarten();
	}
	
	private void setUpWindow()
	{
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		// Setup van linkerdeel van het scherm (Informatie over de spelers en hun kaarten.)
		GridPane grdSpel = new GridPane();
		GridPane grdInstructions = new GridPane();
		Label lblInstructions = new Label("Instructies");
		lblInstructions.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 2.5));
		grdInstructions.add(lblInstructions, 0, 0);
		Label lblaantalkaarten = new Label("Aantal Kaarten");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblaantalkaarten, 0, 2);
		Label lblnummer = new Label();
		lblnummer.setText("1\n2\n3\n4\n5\n6");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblnummer, 0, 3);
		Label lblpunten = new Label();
		lblpunten.setText("1\n3\n6\n10\n15\n21");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblpunten, 1, 3);
		Label lblpuntennaam = new Label("Punten");
		lblpuntennaam.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblpuntennaam, 1, 2);
		
		Label lblColoretto = new Label("Coloretto");
		lblColoretto.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 2.5));
		grdSpel.add(lblColoretto, 2, 0);
		
		
		// Setup van rechterdeel van het scherm (Informatie over de spelers en hun kaarten)
		grdSpelerInformatie = new GridPane();
		Label lblKaartenSpelers = new Label("Kaarten:");
		lblKaartenSpelers.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 1.7));
		grdSpelerInformatie.add(lblKaartenSpelers, 0, 0);
		int desiredPos = 1;
		for (int i = 0; i < namen.size(); i++)
		{
			Label lblSpelerNaam = new Label(String.format("%s:", namen.get(i)));
			Label lblSpelerScore = new Label(String.format("Score: %s", spelers.get(i).berekenScore()));
			FlowPane flwSpelerKaarten = new FlowPane();
			
			setHalignment(lblSpelerScore, HPos.RIGHT);
			lblSpelerScore.setMinWidth(50);
			
			grdSpelerInformatie.add(lblSpelerNaam, 0, desiredPos);
			grdSpelerInformatie.add(flwSpelerKaarten, 0, desiredPos + 1);
			grdSpelerInformatie.add(lblSpelerScore, 1, desiredPos);
			desiredPos += 2;
		}
		grdInstructions.setPrefSize(250, 500);
		grdSpel.add(grdInstructions, 0, 0);
		setHalignment(grdSpelerInformatie, HPos.RIGHT);
		grdSpelerInformatie.setPrefWidth(200);
		grdSpel.setPrefSize(1000, 700);
		this.add(grdSpel, 0, 0);
		this.add(grdSpelerInformatie, 1, 0);
		//TODO
	}
	
	private void updateSpelerKaarten()
	{
		List<Speler> spelers = dc.getSpelers();
		List<FlowPane> flwSpelerKaarten = grdSpelerInformatie.getChildren().stream()
				.filter(node -> node instanceof FlowPane) //Filter de lijst om alleen flowpanes te zien
				.map(node -> (FlowPane) node) //Cast de Node objecten naar FlowPane
				.collect(Collectors.toList()); //Collect het resultaat als List
		List<Label> lblSpelers = grdSpelerInformatie.getChildren().stream()
				.filter(node -> node instanceof Label)
				.map(node -> (Label) node)
				.collect(Collectors.toList());
		lblSpelers.remove(0);
		for (int i = 0; i < namen.size(); i++)
		{
			Speler s = spelers.get(i);
			if (s.equals(dc.getSpelerAanBeurt()))
			{
				lblSpelers.get(i + i).setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, Font.getDefault().getSize()));
			}
			else
			{
				lblSpelers.get(i + i).setFont(Font.font("Tahoma", FontWeight.NORMAL, Font.getDefault().getSize()));
			}
			List<Kaart> kaartenSpeler = s.getKaarten();
			Set<Kaart> distinct = new HashSet<Kaart>(kaartenSpeler);
			flwSpelerKaarten.get(i).getChildren().clear();
			for (Kaart k : distinct)
			{
				try
				{
					Image imgKaart = new Image(new FileInputStream(String.format("src/images/kaart-%s.png", k.getKleur())));
					ImageView imvKaart = new ImageView(imgKaart);
					imvKaart.setFitWidth(25);
					imvKaart.setPreserveRatio(true);
					flwSpelerKaarten.get(i).getChildren().add(imvKaart);
					flwSpelerKaarten.get(i).getChildren().add(new Label("x" + Collections.frequency(kaartenSpeler, k)));
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
