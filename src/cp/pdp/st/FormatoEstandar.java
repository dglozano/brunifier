package cp.pdp.st;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cp.ComponenteDeProcesamiento;
import cp.pdp.st.GNUSmalltalk.Marca;

public class FormatoEstandar extends ComponenteDeProcesamiento {

	private static final GNUSmalltalk.Marca[] marcasAProcesar = new GNUSmalltalk.Marca[] {
			GNUSmalltalk.Marca.aperturaBloque,
			GNUSmalltalk.Marca.cierreBloque
	};

	@Override
	public List<String> ejecutar(List<String> archivo) {
		//Inserta saltos de línea después de un bloque de apertura o de cierre.
		//Como cada línea se puede porcesar de forma independiente lo hago de forma paralela
		return new ArrayList<>(archivo).parallelStream().map(lineaOriginal -> {
			List<String> lineaTransformada = new ArrayList<>();
			Map<Integer, GNUSmalltalk.Marca> mapa = new TreeMap<>();
			this.hacerMapaCon(mapa, lineaOriginal);

			while(!mapa.keySet().isEmpty()){
				Integer indice = mapa.keySet().iterator().next();
				GNUSmalltalk.Marca marca = mapa.remove(indice);
				if(indice + 1 != lineaOriginal.length()){
					String primeraParteLineaConMarca;
					switch(marca) {
					case aperturaBloque:
					case cierreBloque:
						primeraParteLineaConMarca = lineaOriginal.substring(0, indice + 1);
						lineaTransformada.add(primeraParteLineaConMarca.trim());

						lineaOriginal = lineaOriginal.substring(indice + 1, lineaOriginal.length());
						this.hacerMapaCon(mapa, lineaOriginal);
						break;
					default:
						break;
					}
				}
			}

			lineaTransformada.add(lineaOriginal.trim());
			return lineaTransformada;

			//Al finalizar paso de procesamiento paralelo a secuencial para mantener el orden de las líneas
		}).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
	}

	private void hacerMapaCon(Map<Integer, Marca> mapa, String lineaConMarca) {
		mapa.clear();
		Arrays.stream(marcasAProcesar).forEach(marca -> {
			Integer indiceMarca = lineaConMarca.indexOf(marca.toString());
			if(indiceMarca != -1){
				mapa.put(indiceMarca, marca);
			}
		});
	}
}
