package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import domein.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Files not found");
			alert.setHeaderText(null);
			alert.setContentText("Could not load one or more images at src/images.");

			alert.showAndWait();
		}
		
		cardStacks = new ArrayList<CardStack>();
		playerCards = new ArrayList<SpelerKaartDisplay>();
		
		
		dc.startNieuwSpel();
		dc.maakSpelersAan(namen);
		this.spelers = dc.getSpelers();
		dc.startNieuweRonde();
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
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
	}
	
	public static Map<String, Image> getCardImages()
	{
		return cards;
	}
	
	public class CardStackEventHandler implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent event) 
		{
			CardStack stack = (CardStack) event.getSource();
			if (spelDeck.isCardDrawn())
			{
				Kaart card = spelDeck.peekCard();
				if (stack.tryAddCard(card))
				{
					dc.legKaartBijStapel(stack.getStackNumber());
					spelDeck.resetDrawableCard();
					boolean isDrawingAllowed = false;
					for (CardStack cardStack : cardStacks)
					{
						if (!cardStack.isFull())
						{
							isDrawingAllowed = true;
							break;
						}
					}
					spelDeck.setDrawingAllowed(isDrawingAllowed);
					//TODO fix drawingAllowed flag
				}
				else
				{
					//TODO: alert no card drawn
				}
			}
			else if (!stack.isEmpty())
			{
				//TODO: handle taking stack
				dc.geefStapelinhoudAanSpeler(stack.getStackNumber());
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
		}
	}
}
