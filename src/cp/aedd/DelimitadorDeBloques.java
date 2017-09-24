package cp.aedd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.Linea;

public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public List<String> ejecutar(List<String> archivo) {
		List<String> archivoTransformado = new LinkedList<>();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<Linea> pilaDeBloquesAbiertos = new ArrayList<>();
		List<Linea> pilaDeIf = new ArrayList<>();
		List<Linea> pilaDeSwitch = new ArrayList<>();
		int numLinea = 1;
		for(String lineaOriginal: archivo) {
			String lineaConMarca = "";
			if(abreBloque(lineaOriginal.charAt(lineaOriginal.length() - 1))){
				Linea lineaEnPila = new Linea(numLinea,"");
				String aux = lineaOriginal.substring(0, lineaOriginal.length() - 2);
				if(aux.endsWith(":")){
					aux = aux.substring(0, aux.length() - 1);
				}
				if(lineaOriginal.startsWith("if")){
					pilaDeIf.add(lineaEnPila.setCodLinea(aux));
				}
				else if(lineaOriginal.startsWith("switch")){
					pilaDeSwitch.add(lineaEnPila.setCodLinea(aux));
				}
				if(lineaOriginal.contains("else")){
					aux = aux + " EN LINEA " + numLinea + " DE " + pilaDeIf.remove(pilaDeIf.size() - 1).toString() ;
					lineaEnPila.setNumLineaYaMostrado(true);
				}
				else{
					if(lineaOriginal.startsWith("case") || lineaOriginal.startsWith("default")){
						aux = aux + " EN LINEA " + numLinea + " DE " + pilaDeSwitch.get(pilaDeSwitch.size() - 1).toString();
						lineaEnPila.setNumLineaYaMostrado(true);
					}
				}
				if(!lineaOriginal.startsWith("do")) {
					pilaDeBloquesAbiertos.add(lineaEnPila.setCodLinea(aux));
				}
				lineaConMarca = lineaOriginal;
			}
			else{
				if(cierraBloque(lineaOriginal.charAt(lineaOriginal.length() - 1))){
					String marca = "//CIERRA EL BLOQUE DE " + pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1).toString();
					lineaConMarca = lineaOriginal + marca;
					if(marca.startsWith("//CIERRA EL BLOQUE DE switch")){
						pilaDeSwitch.remove(pilaDeSwitch.size() - 1);
					}
				}
				else{
					if(lineaOriginal.equals("};")){
						String marca = "//CIERRA LA DEFINICION DE " + pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1).toString();
						lineaConMarca = lineaOriginal + marca;
					}
					else{
						lineaConMarca = lineaOriginal;
					}
				}
			}
			archivoTransformado.add(lineaConMarca);
			numLinea++;
		}
		return archivoTransformado;
	}

	private boolean abreBloque(char c) {
		return (c == '{');
	}

	private boolean cierraBloque(char c) {
		return (c == '}');
	}
}
