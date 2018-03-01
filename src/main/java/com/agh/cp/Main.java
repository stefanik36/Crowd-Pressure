package com.agh.cp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("App.fxml"));
		Parent pre = fxmlLoader.load();
		Scene scene = new Scene(pre);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Crowd pressure simulation");
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(650);
		primaryStage.setOnCloseRequest(we -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("");
			alert.setTitle("Exit");
			alert.setContentText("Are you sure you want to exit?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				primaryStage.close();
			} else {
				we.consume();
			}
		});
		primaryStage.show();

	}
}
