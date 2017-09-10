package cp;

import java.util.LinkedList;

public abstract class ComponenteDeProcesamiento {

	public ComponenteDeProcesamiento() {
		super();
	}

	public abstract void ejecutar(LinkedList<String> archivo);
	
}