package main;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import persistance.GameMapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import gui.SpelScherm;
import gui.WelkomScherm;


public class StartUp extends Application
{
	Scene welkomScherm, spelScherm;
		
	@Override
	public void start(Stage primaryStage) {
		WelkomScherm root = new WelkomScherm();
		root.btnVerder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				List<String> namen = root.checkAndTryGettingInput();
				if (namen != null)
				{
					spelScherm = new Scene(new SpelScherm(namen), 850, 650);
					primaryStage.setScene(spelScherm);
				}
			}
		});
		
		root.btnHervat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				try
				{
					spelScherm = new Scene(new SpelScherm(GameMapper.retrieveGame(0)), 850, 650);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				primaryStage.setScene(spelScherm);
			}
		});
		
		root.btnHighScores.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0)
			{
				Map<String, Integer> highScores;
				GridPane grdScores = new GridPane();
				grdScores.setHgap(40);
				grdScores.setVgap(5);
				grdScores.setPadding(new Insets(0, 0, 0, 15));
				try
				{
					highScores = GameMapper.retrieveHighScores();
					int position = 0;
					for (Map.Entry<String, Integer> score : highScores.entrySet())
					{
						grdScores.add(new Label(score.getKey()), 0, position);
						grdScores.add(new Label(score.getValue().toString()), 1, position);
						position++;
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("High Scores");
				alert.setHeaderText(null);
				alert.setContentText("Here are the best players!");
				
				alert.getDialogPane().setExpandableContent(grdScores);
				alert.getDialogPane().setExpanded(true);
				alert.showAndWait();
			}
		});
		
		Scene welkomScherm = new Scene(root, 500, 250);
		welkomScherm.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
		
		primaryStage.setScene(welkomScherm);
		
		primaryStage.setTitle("Coloretto - Geef aantal spelers");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
