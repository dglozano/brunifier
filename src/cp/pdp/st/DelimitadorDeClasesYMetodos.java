package cp.pdp.st;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;

public class DelimitadorDeClasesYMetodos extends ComponenteDeProcesamiento {

	private static final String inicioClase = GNUSmalltalk.Marca.inicioClase.toString();
	private static final String aperturaBloque = GNUSmalltalk.Marca.aperturaBloque.toString();
	private static final String cierreBloque = GNUSmalltalk.Marca.cierreBloque.toString();

	@Override
	public List<String> ejecutar(List<String> archivo) {
		List<String> archivoTransformado = new LinkedList<>();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<String> pilaDeBloquesAbiertos = new ArrayList<>();
		List<String> pilaDeClase = new ArrayList<>();
		List<String> pilaDeMetodo = new ArrayList<>();
		List<Boolean> pilaDeTipoMetodoClase = new ArrayList<>();

		archivo.forEach(lineaActual -> {
			if(!lineaActual.isEmpty()){
				if(abreBloque(lineaActual.substring(lineaActual.length() - 1, lineaActual.length()))){
					String aux = lineaActual.substring(0, lineaActual.length() - aperturaBloque.length());
					Integer iInicioClase = lineaActual.indexOf(inicioClase);
					if(iInicioClase != -1){
						aux = aux.substring(iInicioClase + inicioClase.length(), aux.length()).trim();
						pilaDeClase.add(aux);
						lineaActual += " \"Comienzo de la clase " + aux + "\"";
					}
					else if(pilaDeClase.size() == 1 && pilaDeBloquesAbiertos.size() == 1){
						aux = aux.trim();
						if(lineaActual.contains(">>")){
							pilaDeTipoMetodoClase.add(true);
							lineaActual += " \"Comienzo de metodo de clase\"";
							aux = aux.substring(pilaDeClase.get(pilaDeClase.size() - 1).length() + 10, aux.length()).trim();
							pilaDeMetodo.add(aux);
						}
						else{
							pilaDeTipoMetodoClase.add(false);
							lineaActual += " \"Comienzo de metodo de instancia\"";
							pilaDeMetodo.add(aux);
						}
					}
					pilaDeBloquesAbiertos.add(aux);
				}
				else{
					if(cierraBloque(lineaActual.substring(lineaActual.length() - 1, lineaActual.length()))){
						String previoBloque = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						if(pilaDeBloquesAbiertos.size() == 0){
							lineaActual += " \"Fin de clase " + pilaDeClase.remove(pilaDeClase.size() - 1) + "\"";
						}
						else if(pilaDeClase.size() == 1 && pilaDeBloquesAbiertos.size() == 1){
							boolean esMetodoDeClase = pilaDeTipoMetodoClase.remove(pilaDeTipoMetodoClase.size() - 1);
							String nombreMetodo = previoBloque;
							if(esMetodoDeClase){
								lineaActual += " \"Fin de metodo de clase " + nombreMetodo + "\"";
							}
							else{
								lineaActual += " \"Fin de metodo " + nombreMetodo + "\"";
							}
						}
					}
				}
			}
			archivoTransformado.add(lineaActual);
		});
		return archivoTransformado;
	}

	private boolean abreBloque(String c) {
		return (c.equals(aperturaBloque));
	}

	private boolean cierraBloque(String c) {
		return (c.equals(cierreBloque));
	}

}
