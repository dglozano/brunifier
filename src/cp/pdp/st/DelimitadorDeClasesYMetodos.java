package cp.pdp.st;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;

public class DelimitadorDeClasesYMetodos extends ComponenteDeProcesamiento {

	@Override
	public List<String> ejecutar(List<String> archivo) {
		List<String> archivoTransformado = new LinkedList<>();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<String> pilaDeBloquesAbiertos = new ArrayList<>();
		List<String> pilaDeClase = new ArrayList<>();
		List<String> pilaDeMetodo = new ArrayList<>();
		List<Boolean> pilaDeTipoMetodoClase = new ArrayList<>();

		archivo.forEach(lineaOriginal -> {
			String lineaConMarca = lineaOriginal;
			if(!lineaConMarca.isEmpty()){
				if(abreBloque(lineaOriginal.substring(lineaOriginal.length() - 1, lineaOriginal.length()))){
					String aux = lineaOriginal.substring(0, lineaOriginal.length() - GNUSmalltalk.Marca.aperturaBloque.toString().length());
					Integer iInicioClase = lineaOriginal.indexOf(GNUSmalltalk.Marca.inicioClase.toString());
					if(iInicioClase != -1){
						aux = aux.substring(iInicioClase + GNUSmalltalk.Marca.inicioClase.toString().length(), aux.length()).trim();
						pilaDeClase.add(aux);
						lineaConMarca += " \"Comienzo de la clase " + aux + "\"";
					}
					else if(pilaDeClase.size() == 1 && pilaDeBloquesAbiertos.size() == 1){
						aux = aux.trim();
						if(lineaOriginal.contains(">>")){
							pilaDeTipoMetodoClase.add(true);
							lineaConMarca += " \"Comienzo de metodo de clase\"";
							aux = aux.substring(pilaDeClase.get(pilaDeClase.size() - 1).length() + 10, aux.length()).trim();
							pilaDeMetodo.add(aux);
						}
						else{
							pilaDeTipoMetodoClase.add(false);
							lineaConMarca += " \"Comienzo de metodo de instancia\"";
							pilaDeMetodo.add(aux);
						}
					}
					pilaDeBloquesAbiertos.add(aux);
				}
				else{
					if(cierraBloque(lineaOriginal.substring(lineaOriginal.length() - 1, lineaOriginal.length()))){
						String previoBloque = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						if(pilaDeBloquesAbiertos.size() == 0){
							lineaConMarca += " \"Fin de clase " + pilaDeClase.remove(pilaDeClase.size() - 1) + "\"";
						}
						else if(pilaDeClase.size() == 1 && pilaDeBloquesAbiertos.size() == 1){
							boolean esMetodoDeClase = pilaDeTipoMetodoClase.remove(pilaDeTipoMetodoClase.size() - 1);
							String nombreMetodo = previoBloque;
							if(esMetodoDeClase){
								lineaConMarca += " \"Fin de metodo de clase " + nombreMetodo + "\"";
							}
							else{
								lineaConMarca += " \"Fin de metodo " + nombreMetodo + "\"";
							}
						}
					}
				}
			}
			archivoTransformado.add(lineaConMarca);
		});
		return archivoTransformado;
	}

	private boolean abreBloque(String c) {
		return (c.equals(GNUSmalltalk.Marca.aperturaBloque.toString()));
	}

	private boolean cierraBloque(String c) {
		return (c.equals(GNUSmalltalk.Marca.cierreBloque.toString()));
	}

}
