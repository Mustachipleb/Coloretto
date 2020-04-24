package gui;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashMap;
import java.util.stream.Collectors;

import domein.*;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelScherm extends GridPane 
{
	private DomeinController dc = new DomeinController();
	private List<String> namen;
	private List<Speler> spelers;
	private GridPane grdSpelerInformatie;
	private GridPane grdSpeldeck;
	private boolean isCardDrawn = false;
	
	private List<StackPane> rondeStapelsStacks = new ArrayList<StackPane>();
	private ImageView imvDrawableCard;
	private HBox hboxStapels = new HBox();
	
	private final Map<String, Image> cards = new HashMap<String, Image>();
	
	private List<Label> spelerNaamLabels = new ArrayList<Label>();
	private List<Label> spelerScoreLabels = new ArrayList<Label>();
	
	private boolean areAllDecksFull = false;
	private boolean isRoundOver = false;
	private List<Stapel> stapels = new ArrayList<Stapel>();
	private Label lblStatusMsg;
	
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Files not found");
			alert.setHeaderText(null);
			alert.setContentText("Could not load one or more images at src/images.");

			alert.showAndWait();
		}
		
		for (int i = 0; i < namen.size(); i++)
		{
			StackPane stckStapel = new StackPane();
			stckStapel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event)
				{
					StackPane stckCard = (StackPane) stckStapel.getChildren().get(0);
					ImageView card = (ImageView) stckCard.getChildren().get(0);
					if (card.getImage() == null)
					{
						event.consume();
						return;
					}
					if (isCardDrawn)
					{
						for (int i = 2; i >= 0; i--)
						{
							stckCard = (StackPane) stckStapel.getChildren().get(i);
							card = (ImageView) stckCard.getChildren().get(0);
							if (card.getImage().equals(cards.get("back")) && isCardDrawn)
							{
								isCardDrawn = false;
								card.setImage(cards.get(dc.peekKaart().getKleur()));
								imvDrawableCard.setImage(cards.get("back"));
							}
						}
						
						for (int i = 0; i < rondeStapelsStacks.size(); i++)
						{
							if (rondeStapelsStacks.get(i).equals(stckStapel))
							{
								try
								{
									dc.legKaartBijStapel(i);
								}
								catch (IllegalStateException e)
								{
									imvDrawableCard.setImage(cards.get(dc.peekKaart().getKleur()));
									isCardDrawn = true;
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Ooops, there was an error!");

									alert.showAndWait();
									event.consume();
									return;
								}
								catch (NullPointerException e)
								{
									isCardDrawn = true;
									imvDrawableCard.setImage(cards.get(dc.peekKaart().getKleur()));
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Stapel Bestaat Niet");

									alert.showAndWait();
									event.consume();
									return;
								}
							}
						}
					}
					else
					{
						for (int i = 0; i < rondeStapelsStacks.size(); i++)
						{
							if (rondeStapelsStacks.get(i).equals(stckStapel) && !dc.getHuidigeRonde().getStapels().get(i).isLeeg())
							{
								try
								{
									dc.geefStapelinhoudAanSpeler(i);
									
									for (Node nCard : rondeStapelsStacks.get(i).getChildren()) 
									{
										stckCard = (StackPane) nCard;
										ImageView imvCard = (ImageView) stckCard.getChildren().get(0);
										imvCard.setImage(null);
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
								catch (NullPointerException e)
								{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Error Dialog");
									alert.setHeaderText("Look, an Error Dialog");
									alert.setContentText("Stapel Bestaat Niet");

									alert.showAndWait();
								}
							}
							else if (rondeStapelsStacks.get(i).equals(stckStapel) && isCardDrawn)
							{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error Dialog");
								alert.setHeaderText("Look, an Error Dialog");
								alert.setContentText("Stapel Is Leeg");

								alert.showAndWait();
							}
						}
					}
					List<Stapel> rondeStapels = dc.getHuidigeRonde().getStapels();
					stapels.clear();
					int fullDecks = 0;
					for (int i = 0; i < rondeStapels.size(); i++)
					{
						if (rondeStapels.get(i) != null)
						{
							stapels.add(rondeStapels.get(i));
							if (rondeStapels.get(i).isVol())
							{
								fullDecks++;
							}
						}
					}
					areAllDecksFull = stapels.size() == fullDecks ? true : false;
					if (stapels.isEmpty())
					{
						isRoundOver = true;
						areAllDecksFull = false;
						try
						{
							dc.startNieuweRonde();
						}
						catch (IllegalStateException e)
						{
							//TODO: Einde spel
							List<Speler> winnaars = new ArrayList<Speler>();
							int maxScore = 0;
							for (Speler s : spelers)
							{
								for (int i = 0; i < s.getKaarten().size(); i++)
								{
									Kaart k = s.getKaarten().get(i);
									if (k.getKleur().equals("joker"))
									{
										List<String> choices = new ArrayList<>();
										choices.add("Blauw");
										choices.add("Bruin");
										choices.add("Geel");
										choices.add("Grijs");
										choices.add("Groen");
										choices.add("Oranje");
										choices.add("Roze");

										ChoiceDialog<String> dialog = new ChoiceDialog<>("Blauw", choices);
										dialog.setTitle("Keuze Joker");
										dialog.setHeaderText(s.getNaam() + ", wat voor kleur wil je voor je joker?");
										dialog.setContentText("Kleur:");
										
										Optional<String> result = dialog.showAndWait();
										result.ifPresent(kleur -> dc.assignJoker(s, kleur.toLowerCase()));
									}
								}
								
								if (s.berekenScore() > maxScore)
								{
									winnaars.clear();
									winnaars.add(s);
									maxScore = s.berekenScore();
								}
								else if (s.berekenScore() == maxScore)
								{
									winnaars.add(s);
								}
							}
							
							if (winnaars.size() == 1)
							{
								lblStatusMsg.setText(String.format("%s is de winnaar met %s punten!", winnaars.get(0).getNaam(), winnaars.get(0).berekenScore()));
							}
							else
							{
								StringBuilder sb = new StringBuilder(String.format("Met %s punten zijn volgende spelers gewonnen: ", winnaars.get(0).berekenScore()));
								for (int i = 0; i < winnaars.size(); i++)
								{
									sb.append(winnaars.get(i).getNaam());
									if (i < (winnaars.size() - 1))
										sb.append(", ");
									else if (i == (winnaars.size() - 1))
									{
										sb.append(".");
									}
								}
							}
							event.consume();
							return;
						}
						updateRondeStapels();
					}
					updateSpelerKaarten();
					updateSpeldeck();
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
			rondeStapelsStacks.add(stckStapel);
		}
		this.namen = namen;
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		this.spelers = dc.getSpelers();
		dc.startNieuweRonde();
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
		Label lblColoretto = new Label("Coloretto");
		lblColoretto.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 2.5));
		grdInstructions.add(lblColoretto, 0, 0);
		
		Label lblInstructies = new Label();
		lblInstructies.setText(
			"De 4 of 5 spelers nemen kaarten van het speldeck in het midden.\n"
			+ "Tijdens het spel proberen de spelers te gaan voor een paar kleuren, omdat een speler "
			+ "op het einde van het spel voor maar 3 kleuren punten kan verdienen; de rest kost alleen punten.\n"
			+ "Hoe meer kaarten van 1 kleur, hoe meer punten. "
			+ "De speler met de meeste punten wint."
		);
		
		ImageView imvSummary = new ImageView();
		try
		{		
			imvSummary.setImage(new Image(new FileInputStream("src/images/kaart-punten.png")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		imvSummary.setFitWidth(180);
		imvSummary.setPreserveRatio(true);
		grdInstructions.setMaxWidth(200);
		lblInstructies.setWrapText(true);
		grdInstructions.add(lblInstructies, 0, 1);
		grdInstructions.add(imvSummary, 0, 2);
		
		grdSpeldeck = new GridPane();
		updateSpeldeck();
		grdSpel.add(grdSpeldeck, 1, 0);
		
		//hboxStapels.setPrefWidth(800);
		grdSpel.add(hboxStapels, 0, 2, 2, 1);
		updateRondeStapels();
		
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
			spelerNaamLabels.add(lblSpelerNaam);
			spelerScoreLabels.add(lblSpelerScore);
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
			spelerScoreLabels.get(i).setText(String.format("score: %s", spelers.get(i).berekenScore()));
			Speler s = spelers.get(i);
			if (s.equals(dc.getSpelerAanBeurt()))
			{
				spelerNaamLabels.get(i).setText(String.format("->%s:", namen.get(i)));
				lblSpelers.get(i + i).setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, Font.getDefault().getSize()));
			}
			else
			{
				spelerNaamLabels.get(i).setText(String.format("%s:", namen.get(i)));
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
	
	private void updateRondeStapels()
	{
		if (stapels.isEmpty() && isRoundOver)
		{
			for (int i = 0; i < rondeStapelsStacks.size(); i++)
			{
				for (Node nCard : rondeStapelsStacks.get(i).getChildren()) 
				{
					StackPane stckCard = (StackPane) nCard;
					ImageView imvCard = (ImageView) stckCard.getChildren().get(0);
					imvCard.setImage(cards.get("back"));
				}
			}
		}
		hboxStapels.getChildren().clear();
		for (int i = 0; i < rondeStapelsStacks.size(); i++)
		{
			hboxStapels.getChildren().add(rondeStapelsStacks.get(i));
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
		    	if (!isCardDrawn && !areAllDecksFull) 
		    	{
					imvDrawableCard.setImage(cards.get(dc.peekKaart().getKleur()));
					isCardDrawn = true;
					lblStatusMsg.setText(
							String.format("%s heeft een kaart genomen, en moet ze leggen bij een stapel.",
							dc.getSpelerAanBeurt().getNaam()	
					));
				}
		    	event.consume();
		    }
		});
		imvDrawableCard.setFitWidth(170);
		imvDrawableCard.setPreserveRatio(true);
		imvCardBack.setFitWidth(170);
		imvCardBack.setPreserveRatio(true);
		StringBuilder sb = new StringBuilder(String.format("%s is aan beurt, ", dc.getSpelerAanBeurt().getNaam()));
		if (isCardDrawn)
		{
			sb.append("en moet een kaart leggen bij een stapel.");
		}
		else if (!isCardDrawn && areAllDecksFull)
		{
			sb.append("en moet een stapel nemen.");
		}
		else
		{
			sb.append("en moet ofwel een kaart nemen van het speldeck of een stapel nemen.");
		}
		lblStatusMsg = new Label(sb.toString());
		lblStatusMsg.setWrapText(true);
		grdSpeldeckWrapper.add(imvDrawableCard, 0, 0);
		grdSpeldeckWrapper.add(imvCardBack, 1, 0);
		grdSpeldeckWrapper.add(lblCardCount, 0, 1);
		grdSpeldeckWrapper.add(lblStatusMsg, 0, 2, 2, 2);
		grdSpeldeck.getChildren().clear();
		grdSpeldeck.add(grdSpeldeckWrapper, 0, 0);
	}
}
