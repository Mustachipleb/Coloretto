package gui;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;

import domein.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;

public class SpelScherm extends GridPane 
{
	private DomeinController dc = new DomeinController();
	private List<String> namen;
	private List<Speler> spelers;
	private GridPane grdSpelerInformatie, grdSpeldeck;
	private boolean isCardDrawn = false;
	
	private List<StackPane> rondeStapels = new ArrayList<StackPane>();
	private ImageView imvDrawableCard;
	
	private final Map<String, Image> cards = new HashMap<String, Image>();
	
	public SpelScherm(List<String> namen)
	{
		try
		{
			cards.put("back", new Image(new FileInputStream("src/images/kaart-back.png")));
			cards.put("+2", new Image(new FileInputStream("src/images/kaart-+2.png")));
			cards.put("blauw", new Image(new FileInputStream("src/images/kaart-blauw.png")));
			cards.put("bruin", new Image(new FileInputStream("src/images/kaart-bruin.png")));
			cards.put("geel", new Image(new FileInputStream("src/images/kaart-geel.png")));
			cards.put("grijs", new Image(new FileInputStream("src/images/kaart-grijs.png")));
			cards.put("groen", new Image(new FileInputStream("src/images/kaart-groen.png")));
			cards.put("joker", new Image(new FileInputStream("src/images/kaart-joker.png")));
			cards.put("oranje", new Image(new FileInputStream("src/images/kaart-oranje.png")));
			cards.put("roze", new Image(new FileInputStream("src/images/kaart-roze.png")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			//TODO: Error bericht
		}
		
		for (int i = 0; i < namen.size(); i++)
		{
			StackPane stckStapel = new StackPane();
			stckStapel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event)
				{
					if (isCardDrawn)
					{
						for (int i = 2; i >= 0; i--)
						{
							StackPane stckCard = (StackPane) stckStapel.getChildren().get(i);
							ImageView card = (ImageView) stckCard.getChildren().get(0);
							if (card.getImage().equals(cards.get("back")) && isCardDrawn)
							{
								isCardDrawn = false;
								card.setImage(cards.get(dc.peekKaart().getKleur()));
								updateSpelerKaarten();
							}
						}
						
						for (int i = 0; i < rondeStapels.size(); i++)
						{
							if (rondeStapels.get(i).equals(stckStapel))
							{
								try
								{
									dc.legKaartBijStapel(i);
								}
								catch (IllegalStateException e)
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Ooops, there was an error!");

									alert.showAndWait();
								}
								catch (NullPointerException e)
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Stapel Bestaat Niet");

									alert.showAndWait();
								}
							}
						}
					}
					else
					{
						for (int i = 0; i < rondeStapels.size(); i++)
						{
							if (rondeStapels.get(i).equals(stckStapel))
							{
								try
								{
									dc.geefStapelinhoudAanSpeler(i);
									for (Node nCard : stckStapel.getChildren())
									{
										StackPane stckCard = (StackPane) nCard;
										ImageView imvCard = (ImageView) stckCard.getChildren().get(0);
										imvCard.setImage(cards.get("back"));
									}
								}
								catch (IllegalStateException e)
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Ooops, there was an error!");

									alert.showAndWait();
								}
							}
						}
					}
					event.consume();
				}
			});
			stckStapel.setMinWidth(120);
			stckStapel.setPrefWidth(150);
			stckStapel.setMaxWidth(150);
			int xOffset = 0;
			for (int j = 0; j < 3; j++)
			{
				ImageView imvCardBack = new ImageView(cards.get("back"));
				imvCardBack.setFitWidth(60);
				imvCardBack.setPreserveRatio(true);
				StackPane stckCard = new StackPane(imvCardBack);
				stckCard.setPadding(new Insets(0, xOffset, 0, 0));
				xOffset += 80;
				stckStapel.getChildren().add(stckCard);
			}
			rondeStapels.add(stckStapel);
		}
		this.namen = namen;
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		this.spelers = dc.getSpelers();
		dc.startNieuweRonde();
		setUpWindow();
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
		Label lblColoretto = new Label("Coloretto");
		lblColoretto.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 2.5));
		grdInstructions.add(lblColoretto, 0, 0);
		
		Label lblInstructies = new Label();
		lblInstructies.setText(
			"The 2 -5 players draw cards from a card supply in the middle of the table.\n"
			+ "During the game, the players try to specialize in a few colors, because at the end of "
			+ "the game, a player can score plus points for only 3 colors; the rest score minus points.\n"
			+ "The more cards a player has of a color, the more points he scores. "
			+ "The player with the most points wins."
		);

		/*Label lblInstructions = new Label("Instructies");
		lblInstructions.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 2.5));
		grdInstructions.add(lblInstructions, 0, 0);
		Label lblaantalkaarten = new Label("Aantal Kaarten");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.0));
		grdInstructions.add(lblaantalkaarten, 0, 3);
		Label lblnummer = new Label();
		lblnummer.setText("1\n2\n3\n4\n5\n6");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblnummer, 0, 4);
		Label lblpunten = new Label();
		lblpunten.setText("1\n3\n6\n10\n15\n21");
		lblaantalkaarten.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		grdInstructions.add(lblpunten, 1, 4);
		Label lblpuntennaam = new Label("Punten");
		lblpuntennaam.setFont(Font.font("Tahoma", Font.getDefault().getSize() * 1.2));
		lblpuntennaam.setMinWidth(60);
		grdInstructions.add(lblpuntennaam, 1, 3);

		grdInstructions.setMaxWidth(240);
		lblInstructies.setWrapText(true);
		grdInstructions.add(lblInstructies, 0, 1);
		
		HBox hboxStapels = new HBox();
		//hboxStapels.setPrefWidth(800);
		grdSpel.add(hboxStapels, 0, 3);
		//grdInstructions.add(lblpuntennaam, 1, 2);*/
		
		grdInstructions.setMaxWidth(200);
		lblInstructies.setWrapText(true);
		grdInstructions.add(lblInstructies, 0, 1);
		
		grdSpeldeck = new GridPane();
		updateSpeldeck();
		grdSpel.add(grdSpeldeck, 1, 0);
		
		HBox hboxStapels = new HBox();
		//hboxStapels.setPrefWidth(800);
		grdSpel.add(hboxStapels, 0, 2, 2, 1);
		updateRondeStapels(hboxStapels);
		
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
		grdSpel.setPrefSize(800, 700);
		
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
				ImageView imvKaart = new ImageView(cards.get(k.getKleur()));
				imvKaart.setFitWidth(25);
				imvKaart.setPreserveRatio(true);
				flwSpelerKaarten.get(i).getChildren().add(imvKaart);
				flwSpelerKaarten.get(i).getChildren().add(new Label("x" + Collections.frequency(kaartenSpeler, k)));
			}
		}
	}
	
	private void updateRondeStapels(HBox hboxStapels)
	{
		hboxStapels.getChildren().clear();
		for (int i = 0; i < rondeStapels.size(); i++)
		{
			hboxStapels.getChildren().add(rondeStapels.get(i));
		}
	}
	
	private void updateSpeldeck()
	{
		GridPane grdSpeldeckWrapper = new GridPane();
		setHalignment(grdSpeldeckWrapper, HPos.CENTER);
		Image imgCardBack = cards.get("back");
		imvDrawableCard = new ImageView(imgCardBack);
		ImageView imvCardBack = new ImageView(imgCardBack);
		Label lblCardCount = new Label(String.format("(%s)", dc.getAantalKaartenOpSpelDeck()));
		setHalignment(lblCardCount, HPos.CENTER);
		imvCardBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!isCardDrawn) 
		    	{
					imvDrawableCard.setImage(cards.get(dc.peekKaart().getKleur()));
					isCardDrawn = true;
				}
		    	event.consume();
		    }
		});
		imvDrawableCard.setFitWidth(170);
		imvDrawableCard.setPreserveRatio(true);
		imvCardBack.setFitWidth(170);
		imvCardBack.setPreserveRatio(true);
		grdSpeldeckWrapper.add(imvDrawableCard, 0, 0);
		grdSpeldeckWrapper.add(imvCardBack, 1, 0);
		grdSpeldeckWrapper.add(lblCardCount, 0, 1);
		grdSpeldeck.add(grdSpeldeckWrapper, 0, 0);
	}
}
