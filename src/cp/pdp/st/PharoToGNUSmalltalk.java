package cp.pdp.st;

import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;

public class PharoToGNUSmalltalk extends GNUSmalltalk {

	enum Marca {
		inicioClase("subclass:"),
		aperturaBloque("["),
		cierreBloque("]"),
		variablesInstacia("instanceVariableNames:"),
		metodos("methodsFor:"),
		clase("class");

		private String nombre;

		private Marca(String nombre) {
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			return nombre;
		}
	}

	@Override
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new ConversorPharoToGNUst());
		return proceso;
	}

	@Override
	public String toString() {
		return "Pharo a GNU Smalltalk";
	}
}