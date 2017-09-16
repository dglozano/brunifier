package cp.pdp.st;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cp.ComponenteDeProcesamiento;
import cp.pdp.st.GNUSmalltalk.Marca;

public class FormatoEstandar extends ComponenteDeProcesamiento {

	@Override
	public List<String> ejecutar(List<String> archivo) {
		List<String> archivoTransformado = new LinkedList<>();

		archivo.forEach(lineaOriginal -> {
			String lineaConMarca = lineaOriginal.trim();
			Map<Integer, GNUSmalltalk.Marca> mapa = new TreeMap<>();
			this.hacerMapaCon(mapa, lineaConMarca);

			while(!mapa.keySet().isEmpty()){
				Integer indice = mapa.keySet().iterator().next();
				GNUSmalltalk.Marca marca = mapa.remove(indice);
				String primeraParteLineaConMarca;
				if(indice != -1){
					switch(marca) {
					case aperturaBloque:
					case cierreBloque:
						primeraParteLineaConMarca = lineaConMarca.substring(0, indice + 1);
						archivoTransformado.add(primeraParteLineaConMarca);

						lineaConMarca = lineaConMarca.substring(indice + 1, lineaConMarca.length());
						this.hacerMapaCon(mapa, lineaConMarca);
						break;
					default:
						break;
					}
				}
			}

			archivoTransformado.add(lineaConMarca);
		});
		return archivoTransformado;
	}

	private void hacerMapaCon(Map<Integer, Marca> mapa, String lineaConMarca) {
		mapa.clear();
		Integer iApertura = lineaConMarca.indexOf(GNUSmalltalk.Marca.aperturaBloque.toString());
		Integer iCierre = lineaConMarca.indexOf(GNUSmalltalk.Marca.cierreBloque.toString());
		mapa.put(iApertura, GNUSmalltalk.Marca.aperturaBloque);
		mapa.put(iCierre, GNUSmalltalk.Marca.cierreBloque);
	}
}
