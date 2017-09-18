package cp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import cp.exception.UnsupportedLanguageException;

public class Procesamiento {

	private List<String> archivo = new LinkedList<>();
	private List<ComponenteDeProcesamiento> proceso;
	private Lenguaje lenguaje;

	public Procesamiento(Lenguaje lenguaje) throws UnsupportedLanguageException {
		this.lenguaje = lenguaje;
		generarConfiguracion(lenguaje);
	}

	public boolean run(File fileIn, File fileOut) {
		boolean lecturaCorrecta = true, escrituraCorrecta = true;
		if(!leerArchivo(fileIn)){
			lecturaCorrecta = false;
		}

		ejecutarComponentesDeProcesamiento();

		if(!escribirArchivo(fileOut)){
			escrituraCorrecta = false;
		}
		return lecturaCorrecta && escrituraCorrecta;
	}

	private void generarConfiguracion(Lenguaje lenguaje) throws UnsupportedLanguageException {
		proceso = lenguaje.getProceso();
		if(proceso == null){
			throw new UnsupportedLanguageException();
		}
	}

	private boolean leerArchivo(File file) {
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
			e.printStackTrace(); //TODO Manejar excepcion.
			return false;
		}
		return true;
	}

	private void ejecutarComponentesDeProcesamiento() {
		proceso.forEach(componenteDeProcesamiento -> archivo = componenteDeProcesamiento.ejecutar(archivo));
	}

	private boolean escribirArchivo(File file) {
		if(file == null){
			return false;
		}
		try(FileWriter fichero = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fichero);){
			archivo.forEach(linea -> pw.println(linea));
		} catch(Exception e) {
			e.printStackTrace(); //TODO Manejar excepcion.
			return false;
		}
		return true;
	}
}
