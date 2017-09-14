package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.aedd.DelimitadorDeBloques;
import cp.aedd.FormatoEstandar;
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

		@Override
		public List<ComponenteDeProcesamiento> getProceso() {
			List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
			proceso.add(new FormatoEstandar());
			proceso.add(new DelimitadorDeBloques());
			return proceso;
		}
	},
	GNUSmalltalk {
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
	};

	public FileChooser getFileChooser() {
		ExtensionFilter filtro = new ExtensionFilter("Archivo ", new ArrayList<>());
		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);
		return archivoSeleccionado;
	};

	public List<ComponenteDeProcesamiento> getProceso() {
		return null;
	}
}
