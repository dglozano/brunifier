package cp.pdp.lisp;

import java.util.Iterator;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

public class QuitarSaltosDeLinea extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme nuevoArchivo = new ArchivoScheme();
		Iterator<?> itLineasArchivo = archivo.getLineas().iterator();
		String acumuladorCodigo = "";
		String acumuladorMarca = "";
		String acumuladorComentario = "";
		while(itLineasArchivo.hasNext()){
			Object lineaActualObj = itLineasArchivo.next();
			if(lineaActualObj instanceof LineaScheme){
				LineaScheme lineaActual = (LineaScheme) lineaActualObj;
				String codigoLineaActual = lineaActual.getCodigoLinea().trim();
				acumuladorCodigo += " " + codigoLineaActual;
				acumuladorMarca += " " + lineaActual.getMarca().trim();
				acumuladorComentario += " " + lineaActual.getComentario().trim();
				if(codigoLineaActual.endsWith(Scheme.Marca.cierreBloque.toString())){
					nuevoArchivo.addLinea(acumuladorCodigo.trim());
					nuevoArchivo.getLineas().get(nuevoArchivo.getLineas().size() - 1).setMarca(acumuladorMarca.trim());
					nuevoArchivo.getLineas().get(nuevoArchivo.getLineas().size() - 1).setComentario(acumuladorComentario.trim());
					acumuladorCodigo = "";
					acumuladorMarca = "";
					acumuladorComentario = "";
				}
			}
		}
		return nuevoArchivo;
	}
}
