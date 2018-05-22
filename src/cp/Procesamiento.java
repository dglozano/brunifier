package cp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import cp.exception.UnsupportedLanguageException;

/**
 * El Procesamiento se encarga de:
 *  1- Leer el archivo de entrada seleccionado en el File chooser
 *  2- Aplicarle cada uno de los ComponenteDeProcesamiento definido en proceso
 *  3- Escribir el archivo resultante a disco
 *
 *  El tipo de Archivo<?> a crear lo obtiene del Lenguaje pasado por paraemtro,
 *  asi como tambien la lista de ComponenteDeProcesamiento a aplicarle
 */
public class Procesamiento {

	private Archivo<?> archivo;
	private List<ComponenteDeProcesamiento> proceso;

	/**
	 * Crea un nuevo Procesamiento para un Lenguaje dado.
	 * Del Lenguaje, obtiene la instancia especifica del Archivo y los ComponenteDeProcesamiento a aplicar
	 * @param lenguaje Lenguaje del archivo a parsear
	 * @throws UnsupportedLanguageException en caso que el Lenguaje exista pero no tengo proceso definido
	 */
	public Procesamiento(Lenguaje lenguaje) throws UnsupportedLanguageException {
		this.archivo = lenguaje.createArchivo();
		this.proceso = lenguaje.getProceso();
		if(this.proceso == null){
			throw new UnsupportedLanguageException();
		}
	}

	/**
	 * Lee el archivo a parsear en memoria
	 * Le aplica con una estrategia de Pipe&Filter cada uno de los CP definidos en el proceso
	 * Escribe en disco el archivo con marcas resultante
	 * @param fileIn Archivo a parsear
	 * @param fileOut Archivo parseado
	 */
	public void run(File fileIn, File fileOut) {
		leerArchivo(fileIn);
		ejecutarComponentesDeProcesamiento();
		escribirArchivo(fileOut);
	}

	/**
	 * Lee linea por linea (strings) el archivo de entrada desde el disco
	 * y crea por cada una instancia del objeto Linea que añade al Archivo.
	 * @param file
	 */
	private void leerArchivo(File file) {
		try(FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr)){
			String lineaString;
			archivo.clear();
			while((lineaString = br.readLine()) != null){
				archivo.addLinea(lineaString);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Ejecuta en modo Pipe&Filter cada Componente de Procesamiento sobre el archivo leido.
	 * Luego de ejecutar un ComponenteDeProcesamiento, obtiene un nuevo archivo con marcas
	 * que sera la entrada del proximo CP.
	 */
	private void ejecutarComponentesDeProcesamiento() {
		proceso.forEach(componenteDeProcesamiento -> archivo = componenteDeProcesamiento.ejecutar(archivo));
	}

	/**
	 * Escribe el archivo con marcas al disco
	 * @param file
	 */
	private void escribirArchivo(File file) {
		try(FileWriter fichero = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fichero);){
			archivo.getLineas().forEach(linea -> pw.println(linea.toString()));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
