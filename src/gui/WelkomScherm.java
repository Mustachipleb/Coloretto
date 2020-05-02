package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WelkomScherm extends GridPane {
	private List<TextField> textFields = new ArrayList<TextField>();
	public Button btnVerder;

	public WelkomScherm() {
		Font titelFont = Font.font("Tahoma", FontWeight.NORMAL, Font.getDefault().getSize() * 1.3);
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(15, 15, 15, 15));

		Label lblWelkom = new Label("Welkom bij Coloretto, met hoeveel wenst u te spelen?");
		lblWelkom.setFont(titelFont);
		this.add(lblWelkom, 0, 0, 2, 1);

		ComboBox<Integer> cmbAantalSpelers = new ComboBox<Integer>(FXCollections.observableArrayList(4, 5));
		this.add(cmbAantalSpelers, 0, 1);

		GridPane grdSpelerNamen = new GridPane();
		this.add(grdSpelerNamen, 0, 2);

		Label lblNamen = new Label("Namen van de spelers:");
		lblNamen.setFont(titelFont);
		grdSpelerNamen.add(lblNamen, 0, 0, 2, 1);
		for (int i = 0; i < 5; i++) {
			grdSpelerNamen.add(new Label(String.format("Speler %s: ", (i + 1))), 0, i + 1);
			TextField txtNaam = new TextField();
			txtNaam.setDisable(true);
			txtNaam.setTooltip(new Tooltip("Selecteer eerst het aantal spelers."));
			grdSpelerNamen.add(txtNaam, 1, i + 1);
			textFields.add(txtNaam);
		}

		btnVerder = new Button("Verder");
		this.add(btnVerder, 0, 3);

		cmbAantalSpelers.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < cmbAantalSpelers.getValue(); i++) {
					textFields.get(i).setDisable(false);
				}

				for (int i = cmbAantalSpelers.getValue(); i < textFields.size(); i++) {
					textFields.get(i).setDisable(true);
					textFields.get(i).setText("");
				}
			}
		});
	}

	public List<String> checkAndTryGettingInput() {
		boolean isInputCorrect = true;
		for (TextField txt : textFields) {
			if (!txt.isDisable() && txt.getText().isBlank()) {
				isInputCorrect = false;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Niet alle namen zijn ingevuld");
				alert.setContentText("Gelieve alle spelers hun naam in te vullen.");
				alert.showAndWait();
				break;
			}
		}

		if (isInputCorrect) {
			List<String> namen = textFields.stream()
				.filter(field -> !field.isDisable())
				.map(x -> x.getText())
				.collect(Collectors.toList());
			return namen;
		}
		else
		{
			return null;
		}
	}
}
