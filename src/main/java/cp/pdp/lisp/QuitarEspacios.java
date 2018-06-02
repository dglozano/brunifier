package main.java.cp.pdp.lisp;

import java.util.Iterator;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

public class QuitarEspacios extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		/*
		 * Quita saltos de línea después de cualquier cosa que no sea un bloque de cierre.
		 */
		ArchivoScheme nuevoArchivo = new ArchivoScheme();
		Iterator<?> itLineasArchivo = archivo.getLineas().iterator();
		String marcaQuote = Scheme.Marca.quote.toString();
		String abreBloque = Scheme.Marca.aperturaBloque.toString();
		String cierraBloque = Scheme.Marca.cierreBloque.toString();
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
				if(!codigoLineaActual.endsWith(marcaQuote) || !codigoLineaActual.endsWith(abreBloque)){
					acumuladorCodigo = acumuladorCodigo.trim()
							.replaceAll("\\" + marcaQuote + "\\s+\\" + abreBloque, marcaQuote + abreBloque)
							.replaceAll("\\" + abreBloque + "\\s+", abreBloque)
							.replaceAll("\\s+" + "\\" + cierraBloque, cierraBloque);
					nuevoArchivo.addLinea(acumuladorCodigo);
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
