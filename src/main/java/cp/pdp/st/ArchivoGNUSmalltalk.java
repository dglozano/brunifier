package main.java.cp.pdp.st;

import java.util.ArrayList;
import java.util.List;

import main.java.cp.model.Archivo;

public class ArchivoGNUSmalltalk extends Archivo<LineaGNUSmalltalk> {

	private final List<LineaGNUSmalltalk> lineasArchivo = new ArrayList<>();

	@Override
	public LineaGNUSmalltalk getLinea(int numeroLinea) {
		return lineasArchivo.get(numeroLinea - 1);
	}

	@Override
	public void addLinea(String codigoLinea) {
		LineaGNUSmalltalk nuevaLinea = new LineaGNUSmalltalk();
		nuevaLinea.setNumeroLinea(lineasArchivo.size() + 1);
		nuevaLinea.setCodigoLinea(codigoLinea);
		lineasArchivo.add(nuevaLinea);
	}

	@Override
	public void putLinea(LineaGNUSmalltalk nuevaLinea) {
		lineasArchivo.set(nuevaLinea.getNumeroLinea() - 1, nuevaLinea);
	}

	@Override
	public void clear() {
		this.lineasArchivo.clear();
	}

	@Override
	public List<LineaGNUSmalltalk> getLineas() {
		return this.lineasArchivo;
	}
}
