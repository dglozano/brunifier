package cp.aedd;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;

public class ArchivoCMasMas extends Archivo<LineaCMasMas> {

	private final List<LineaCMasMas> lineasArchivo = new ArrayList<>();

	@Override
	public LineaCMasMas getLinea(int numeroLinea) {
		return lineasArchivo.get(numeroLinea - 1);
	}

	@Override
	public void addLinea(String codigoLinea) {
		LineaCMasMas nuevaLinea = new LineaCMasMas();
		nuevaLinea.setNumeroLinea(lineasArchivo.size() + 1);
		nuevaLinea.setCodigoLinea(codigoLinea);
		lineasArchivo.add(nuevaLinea);
	}

	@Override
	public void putLinea(LineaCMasMas nuevaLinea) {
		lineasArchivo.set(nuevaLinea.getNumeroLinea() - 1, nuevaLinea);
	}

	@Override
	public void clear() {
		this.lineasArchivo.clear();
	}

	@Override
	public List<LineaCMasMas> getLineas() {
		return new ArrayList<>(this.lineasArchivo);
	}
}
