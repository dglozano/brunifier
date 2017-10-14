package cp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import cp.exception.UnsupportedLanguageException;

public class Procesamiento {

	private Archivo archivo;
	private List<ComponenteDeProcesamiento> proceso;
	
	public Procesamiento(Lenguaje lenguaje) throws UnsupportedLanguageException {
		generarConfiguracion(lenguaje);
		this.archivo = lenguaje.createArchivo();
	}

	public void run(File fileIn, File fileOut) {
		leerArchivo(fileIn);
		ejecutarComponentesDeProcesamiento();
		escribirArchivo(fileOut);
	}

	private void generarConfiguracion(Lenguaje lenguaje) throws UnsupportedLanguageException {
		proceso = lenguaje.getProceso();
		if(proceso == null){
			throw new UnsupportedLanguageException();
		}
	}

	private void leerArchivo(File file) {
		try(FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);){
			String lineaString;
			archivo.clear();
			while((lineaString = br.readLine()) != null){
				archivo.addLinea(lineaString);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private void ejecutarComponentesDeProcesamiento() {
		proceso.forEach(componenteDeProcesamiento -> archivo = componenteDeProcesamiento.ejecutar(archivo));
	}

	private void escribirArchivo(File file) {
		try(FileWriter fichero = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fichero);){
			archivo.getLineas().forEach(linea -> pw.println(linea.toString()));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
