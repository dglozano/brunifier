package cp.aedd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.Lenguaje;

public class CMasMasProcedural extends Lenguaje {
	
	@Override
	public List<ComponenteDeProcesamiento> getProceso() {
		List<ComponenteDeProcesamiento> proceso = new LinkedList<>();
		proceso.add(new FormatoEstandar());
		proceso.add(new DelimitadorDeBloques());
		proceso.add(new DelimitadorDeFunciones());
		return proceso;
	}
	
	@Override
	public String getNombreFiltro() {
		return "Archivo C/C++ (*.cpp, *.c)";
	}
	
	@Override
	public ArrayList<String> getTiposFiltro() {
		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.cpp");
		tiposFiltro.add("*.c");
		return tiposFiltro;
	}

	@Override
	public ArchivoCMasMas createArchivo() {
		return new ArchivoCMasMas();
	}
	
	@Override
	public String toString() {
		return "C++ Procedural";
	}
}
