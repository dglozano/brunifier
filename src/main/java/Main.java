package main.java;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.java.ui.FXMLFileChooserController;
import main.java.util.SoundPlayer;

public class Main extends Application {

	public Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		mostrarPantallaInicial();
		primaryStage.show();
	}

	private void mostrarPantallaInicial() throws IOException {
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/resources/img/braille.png")));
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/main/resources/FXMLFileChooser.fxml"));
		Parent fileChooserView = loader.load();

		Scene initScene = new Scene(fileChooserView);

		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setTitle("Brunifier");
		primaryStage.setScene(initScene);
		primaryStage.setResizable(false);
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if(KeyCode.ESCAPE == event.getCode()){
				Platform.exit();
			}
		});

		primaryStage.setScene(initScene);

		SoundPlayer.playBeep();

		FXMLFileChooserController controller = (FXMLFileChooserController) loader.getController();
		controller.setStageAndInitializeKeyCombinations(primaryStage);
	}
}