package cp;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

	@Override
	public abstract String toString();

}
