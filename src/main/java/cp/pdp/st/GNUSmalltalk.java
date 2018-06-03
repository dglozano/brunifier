package main.java.cp.pdp.st;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.cp.model.ComponenteDeProcesamiento;
import main.java.cp.model.Lenguaje;

public class GNUSmalltalk extends Lenguaje {

	enum Marca {
		inicioClase(" subclass: "), aperturaBloque("["), cierreBloque("]");

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
	public String getNombreFiltro() {
		return "Archivo Smalltalk (*.st, *.txt)";
	}

	@Override
	public ArrayList<String> getTiposFiltro() {
		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.st");
		tiposFiltro.add("*.txt");
		return tiposFiltro;
	}

	@Override
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new FormatoEstandar());
		proceso.add(new DelimitadorDeClasesYMetodos());
		return proceso;
	}

	@Override
	public ArchivoGNUSmalltalk createArchivo() {
		return new ArchivoGNUSmalltalk();
	}

	@Override
	public String toString() {
		return "GNU Smalltalk";
	}
}
