package gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domein.Card;
import domein.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class SpelerKaartDisplay extends VBox
{
	private Player speler;
	
	private BorderPane bpSpelerInfo;
	private Label lblName;
	private Label lblScore;
	
	private FlowPane flwKaarten;
	
	public SpelerKaartDisplay(Player speler)
	{
		this.speler = speler;
		setMinWidth(100);
		setMaxWidth(220);
		
		bpSpelerInfo = new BorderPane();
		lblName = new Label(String.format("%s:", speler.getNaam()));
		lblScore = new Label(String.format("Score: %s", speler.berekenScore()));
		bpSpelerInfo.setLeft(lblName);
		bpSpelerInfo.setRight(lblScore);
		
		flwKaarten = new FlowPane();
		flwKaarten.setMaxWidth(220);
		flwKaarten.setMinWidth(100);
		
		this.getChildren().add(bpSpelerInfo);
		this.getChildren().add(flwKaarten);
		
		updateCards();
	}
	
	public void updateCards()
	{
		flwKaarten.getChildren().clear();
		List<Card> kaarten = speler.getKaarten();
		Set<Card> distinct = new HashSet<Card>(kaarten);
		for (Card kaart : distinct)
		{
			
			Image x = SpelScherm.getCardImages().get(kaart.getKleur());
			ImageView imvKaart = new ImageView(x);
			imvKaart.setFitWidth(25);
			imvKaart.setPreserveRatio(true);
			CardView cardView = new CardView(imvKaart, Collections.frequency(kaarten, kaart));
			flwKaarten.getChildren().add(cardView);
		}
		lblScore.setText(String.format("Score: %s", speler.berekenScore()));
	}
	
	public class CardView extends FlowPane
	{
		public CardView(ImageView imvKaart, int copies)
		{
			setMaxWidth(40);
			this.getChildren().add(imvKaart);
			this.getChildren().add(new Label("x" + copies));
		}
	}
}


