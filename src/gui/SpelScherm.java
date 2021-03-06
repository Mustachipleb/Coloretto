package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import domein.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelScherm extends GridPane
{
	private DomeinController dc = new DomeinController();
	private List<CardStack> cardStacks;
	private SpelDeck spelDeck;
	private List<SpelerKaartDisplay> playerCards;
	
	private List<Speler> spelers;
	
	private static final Map<String, Image> cards = new HashMap<String, Image>();
	
	public SpelScherm(List<String> namen)
	{
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		dc.startNieuweRonde();
		initialise();
	}
	
	public SpelScherm(DomeinController dc)
	{
		this.dc = dc;
		initialise();
		resumeGame();
	}
	
	public static Map<String, Image> getCardImages()
	{
		return cards;
	}
	
	public void launchEndSequence()
	{
		for (int i = 0; i < 3; i++)
		{
			if (dc.getNextJokerOwner() != null)
			{
				Speler speler = dc.getNextJokerOwner();
				List<String> choices = new ArrayList<>();
				choices.add("Blauw");
				choices.add("Bruin");
				choices.add("Geel");
				choices.add("Grijs");
				choices.add("Groen");
				choices.add("Oranje");
				choices.add("Roze");
				
				ChoiceDialog<String> dialog = new ChoiceDialog<>("Blauw", choices);
				dialog.setTitle("Einde Spel");
				dialog.setHeaderText(String.format("%s, welke kleur wil je dat ��n van je jokers wordt?", speler.getNaam()));
				dialog.setContentText(null);

				Optional<String> result = dialog.showAndWait();
				result.ifPresent(nieuweKleur -> dc.assignJoker(speler, nieuweKleur.toLowerCase()));
			}
		}
		
		List<Speler> winnaars = new ArrayList<Speler>();
		int maxScore = 0;
		for (Speler speler : spelers)
		{
			if (speler.berekenScore() > maxScore)
			{
				winnaars.clear();
				winnaars.add(speler);
				maxScore = speler.berekenScore();
			}
			else if (speler.berekenScore() == maxScore)
			{
				winnaars.add(speler);
			}
		}
		
		if (winnaars.size() == 1)
		{
			spelDeck.setStatusMessage(String.format("%s won with %s points!!!", winnaars.get(0).getNaam(), winnaars.get(0).berekenScore()));
		}
		else
		{
			StringBuilder sb = new StringBuilder(String.format("With %s points, the following players won: ", winnaars.get(0).berekenScore()));
			for (int i = 0; i < winnaars.size(); i++)
			{
				sb.append(winnaars.get(i).getNaam());
				if (i < (winnaars.size() - 1))
					sb.append(", ");
				else if (i == (winnaars.size() - 1))
				{
					sb.append("!!!");
				}
			}
			spelDeck.setStatusMessage(sb.toString());
		}
	}
	
	public class CardStackEventHandler implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent event) 
		{
			CardStack stack = (CardStack) event.getSource();
			boolean isGameOver = false;
			if (spelDeck.isCardDrawn())
			{
				Kaart card = spelDeck.peekCard();
				if (stack.tryAddCard(card))
				{
					dc.legKaartBijStapel(stack.getStackNumber());
					spelDeck.resetDrawableCard();
				}
			}
			else if (!stack.isEmpty())
			{
				try
				{
					dc.geefStapelinhoudAanSpeler(stack.getStackNumber());
				}
				catch (IllegalStateException e)
				{
					launchEndSequence();
					isGameOver = true;
				}
				playerCards.forEach(cardDisplay -> cardDisplay.updateCards());
				stack.clear();
				boolean isRoundOver = true;
				for (CardStack cardStack : cardStacks)
				{
					if (!cardStack.hasBeenTaken())
					{
						isRoundOver = false;
						break;
					}
				}
				if (isRoundOver)
				{
					cardStacks.forEach(cardStack -> cardStack.resetContent());
				}
			}
			
			boolean isDrawingAllowed = false;
			for (CardStack cardStack : cardStacks)
			{
				if (!cardStack.isFull() && !cardStack.hasBeenTaken())
				{
					isDrawingAllowed = true;
					break;
				}
			}
			spelDeck.setDrawingAllowed(isDrawingAllowed);

			if (!isGameOver)
			{
				String spelerAanBeurt = dc.getSpelerAanBeurt().getNaam();
				spelDeck.setStatusMessage(String.format("It's %s turn.", spelerAanBeurt.endsWith("s") ? spelerAanBeurt + "'" : spelerAanBeurt + "'s"));
			}
			
			event.consume();
		}
	}
	
	private void resumeGame()
	{
		for (int i = 0; i < dc.getHuidigeRonde().getStapels().size(); i++)
		{
			Stapel stack = dc.getHuidigeRonde().getStapels().get(i);
			CardStack cs = cardStacks.get(i);
			for (Kaart k : stack.getKaarten())
			{
				cs.tryAddCard(k);
			}
		}
	}
	
	private void initialise()
	{
		try
		{
			cards.put("back", new Image(getClass().getResourceAsStream("/images/kaart-back.png")));
			cards.put("+2", new Image(getClass().getResourceAsStream("/images/kaart-+2.png")));
			cards.put("blauw", new Image(getClass().getResourceAsStream("/images/kaart-blauw.png")));
			cards.put("bruin", new Image(getClass().getResourceAsStream("/images/kaart-bruin.png")));
			cards.put("geel", new Image(getClass().getResourceAsStream("/images/kaart-geel.png")));
			cards.put("grijs", new Image(getClass().getResourceAsStream("/images/kaart-grijs.png")));
			cards.put("groen", new Image(getClass().getResourceAsStream("/images/kaart-groen.png")));
			cards.put("joker", new Image(getClass().getResourceAsStream("/images/kaart-joker.png")));
			cards.put("oranje", new Image(getClass().getResourceAsStream("/images/kaart-oranje.png")));
			cards.put("roze", new Image(getClass().getResourceAsStream("/images/kaart-roze.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fatal - Files not found");
			alert.setHeaderText(null);
			alert.setContentText("Could not load one or more images at src/images.");

			alert.showAndWait();
			Platform.exit();
		}
		
		setPrefSize(750, 650);

		cardStacks = new ArrayList<CardStack>();
		playerCards = new ArrayList<SpelerKaartDisplay>();
		this.spelers = dc.getSpelers();
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		Button Instructions = new Button("Instructies");
		this.add(Instructions, 3, 2);
		Instructions.setMinWidth(100);
		Instructions.setPrefSize(12, 20);
		Instructions.setOnMouseClicked((MouseEvent event) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("The 2 -5 players draw cards from a card supply in the middle of the table.\r\n" + 
					"During the game, the players try to specialize in a few colors, because at the end of \r\n" + 
					"the game, a player can score plus points for only 3 colors; the rest score minus points.\r\n" + 
					"The more cards a player has of a color, the more points he scores.\r\n" + 
					"The player with the most points wins.");

			Image image = new Image(getClass().getResourceAsStream("/images/kaart-punten.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(150);
			imageView.setPreserveRatio(true);
			alert.setGraphic(imageView);
			
			alert.showAndWait();
		});
		
		// Setup van linkerdeel van het scherm (Informatie over de spelers en hun kaarten.)
		GridPane grdSpel = new GridPane();
		spelDeck = new SpelDeck(dc.getSpelDeck());
		grdSpel.add(spelDeck, 1, 0);
		grdSpel.setPrefSize(800, 700);

		HBox hboxCardStacks = new HBox();
		for (int i = 0; i < spelers.size(); i++)
		{
			CardStack stack = new CardStack(i);
			stack.addEventHandler(MouseEvent.MOUSE_CLICKED, new CardStackEventHandler());
			cardStacks.add(stack);
			hboxCardStacks.getChildren().add(stack);
		}
		this.add(hboxCardStacks, 0, 1, 2, 1);
		
		VBox vboxPlayerCardDisplays = new VBox();
		vboxPlayerCardDisplays.setMaxWidth(220);
		vboxPlayerCardDisplays.setMinWidth(100);
		Label lblKaartenSpelers = new Label("Kaarten:");
		lblKaartenSpelers.setFont(Font.font("Tahoma", FontWeight.BOLD, Font.getDefault().getSize() * 1.7));
		vboxPlayerCardDisplays.getChildren().add(lblKaartenSpelers);
		for (Speler player : spelers)
		{
			SpelerKaartDisplay cardDisplay = new SpelerKaartDisplay(player);
			playerCards.add(cardDisplay);
			vboxPlayerCardDisplays.getChildren().add(cardDisplay);
		}
		this.add(vboxPlayerCardDisplays, 1, 0);
		
		this.add(grdSpel, 0, 0);
		
		String spelerAanBeurt = dc.getSpelerAanBeurt().getNaam();
		
		
		spelDeck.setStatusMessage(String.format("It's %s turn.", spelerAanBeurt.endsWith("s") ? spelerAanBeurt + "'" : spelerAanBeurt + "'s"));
	}
}
