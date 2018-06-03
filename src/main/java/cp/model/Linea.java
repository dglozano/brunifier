package main.java.cp.model;

/**
 * Representa una Linea de un Archivo de un Lenguaje determinado.
 * Compuesta por un String con el código en esa linea y el numero de la linea.
 */
public abstract class Linea {

	private int numeroLinea;
	private String codigoLinea;

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public String getCodigoLinea() {
		return codigoLinea;
	}

	public void setCodigoLinea(String codigoLinea) {
		this.codigoLinea = codigoLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	@Override
	public abstract String toString();
}
