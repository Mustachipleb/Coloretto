package gui;

import java.util.List;

import domein.Card;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpelDeck extends VBox
{
	private List<Card> deckContents;
	
	private HBox hbCards;
	private Label lblCardCount;
	private Label lblStatusMessage;
	private ImageView imvCardBack;
	private ImageView imvDrawableCard;
	
	private boolean isCardDrawn;
	private boolean isDrawingAllowed;
	private static final int CARD_WIDTH = 200;
	
	public SpelDeck(List<Card> deckContents)
	{
		this.deckContents = deckContents;
		isDrawingAllowed = true;
		
		lblCardCount = new Label(String.format("%s cards left.", deckContents.size()));
		lblStatusMessage = new Label();
		hbCards = new HBox();
		
		imvCardBack = new ImageView(SpelScherm.getCardImages().get("back"));
		imvDrawableCard = new ImageView(SpelScherm.getCardImages().get("back"));
		imvDrawableCard.setFitWidth(CARD_WIDTH);
		imvCardBack.setFitWidth(CARD_WIDTH);
		imvDrawableCard.setPreserveRatio(true);
		imvCardBack.setPreserveRatio(true);
		

		imvCardBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!isCardDrawn && isDrawingAllowed)
		    	{
					imvDrawableCard.setImage(SpelScherm.getCardImages().get(deckContents.get(0).getKleur()));
					isCardDrawn = true;
					/*
					 * lblStatusMsg.setText(
					 * String.format("%s heeft een kaart genomen, en moet ze leggen bij een stapel."
					 * , dc.getSpelerAanBeurt().getNaam() ));
					 */
				}
		    	event.consume();
		    }
		});
		
		hbCards.getChildren().add(imvDrawableCard);
		hbCards.getChildren().add(imvCardBack);
		this.getChildren().add(hbCards);
		this.getChildren().add(lblCardCount);
		this.getChildren().add(lblStatusMessage);
	}
	
	public boolean isCardDrawn()
	{
		return isCardDrawn;
	}
	
	public Card peekCard()
	{
		return deckContents.get(0);
	}
	
	public void resetDrawableCard()
	{
		imvDrawableCard.setImage(SpelScherm.getCardImages().get("back"));
		if (deckContents.size() < 15)
			lblCardCount.setText(String.format("%s cards left. last round of the game!", deckContents.size()));
		else
			lblCardCount.setText(String.format("%s cards left.", deckContents.size()));
		isCardDrawn = false;
	}
	
	public void setDrawingAllowed(boolean isAllowed)
	{
		isDrawingAllowed = isAllowed;
	}
	
	public void setStatusMessage(String message)
	{
		lblStatusMessage.setText(message);
	}
}
