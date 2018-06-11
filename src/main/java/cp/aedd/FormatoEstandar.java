package main.java.cp.aedd;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

public class FormatoEstandar extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoCMasMas archivoTransformado = (ArchivoCMasMas) archivo;
		archivoTransformado.getLineas().forEach(lineaOriginal -> {
		    String codigoLinea = lineaOriginal.getCodigoLinea();
			codigoLinea = codigoLinea.trim();
			if(codigoLinea.contains("{")){
                codigoLinea =  codigoLinea.replace("{"," {");
            }
            codigoLinea = codigoLinea.trim();
		    if(codigoLinea.contains("//")){
		        int posicionBarra = codigoLinea.lastIndexOf("/");
		        String comentario = codigoLinea.substring(posicionBarra+1, codigoLinea.length());
		        String codigoAntesComentario = codigoLinea.substring(0, posicionBarra-1);
		        lineaOriginal.addMarca(comentario);
		        lineaOriginal.setCodigoLinea(codigoAntesComentario);
            } else {
                lineaOriginal.setCodigoLinea(codigoLinea);
            }
		});
		return archivoTransformado;
	}

	/*
	 * TODO Este modulo debe dejar todas las lineas sin indentaci�n y sin blancos al final de las lineas
	 * (para poder preguntar por startWith y endsWith), sin lineas en blanco (para que todas las lineas sean procesables)
	 * y con un �nico blanco antes de la instrucci�n y la llave que abre un bloque (o sea, por ejemplo "if() {").
	 */
}
