package main.java.cp.pdp.st;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

public class FormatoEstandar extends ComponenteDeProcesamiento {

	private static final GNUSmalltalk.Marca[] marcasAProcesar = new GNUSmalltalk.Marca[] {
			GNUSmalltalk.Marca.aperturaBloque,
			GNUSmalltalk.Marca.cierreBloque
	};

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		/*
		 * Inserta saltos de línea después de un bloque de apertura o de cierre.
		 * Como cada línea se puede porcesar de forma independiente, se hace de forma paralela
		 * Los ArrayLists son mejores para procesamiento en paralelo.
		 */
		ArchivoGNUSmalltalk nuevoArchivo = new ArchivoGNUSmalltalk();
		new ArrayList<>(archivo.getLineas()).parallelStream().map(linea -> linea.getCodigoLinea()).map(lineaActual -> {
			List<String> lineaTransformada = new ArrayList<>();
			Map<Integer, GNUSmalltalk.Marca> mapa = new TreeMap<>();
			this.hacerMapaCon(mapa, lineaActual);

			while(!mapa.keySet().isEmpty()){
				Integer indice = mapa.keySet().iterator().next();
				GNUSmalltalk.Marca marca = mapa.remove(indice);
				if(indice + 1 != lineaActual.length()){
					String primeraParteLineaConMarca;
					switch(marca) {
					case aperturaBloque:
					case cierreBloque:
						primeraParteLineaConMarca = lineaActual.substring(0, indice + 1);
						lineaTransformada.add(primeraParteLineaConMarca.trim());

						lineaActual = lineaActual.substring(indice + 1, lineaActual.length());
						this.hacerMapaCon(mapa, lineaActual);
						break;
					default:
						break;
					}
				}
			}

			lineaTransformada.add(lineaActual.trim());
			return lineaTransformada;

			//Al finalizar paso de procesamiento paralelo a secuencial para mantener el orden de las líneas
		}).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList())
				.forEach(linea -> nuevoArchivo.addLinea(linea));
		return nuevoArchivo;
	}

	private void hacerMapaCon(Map<Integer, GNUSmalltalk.Marca> mapa, String lineaConMarca) {
		mapa.clear();
		Arrays.stream(marcasAProcesar).forEach(marca -> {
			Integer indiceMarca = lineaConMarca.indexOf(marca.toString());
			if(indiceMarca != -1){
				mapa.put(indiceMarca, marca);
			}
		});
	}
}
