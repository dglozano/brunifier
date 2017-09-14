package cp.pdp;

import java.util.ArrayList;

import cp.Lenguaje;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GNUSmalltalk extends Lenguaje {

	@Override
	public FileChooser getFileChooser() {
		String tipo = "(*.st, *.txt)";

		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.st");
		tiposFiltro.add("*.txt");

		ExtensionFilter filtro = new ExtensionFilter("Archivo Smalltalk " + tipo, tiposFiltro);

		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);

		return archivoSeleccionado;
	}

	@Override
	public String toString() {
		return "GNUSmalltalk";
	}
}
