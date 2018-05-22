package cp.pdp.lisp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;
import cp.pdp.lisp.Scheme.Marca;

public class AgregarSaltosDeLinea extends ComponenteDeProcesamiento {

	private static final Scheme.Marca[] marcasAProcesar = new Scheme.Marca[] {
			Scheme.Marca.cierreBloque
	};

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		/*
		 * Inserta saltos de línea después de un bloque de cierre.
		 * Como cada línea se puede porcesar de forma independiente, se hace de forma paralela
		 * Los ArrayLists son mejores para procesamiento en paralelo.
		 */
		ArchivoScheme nuevoArchivo = new ArchivoScheme();
		List<List<String>> lineasDivididas = new ArrayList<>(archivo.getLineas()).parallelStream().map(linea -> linea.getCodigoLinea()).map(lineaActual -> {
			List<String> lineaTransformada = new ArrayList<>();
			Map<Integer, Scheme.Marca> mapa = new TreeMap<>();
			this.hacerMapaCon(mapa, lineaActual);

			while(!mapa.keySet().isEmpty()){
				Integer indice = mapa.keySet().iterator().next();
				Scheme.Marca marca = mapa.remove(indice);
				if(indice + 1 != lineaActual.length()){
					String primeraParteLineaConMarca;
					switch(marca) {
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
		}).collect(Collectors.toList());

		// Arma el archivo conservando las marcas
		Iterator<List<String>> itLineasDivididas = lineasDivididas.iterator();
		Iterator<?> itLineasArchivo = archivo.getLineas().iterator();
		while(itLineasDivididas.hasNext() && itLineasArchivo.hasNext()){
			itLineasDivididas.next().forEach(linea -> nuevoArchivo.addLinea(linea));
			List<LineaScheme> lineas = nuevoArchivo.getLineas();
			Object lineaActual = itLineasArchivo.next();
			if(lineaActual instanceof LineaScheme){
				lineas.get(lineas.size() - 1).setMarca(((LineaScheme) lineaActual).getMarca());
				lineas.get(lineas.size() - 1).setComentario(((LineaScheme) lineaActual).getComentario());
			}
		}
		return nuevoArchivo;
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
