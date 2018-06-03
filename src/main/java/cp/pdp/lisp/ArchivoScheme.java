package main.java.cp.pdp.lisp;

import java.util.ArrayList;
import java.util.List;

import main.java.cp.model.Archivo;

public class ArchivoScheme extends Archivo<LineaScheme> {

	private final List<LineaScheme> lineasArchivo = new ArrayList<>();

	@Override
	public LineaScheme getLinea(int numeroLinea) {
		return lineasArchivo.get(numeroLinea - 1);
	}

	@Override
	public void addLinea(String codigoLinea) {
		LineaScheme nuevaLinea = new LineaScheme();
		nuevaLinea.setNumeroLinea(lineasArchivo.size() + 1);
		nuevaLinea.setCodigoLinea(codigoLinea);
		lineasArchivo.add(nuevaLinea);
	}

	@Override
	public void putLinea(LineaScheme nuevaLinea) {
		lineasArchivo.set(nuevaLinea.getNumeroLinea() - 1, nuevaLinea);
	}

	@Override
	public void clear() {
		this.lineasArchivo.clear();
	}

	@Override
	public List<LineaScheme> getLineas() {
		return this.lineasArchivo;
	}
}
