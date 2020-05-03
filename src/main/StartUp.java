package main;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import gui.SpelScherm;
import gui.WelkomScherm;


public class StartUp extends Application
{
	Scene welkomScherm, spelScherm;
	
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/coloretto?user=user&password=NFohFZx3Y3L7AH/&serverTimezone=UTC&useSSL=false";
	
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
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL))
		{
			System.out.println("U good");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
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
