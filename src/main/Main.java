package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		mostrarPantallaInicial();
		primaryStage.show();
	}

	private void mostrarPantallaInicial() {
		//Setear icono y titulo de espera
		primaryStage.setTitle("Herramienta de marcado de código");

		//Crear panel base
		VBox panel = new VBox();
		panel.setAlignment(Pos.CENTER);

		//Agregar comboBox con lenguajes
		Label esperando = new Label("Lenguaje a marcar:");
		panel.getChildren().add(esperando);

		ComboBox<Lenguaje> cbLenguajes = new ComboBox<>();
		cbLenguajes.getItems().addAll(Lenguaje.values());
		cbLenguajes.getSelectionModel().select(0);
		panel.getChildren().add(cbLenguajes);

		Button aceptar = new Button("Aceptar");
		aceptar.setOnAction(t -> {
			Lenguaje lenguaje = cbLenguajes.getValue();
			procesar(lenguaje);
		});
		panel.getChildren().add(aceptar);

		Button cancelar = new Button("Cancelar");
		cancelar.setOnAction(t -> {
			Platform.exit();
		});
		panel.getChildren().add(cancelar);

		Scene scene = new Scene(panel);
		primaryStage.setScene(scene);
	}

	private void procesar(Lenguaje lenguaje) {
		Procesamiento procesamiento = new Procesamiento(lenguaje, primaryStage);
		boolean seguir = true;
		while(seguir){
			seguir = procesamiento.run();
		}

		Platform.exit();
	}
}
