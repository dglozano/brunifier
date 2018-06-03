package main.java.cp.pdp.st;

import java.util.ArrayList;
import java.util.List;

import main.java.cp.model.Archivo;

public class ArchivoPharo extends Archivo<LineaPharo> {

	private final List<LineaPharo> lineasArchivo = new ArrayList<>();

	@Override
	public LineaPharo getLinea(int numeroLinea) {
		return lineasArchivo.get(numeroLinea - 1);
	}

	@Override
	public void addLinea(String codigoLinea) {
		LineaPharo nuevaLinea = new LineaPharo();
		nuevaLinea.setNumeroLinea(lineasArchivo.size() + 1);
		nuevaLinea.setCodigoLinea(codigoLinea);
		lineasArchivo.add(nuevaLinea);
	}

	@Override
	public void putLinea(LineaPharo nuevaLinea) {
		lineasArchivo.set(nuevaLinea.getNumeroLinea() - 1, nuevaLinea);
	}

	@Override
	public void clear() {
		this.lineasArchivo.clear();
	}

	@Override
	public List<LineaPharo> getLineas() {
		return this.lineasArchivo;
	}
}
