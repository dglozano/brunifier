package main.java.cp.pdp.lisp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.cp.model.ComponenteDeProcesamiento;
import main.java.cp.model.Lenguaje;

public class Scheme extends Lenguaje {

	enum Marca {
		aperturaBloque("("), cierreBloque(")"), quote("'"), iF("if"), comentario(";"), lambda("lambda");

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
		return "Archivo Scheme (*.scm, *.sls, *.ss, *.rkt)";
	}

	@Override
	public ArrayList<String> getTiposFiltro() {
		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.scm");
		tiposFiltro.add("*.sls");
		tiposFiltro.add("*.ss");
		tiposFiltro.add("*.rkt");
		tiposFiltro.add("*.txt");
		return tiposFiltro;
	}

	@Override
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new SepararComentarios());
		proceso.add(new QuitarEspacios());
		proceso.add(new AgregarSaltosDeLinea());
		proceso.add(new QuitarSaltosDeLinea());
		proceso.add(new AgregarSaltosDeLineaIfs());
		proceso.add(new DelimitadorDeFunciones());
		proceso.add(new DelimitadorDeQuotes());
		// TODO ver si ayuda o no
		// proceso.add(new Identar());
		return proceso;
	}

	@Override
	public ArchivoScheme createArchivo() {
		return new ArchivoScheme();
	}

	@Override
	public String toString() {
		return "Scheme";
	}
}
