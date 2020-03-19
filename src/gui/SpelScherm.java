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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelScherm extends GridPane 
{
	private DomeinController dc = new DomeinController();
	private List<String> namen;
	private GridPane grdSpelerInformatie;
	
	public SpelScherm(List<String> namen)
	{
		this.namen = namen;
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		setUpWindow();
		updateSpelerKaarten();
	}
	
	private void setUpWindow()
	{
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		GridPane grdSpel = new GridPane();
		
		grdSpelerInformatie = new GridPane();
		Label lblKaartenSpelers = new Label("Kaarten:");
		grdSpelerInformatie.add(lblKaartenSpelers, 0, 0);
		int desiredPos = 1;
		for (int i = 0; i < namen.size(); i++)
		{
			Label lblSpelerNaam = new Label(String.format("%s:", namen.get(i)));
			FlowPane flwSpelerKaarten = new FlowPane();
			grdSpelerInformatie.add(lblSpelerNaam, 0, desiredPos);
			grdSpelerInformatie.add(flwSpelerKaarten, 0, desiredPos + 1);
			desiredPos += 2;
		}
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
				.map(node -> (FlowPane)node) //Cast de Node objecten naar FlowPane
				.collect(Collectors.toList()); //Collect het resultaat als List
		for (int i = 0; i < namen.size(); i++)
		{
			Speler s = spelers.get(i);
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
