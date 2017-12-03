package cp.pdp.lisp;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

public class DelimitadorDeQuotes extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme archivoTransformado = (ArchivoScheme) archivo;
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<Boolean> pilaDeTokens = new ArrayList<>();

		String aperturaStr = Scheme.Marca.aperturaBloque.toString();
		String cierreStr = Scheme.Marca.cierreBloque.toString();
		String quoteStr = Scheme.Marca.quote.toString();

		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			Integer indice = 0;
			while(indice < lineaOriginal.getCodigoLinea().length()){
				Integer indiceApertura = lineaOriginal.abreBloque(indice);
				if(indiceApertura != -1){
					indice = indiceApertura + aperturaStr.length();

					Integer indiceQuote = indiceApertura - quoteStr.length();
					Boolean hayQuoteBloque = false;
					if(indiceQuote != -1){
						hayQuoteBloque |= lineaOriginal.getCodigoLinea().substring(indiceQuote, indiceApertura)
								.equals(quoteStr);
					}
					if(!pilaDeTokens.isEmpty()){
						hayQuoteBloque |= pilaDeTokens.get(pilaDeTokens.size() - 1);
					}
					pilaDeTokens.add(hayQuoteBloque);
				}
				else{
					Integer indiceCierre = lineaOriginal.cierraBloque(indice);
					if(indiceCierre != -1){
						indice = indiceCierre + cierreStr.length();

						Boolean token = pilaDeTokens.remove(pilaDeTokens.size() - 1);
						if(!pilaDeTokens.isEmpty()){
							Boolean proximoToken = pilaDeTokens.get(pilaDeTokens.size() - 1);
							if(token){
								if(proximoToken){
									lineaOriginal.setMarca("");
								}
								else{
									lineaOriginal.setMarca("CIERRA LISTA");
								}
							}
						}
					}
					else{
						indice = lineaOriginal.getCodigoLinea().length();
					}
				}
			}
		});
		return archivo;
	}
}
