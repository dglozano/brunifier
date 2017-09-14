package cp;

import java.util.List;

public abstract class ComponenteDeProcesamiento {

	public ComponenteDeProcesamiento() {
		super();
	}

	public abstract List<String> ejecutar(List<String> archivo);

}