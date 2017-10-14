package cp.aedd;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.Linea;

public class ArchivoCMasMas extends Archivo {
	
	private final List<LineaCMasMas> lineasArchivo = new ArrayList<LineaCMasMas>();

	@Override
	public LineaCMasMas getLinea(int numeroLinea) {
		return lineasArchivo.get(numeroLinea-1);
	}

	@Override
	public void addLinea(String codigoLinea) {
		LineaCMasMas nuevaLinea = new LineaCMasMas();
		nuevaLinea.setNumeroLinea(lineasArchivo.size() + 1);
		nuevaLinea.setCodigoLinea(codigoLinea);
		lineasArchivo.add(nuevaLinea);
	}

	@Override
	public void putLinea(Linea nuevaLinea) {
		lineasArchivo.set(nuevaLinea.getNumeroLinea() - 1, (LineaCMasMas) nuevaLinea);
	}

	@Override
	public void clear() {
		this.lineasArchivo.clear();
	}
	
	public List<Linea> getLineas(){
		return new ArrayList<>(this.lineasArchivo);
	}

	public List<LineaCMasMas> getLineasCMasMas(){
		return this.lineasArchivo;
	}
}
