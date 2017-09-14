package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.aedd.DelimitadorDeBloques;
import cp.aedd.FormatoEstandar;
import javafx.stage.Stage;

public class Procesamiento {

	private List<String> archivo = new LinkedList<>();
	private List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
	private Lenguaje lenguaje;
	private Stage stage;

	public Procesamiento(Lenguaje lenguaje, Stage stage) {
		this.lenguaje = lenguaje;
		this.stage = stage;
	}

	public void run() {
		generarConfiguracion(lenguaje);
		if(!leerArchivo()){
			return;
		}
		ejecutarComponentesDeProcesamiento();
		if(!escribirArchivo()){
			return;
		}
	}

	private void generarConfiguracion(Lenguaje lenguaje) {
		//TODO Completar para otros lenguajes. La lista debe generarse en el orden de procesamiento requerido.
		switch(lenguaje) {
		case CMasMasProcedural:
			proceso.add(new FormatoEstandar());
			proceso.add(new DelimitadorDeBloques());
			break;
		default:
			System.out.println("Lenguaje no soportado!");
			break;
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
		//TODO Ver ubicaci�n (por ahora est� en D:) - Tal vez, generar una carpeta espec�fica donde almacenarlos?
		// String url = "D:\\" + nombre.substring(0, nombre.indexOf(".")) + "ConMarcas" + nombre.substring(nombre.indexOf("."), nombre.length());
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
