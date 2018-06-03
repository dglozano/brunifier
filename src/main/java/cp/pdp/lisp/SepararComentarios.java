package main.java.cp.pdp.lisp;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

public class SepararComentarios extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme archivoTransformado = (ArchivoScheme) archivo;

		String comentarioStr = Scheme.Marca.comentario.toString();
		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			String linea = lineaOriginal.getCodigoLinea();
			Integer indiceComentario = linea.indexOf(comentarioStr);
			if(indiceComentario != -1){
				String codigo = linea.substring(0, indiceComentario);
				String comentario = linea.substring(indiceComentario);
				lineaOriginal.setCodigoLinea(codigo);
				lineaOriginal.setComentario(comentario);
			}
		});
		return archivo;
	}
}
