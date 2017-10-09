package cp;

import java.util.ArrayList;
import java.util.List;

public abstract class Lenguaje {

	public Lenguaje() {
		super();
	}

	public List<ComponenteDeProcesamiento> getProceso() {
		return null;
	}
	
	public String getNombreFiltro() {
		return "Archivo ";
	}
	
	public ArrayList<String> getTiposFiltro() {
		return new ArrayList<>();
	}
	
	public Linea createLinea() {
		return null;
	}

	@Override
	public abstract String toString();

}
