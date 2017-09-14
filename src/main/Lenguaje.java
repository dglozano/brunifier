package main;

import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public enum Lenguaje {
	CMasMasProcedural {
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
	};

	public FileChooser getFileChooser() {
		ExtensionFilter filtro = new ExtensionFilter("Archivo ", new ArrayList<>());
		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);
		return archivoSeleccionado;
	};
}
