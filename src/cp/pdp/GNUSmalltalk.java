package cp.pdp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
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
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new DelimitadorDeClasesYMetodos());
		return proceso;
	}

	@Override
	public String toString() {
		return "GNU Smalltalk";
	}
}
