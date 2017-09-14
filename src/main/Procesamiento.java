package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Procesamiento {

	private List<String> archivo = new LinkedList<>();
	private List<ComponenteDeProcesamiento> proceso;
	private Lenguaje lenguaje;
	private Stage stage;

	public Procesamiento(Lenguaje lenguaje, Stage stage) {
		this.lenguaje = lenguaje;
		this.stage = stage;
		generarConfiguracion(lenguaje);
	}

	public boolean run() {
		if(!leerArchivo()){
			return false;
		}

		ejecutarComponentesDeProcesamiento();

		if(!escribirArchivo()){
			return false;
		}
		return true;
	}

	private void generarConfiguracion(Lenguaje lenguaje) {
		proceso = lenguaje.getProceso();
		if(proceso == null){
			System.out.println("Lenguaje no soportado!");

			Alert error = new Alert(AlertType.ERROR);
			try{
				error.initOwner(stage);
			} catch(NullPointerException e){
				//Si no tiene scene tira esta excepcion
			}
			error.setContentText("Lenguaje no soportado");
			error.setHeaderText(null);
			error.setTitle("Lenguaje no soportado");
			error.showAndWait();
			System.exit(1);
		}
	}

	private boolean leerArchivo() {
		File file = lenguaje.getFileChooser().showOpenDialog(stage);
		if(file == null){
			return false;
		}
		try(FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);){
			String linea;
			archivo.clear();
			while((linea = br.readLine()) != null){
				archivo.add(linea);
			}
		} catch(Exception e){
			e.printStackTrace(); //TODO Manejar excepci�n.
			return false;
		}
		return true;
	}

	private void ejecutarComponentesDeProcesamiento() {
		proceso.forEach(actual -> archivo = actual.ejecutar(archivo));
	}

	private boolean escribirArchivo() {
		File file = lenguaje.getFileChooser().showSaveDialog(stage);
		if(file == null){
			return false;
		}
		try(FileWriter fichero = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fichero);){
			archivo.forEach(linea -> pw.println(linea));
		} catch(Exception e){
			e.printStackTrace(); //TODO Manejar excepci�n.
			return false;
		}
		return true;
	}
}
