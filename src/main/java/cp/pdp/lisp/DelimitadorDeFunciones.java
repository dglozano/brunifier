package main.java.cp.pdp.lisp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

public class DelimitadorDeFunciones extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoScheme archivoTransformado = (ArchivoScheme) archivo;
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<String> pilaDeFuncionesAbiertas = new ArrayList<>();

		String aperturaStr = Scheme.Marca.aperturaBloque.toString();
		String cierreStr = Scheme.Marca.cierreBloque.toString();
		String ifStr = Scheme.Marca.iF.toString();
		List<String> pilaIFOriginal = Arrays.asList(
				" CAMINO POR FALSO DE " + ifStr,
				" CAMINO POR VERDADERO DE " + ifStr,
				" CONDICION DE " + ifStr);
		List<String> pilaIF = new ArrayList<>();

		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			Integer indice = 0;
			while(indice < lineaOriginal.getCodigoLinea().length()){
				Integer indiceApertura = lineaOriginal.abreBloque(indice);
				if(indiceApertura != -1){
					indice = indiceApertura + aperturaStr.length();

					String nombreFuncion = lineaOriginal.getNombreFuncion(indice);
					if(nombreFuncion != null){
						if(nombreFuncion.contains(aperturaStr)){
							nombreFuncion = "";
						}
						else if(nombreFuncion.contains(ifStr)){
							pilaIF.addAll(pilaIFOriginal);
						}
						indice += nombreFuncion.length();
						pilaDeFuncionesAbiertas.add(nombreFuncion);
					}
				}
				else{
					Integer indiceCierre = lineaOriginal.cierraBloque(indice);
					if(indiceCierre != -1){
						indice = indiceCierre + cierreStr.length();

						String funcionTopePila = pilaDeFuncionesAbiertas.remove(pilaDeFuncionesAbiertas.size() - 1);
						if(!pilaDeFuncionesAbiertas.isEmpty()){
							String funcionContenedora = pilaDeFuncionesAbiertas.get(pilaDeFuncionesAbiertas.size() - 1);
							if(funcionContenedora.equals(ifStr)){
								String finalStr = pilaIF.remove(pilaIF.size() - 1);
								lineaOriginal.setMarca("CIERRA " + funcionTopePila + " EN " + finalStr);
							}
							else{
								lineaOriginal.setMarca("CIERRA " + funcionTopePila + " EN " + funcionContenedora);
							}
						}
						else{
							lineaOriginal.setMarca("CIERRA " + funcionTopePila);
						}
					}
					else{
						if(!pilaDeFuncionesAbiertas.isEmpty()){
							String funcionTopePila = pilaDeFuncionesAbiertas.get(pilaDeFuncionesAbiertas.size() - 1);
							if(funcionTopePila.equals(ifStr)){
								String finalStr = pilaIF.remove(pilaIF.size() - 1);
								lineaOriginal.setMarca(finalStr);
							}
						}
						indice = lineaOriginal.getCodigoLinea().length();
					}
				}
			}
		});
		return archivo;
	}
}
