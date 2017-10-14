package cp;

import java.util.List;

public abstract class Archivo {
	
	public abstract Linea getLinea(int numeroLinea);
	public abstract void addLinea(String codigoLinea);
	public abstract void putLinea(Linea nuevaLinea);
	public abstract void clear();
	public abstract List<Linea> getLineas();
}
