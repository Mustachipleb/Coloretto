package gui;

import java.util.ArrayList;
import java.util.List;

import domein.Card;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardStack extends StackPane
{
	private List<ImageView> cardStackContent;
	
	private int amountOfNonBlankCards;
	private int stackNumber;
	
	public CardStack(int stackNumber)
	{
		this.stackNumber = stackNumber;
		this.setMaxWidth(160);
		this.setMinWidth(160);
		cardStackContent = new ArrayList<ImageView>();
		amountOfNonBlankCards = 0;
		for (int i = 0; i < 3; i++)
		{
			ImageView imvCard = new ImageView(SpelScherm.getCardImages().get("back"));
			imvCard.setFitWidth(60);
			imvCard.setPreserveRatio(true);
			cardStackContent.add(imvCard);
			StackPane wrapper = new StackPane();
			wrapper.setMaxWidth(60);
			wrapper.setMinWidth(60);
			wrapper.setPadding(new Insets(0, 0, 0, (i - 1) * 80));
			wrapper.getChildren().add(cardStackContent.get(i));
			this.getChildren().add(wrapper);
		}
	}
	
	public boolean tryAddCard(Card kaart)
	{
		if (amountOfNonBlankCards < 3)
		{
			cardStackContent.get(2 - amountOfNonBlankCards).setImage(SpelScherm.getCardImages().get(kaart.getKleur()));
			amountOfNonBlankCards++;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void resetContent()
	{
		for (ImageView imvCard : cardStackContent)
		{
			imvCard.setImage(SpelScherm.getCardImages().get("back"));
		}
		amountOfNonBlankCards = 0;
	}
	
	public void clear()
	{
		for (ImageView imvCard : cardStackContent)
		{
			imvCard.setImage(null);
		}
		amountOfNonBlankCards = -1;
	}
	
	public int getStackNumber()
	{
		return this.stackNumber;
	}
	
	public boolean isFull()
	{
		return amountOfNonBlankCards == 3;
	}
	
	public boolean isEmpty()
	{
		return amountOfNonBlankCards == 0;
	}
	
	public boolean hasBeenTaken()
	{
		return amountOfNonBlankCards == -1;
	}
}
