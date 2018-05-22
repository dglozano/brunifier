package cp.pdp.lisp;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

public class AgregarSaltosDeLineaIfs extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme archivoTransformado = new ArchivoScheme();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<Boolean> pilaDeTokens = new ArrayList<>();

		String aperturaStr = Scheme.Marca.aperturaBloque.toString();
		String cierreStr = Scheme.Marca.cierreBloque.toString();
		String ifStr = Scheme.Marca.iF.toString();

		((ArchivoScheme) archivo).getLineas().forEach(lineaOriginal -> {
			Integer indice = 0;
			while(indice < lineaOriginal.getCodigoLinea().length()){
				Boolean lineaModificada = false;
				if(!pilaDeTokens.isEmpty()){
					Boolean enIF = pilaDeTokens.get(pilaDeTokens.size() - 1);
					if(enIF){
						String argumento = lineaOriginal.getNombreFuncion(indice);
						if(!argumento.contains(aperturaStr) && !argumento.equals(cierreStr)){
							if(argumento.contains(cierreStr)){
								argumento.substring(0, argumento.length() - cierreStr.length());
							}
							indice = lineaOriginal.getCodigoLinea().indexOf(argumento, indice) + argumento.length();
							archivoTransformado.addLinea(lineaOriginal.getCodigoLinea().substring(0, indice).trim());
							lineaOriginal.setCodigoLinea(lineaOriginal.getCodigoLinea().substring(indice).trim());
							indice = 0;
							lineaModificada = true;
						}
					}
				}
				if(!lineaModificada){
					Integer indiceApertura = lineaOriginal.abreBloque(indice);
					if(indiceApertura != -1){
						indice = indiceApertura + aperturaStr.length();

						String nombreFuncion = lineaOriginal.getNombreFuncion(indice);
						if(nombreFuncion.contains(aperturaStr)){
							nombreFuncion = "";
						}
						indice += nombreFuncion.length();
						Boolean enIF = nombreFuncion != null && nombreFuncion.equals(ifStr);
						pilaDeTokens.add(enIF);
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
			}
			archivoTransformado.getLineas().add(lineaOriginal);
		});
		return archivoTransformado;
	}
}
