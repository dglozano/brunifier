package cp;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public abstract class Lenguaje {

	public Lenguaje() {
		super();
	}

	public FileChooser getFileChooser() {
		ExtensionFilter filtro = new ExtensionFilter("Archivo ", new ArrayList<>());
		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);
		return archivoSeleccionado;
	};

	public List<ComponenteDeProcesamiento> getProceso() {
		return null;
	}

	@Override
	public abstract String toString();

}
