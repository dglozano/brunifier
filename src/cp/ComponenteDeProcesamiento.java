package cp;

public abstract class ComponenteDeProcesamiento {

	public ComponenteDeProcesamiento() {
		super();
	}

	public abstract Archivo<?> ejecutar(Archivo<?> archivo);

}