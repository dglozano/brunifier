package main.java.cp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un Lenguaje de Programacion. Contiene una Lista de ComponenteDeProcesamiento
 * que se aplicaran a los archivos de este lenguaje.
 */
public abstract class Lenguaje {

	public Lenguaje() {
		super();
	}

	/**
	 * Lista de ComponenteDeProcesamiento que serán aplicados a los archivos de este Lenguaje
	 * @return Lista de ComponenteDeProcesamiento
	 */
	public List<ComponenteDeProcesamiento> getProceso() {
		return null;
	}

	/**
	 * Retorna el nombre del Filtro a usarse en el FileChooser
	 * @return string con el nombre del filtro
	 */
	public String getNombreFiltro() {
		return "Archivo ";
	}

	/**
	 * Retorna los filtros a usarse en el FileChooser
	 * @return ArrayList de Filtros para FileChooser
	 */
	public ArrayList<String> getTiposFiltro() {
		return new ArrayList<>();
	}

	/**
	 * Patron Builder. El Lenguaje se encarga de instanciar Archivos compuesto por Lineas que
	 * correspondan a este Lenguaje
	 * @return
	 */
	public Archivo<?> createArchivo() {
		return null;
	}

	@Override
	public abstract String toString();

}
