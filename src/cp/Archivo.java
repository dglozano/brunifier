package cp;

import java.util.List;

public abstract class Archivo<T extends Linea> {

	public abstract T getLinea(int numeroLinea);

	public abstract void addLinea(String codigoLinea);

	public abstract void putLinea(T nuevaLinea);

	public abstract void clear();

	public abstract List<T> getLineas();
}
