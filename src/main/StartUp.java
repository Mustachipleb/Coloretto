package main;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import persistance.GameMapper;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import domein.DomeinController;
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
