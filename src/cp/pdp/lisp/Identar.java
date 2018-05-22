package cp.pdp.lisp;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

public class Identar extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme archivoTransformado = (ArchivoScheme) archivo;
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<Boolean> pilaDeTokens = new ArrayList<>();

		String aperturaStr = Scheme.Marca.aperturaBloque.toString();
		String cierreStr = Scheme.Marca.cierreBloque.toString();

		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			Integer cierre = (lineaOriginal.getCodigoLinea().trim().equals(cierreStr)) ? 1 : 0;
			lineaOriginal.setCodigoLinea(this.identar(pilaDeTokens.size() - cierre) + lineaOriginal.getCodigoLinea());

			Integer indice = 0;
			while(indice < lineaOriginal.getCodigoLinea().length()){
				Integer indiceApertura = lineaOriginal.abreBloque(indice);
				if(indiceApertura != -1){
					indice = indiceApertura + aperturaStr.length();
					pilaDeTokens.add(false);
				}
				else{
					Integer indiceCierre = lineaOriginal.cierraBloque(indice);
					if(indiceCierre != -1){
						indice = indiceCierre + cierreStr.length();
						pilaDeTokens.remove(pilaDeTokens.size() - 1);
					}
					else{
						indice = lineaOriginal.getCodigoLinea().length();
					}
				}
			}
		});
		return archivo;
	}

	private String identar(int i) {
		String identacion = "";
		for(int j = 0; j < i; j++){
			identacion += "\t";
		}
		return identacion;
	}
}
