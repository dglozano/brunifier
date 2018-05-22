package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cp.Lenguaje;
import cp.Procesamiento;
import cp.aedd.CMasMasProcedural;
import cp.exception.UnsupportedLanguageException;
import cp.pdp.lisp.Scheme;
import cp.pdp.st.GNUSmalltalk;
import cp.pdp.st.PharoToGNUSmalltalk;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
		//Setear titulo
		primaryStage.setTitle("Herramienta de marcado de código");

		//Crear panel base
		VBox panel = new VBox();
		panel.setPrefWidth(200);
		panel.setPrefHeight(100);
		panel.setSpacing(10);
		panel.setPadding(new Insets(10));
		panel.setAlignment(Pos.CENTER_LEFT);

		//Agregar comboBox con lenguajes
		Label esperando = new Label("Lenguaje a marcar:");
		panel.getChildren().add(esperando);

		ComboBox<Lenguaje> cbLenguajes = new ComboBox<>();
		cbLenguajes.getItems().addAll(this.lenguajesSoportados());
		cbLenguajes.getSelectionModel().select(0);
		cbLenguajes.setMaxWidth(Double.MAX_VALUE);
		panel.getChildren().add(cbLenguajes);

		//Crear panel botones
		HBox panelBotones = new HBox();
		panelBotones.setAlignment(Pos.BOTTOM_RIGHT);
		panel.getChildren().add(panelBotones);

		//Crear boton aceptar
		Button aceptar = new Button("Aceptar");
		aceptar.setOnAction(t -> {
			Lenguaje lenguaje = cbLenguajes.getValue();
			procesar(lenguaje);
		});
		panelBotones.getChildren().add(aceptar);

		//Crear boton cancelar
		Button cancelar = new Button("Cancelar");
		cancelar.setOnAction(t -> {
			Platform.exit();
		});
		HBox.setMargin(cancelar, new Insets(0, 0, 0, 10));
		panelBotones.getChildren().add(cancelar);

		Scene scene = new Scene(panel);
		primaryStage.setScene(scene);
	}

	private void procesar(Lenguaje lenguaje) {
		try{
			//Un Procesamiento consiste en una cadena de ComponenteDeProcesamiento aplicadas al lenguaje
			Procesamiento procesamiento = new Procesamiento(lenguaje);
			//Muestra el file chooser para seleccionar el o los archivos a procesar
			List<File> archivos = getFileChooser(lenguaje).showOpenMultipleDialog(primaryStage);
			if(archivos != null){
				//Procesa en paralelo cada archivo seleccionado
				archivos.parallelStream().forEach(fileIn -> {
					//Crea el archivo de salida con el nombre compuesto por el nombre y extension original,
					//pero añadiendo "ConMarcas" al final del mismo.
					String[] nombreExtension = this.stripExtension(fileIn.getName());
					File fileOut = new File(fileIn.getParentFile(), nombreExtension[0] + "ConMarcas" + nombreExtension[1]);
					if(fileOut.exists()){
						//Si ya existe un archivo de salida con ese nombre lo borra
						fileOut.delete();
					}
					//Ejecuta en un nuevo hilo el procesamiento del archivo
					new Thread(() -> procesamiento.run(fileIn, fileOut)).start();
				});
			}
			Platform.exit();
		} catch(UnsupportedLanguageException e){
			e.printStackTrace();
			mostrarError(e.getMessage());
		}
	}

	// Devuelve un array con dos strings: el nombre del archivo hasta el "." y el nombre de la extension
	private String[] stripExtension(String str) {

		// Handle null case specially.
		if(str == null){
			return null;
		}

		// Get position of last '.'.
		int pos = str.lastIndexOf(".");

		// If there wasn't any '.' just return the string as is.
		if(pos == -1){
			return new String[] { str, "" };
		}

		// Otherwise return the string, up to the dot.
		return new String[] { str.substring(0, pos), str.substring(pos) };
	}

	// Retorna un FileChooser con los filtros especificos para un Lenguaje dado (por ejemplo C++ --> .cpp)
	private FileChooser getFileChooser(Lenguaje lenguaje) {
		ExtensionFilter filtro = new ExtensionFilter(lenguaje.getNombreFiltro(), lenguaje.getTiposFiltro());

		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);

		return archivoSeleccionado;
	}

	// Muestra un alert con un mensaje de error
	private void mostrarError(String errorMsg) {
		Alert error = new Alert(AlertType.ERROR);
		try{
			error.initOwner(primaryStage);
		} catch(NullPointerException exc){
			exc.printStackTrace();
		}
		error.setContentText(errorMsg);
		error.setHeaderText(null);
		error.setTitle("ERROR: " + errorMsg);
		error.showAndWait();
	}

	//TODO al agregar un lenguaje, agregarlo a la lista del método
	public List<Lenguaje> lenguajesSoportados() {
		List<Lenguaje> lenguajesSoportados = new ArrayList<>();
		lenguajesSoportados.add(new CMasMasProcedural());
		lenguajesSoportados.add(new GNUSmalltalk());
		lenguajesSoportados.add(new PharoToGNUSmalltalk());
		lenguajesSoportados.add(new Scheme());
		return lenguajesSoportados;
	}
}
