package cp.aedd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.Lenguaje;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CMasMasProcedural extends Lenguaje {

	@Override
	public FileChooser getFileChooser() {
		String tipo = "(*.cpp, *.c)";

		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.cpp");
		tiposFiltro.add("*.c");

		ExtensionFilter filtro = new ExtensionFilter("Archivo C/C++ " + tipo, tiposFiltro);

		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);

		return archivoSeleccionado;
	}

	@Override
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new FormatoEstandar());
		proceso.add(new DelimitadorDeBloques());
		return proceso;
	}

	@Override
	public String toString() {
		return "C++ Procedural";
	}
}
