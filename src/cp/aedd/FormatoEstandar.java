package cp.aedd;

import java.util.List;

import cp.ComponenteDeProcesamiento;

public class FormatoEstandar extends ComponenteDeProcesamiento {

	@Override
	public List<String> ejecutar(List<String> archivo) {
		//TODO Hacer.
		return archivo;
	}

	/*
	 * TODO Este modulo debe dejar todas las lineas sin indentaci�n y sin blancos al final de las lineas
	 * (para poder preguntar por startWith y endsWith), sin lineas en blanco (para que todas las lineas sean procesables)
	 * y con un �nico blanco antes de la instrucci�n y la llave que abre un bloque (o sea, por ejemplo "if() {").
	 */
}
