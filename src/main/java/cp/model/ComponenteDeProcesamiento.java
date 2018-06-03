package main.java.cp.model;


/**
 * Un ComponenteDeProcesamiento agrega marcas a un archivo pasado como parametro y retorna
 * el archivo con las nuevas marcas.
 */
public abstract class ComponenteDeProcesamiento {

	public ComponenteDeProcesamiento() {
		super();
	}

	/**
	 * En este metodo debe escribirse la logica para parsear el archivo y añadir las marcas
	 * @param archivo al que se agregaran las marcas
	 * @return archivo con marcas
	 */
	public abstract Archivo<?> ejecutar(Archivo<?> archivo);
}