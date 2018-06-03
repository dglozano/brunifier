package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.java.cp.exception.UnsupportedLanguageException;
import main.java.cp.model.Lenguaje;
import main.java.cp.model.Procesamiento;
import main.java.cp.aedd.CMasMasProcedural;
import main.java.cp.pdp.lisp.Scheme;
import main.java.cp.pdp.st.GNUSmalltalk;
import main.java.cp.pdp.st.PharoToGNUSmalltalk;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/resources/FXMLFileChooser.fxml"));
        Parent fileChooserView = loader.load();

        Scene initScene = new Scene(fileChooserView);

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("Herramienta de Marcado de Codigo");
        primaryStage.setScene(initScene);
        primaryStage.setResizable(false);
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                Platform.exit();
            }
        });

		primaryStage.setScene(initScene);

		SoundPlayer.playBeep();

        FXMLFileChooserController controller = (FXMLFileChooserController)loader.getController();
        controller.setStageAndInitializeKeyCombinations(primaryStage);
	}
}