package gui;

import java.util.List;

import domein.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SpelScherm extends GridPane 
{
	DomeinController dc = new DomeinController();
	
	public SpelScherm(List<String> namen)
	{
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
	}
	
	private void setup()
	{
		//TODO
	}
}
