package cp;

import java.util.List;

public abstract class ComponenteDeProcesamiento {

	public ComponenteDeProcesamiento() {
		super();
	}

	public abstract List<Linea> ejecutar(List<Linea> archivo);

}