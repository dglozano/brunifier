package main.java.cp.model;

import java.util.List;

/**
 * Representa un Archivo de un Lenguaje determinado. Estara compuesto por multiples Lineas
 * de ese Lenguaje
 */

public abstract class Archivo<T extends Linea> {

	/**
	 * Obtiene la Linea en la posicion numeroLinea
	 * @param numeroLinea
	 * @return Linea en la posicion numeroLinea
	 */
	public abstract T getLinea(int numeroLinea);

	/**
	 * Agrega una nueva linea al final
	 * @param codigoLinea
	 */
	public abstract void addLinea(String codigoLinea);

	/**
	 * Agrega una nueva Linea al archivo. La Linea contiene un numero de Linea que indicara
	 * en que posicion se debe agregar.
	 * @param nuevaLinea
	 */
	public abstract void putLinea(T nuevaLinea);

	/**
	 * Elimina la lista de lineas del archivo
	 */
	public abstract void clear();

	/**
	 * Obtiene todas las lineas del archivo.
	 * @return Lista de Linea del archivo
	 */
	public abstract List<T> getLineas();
}
