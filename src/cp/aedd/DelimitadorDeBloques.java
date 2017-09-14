package cp.aedd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;

public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public List<String> ejecutar(List<String> archivo) {
		List<String> archivoTransformado = new LinkedList<>();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<String> pilaDeBloquesAbiertos = new ArrayList<>();
		List<String> pilaDeIf = new ArrayList<>();
		List<String> pilaDeSwitch = new ArrayList<>();

		archivo.forEach(lineaOriginal -> {
			String lineaConMarca = "";
			if(abreBloque(lineaOriginal.charAt(lineaOriginal.length() - 1))){
				String aux = lineaOriginal.substring(0, lineaOriginal.length() - 2);
				if(aux.endsWith(":")){
					aux = aux.substring(0, aux.length() - 1);
				}
				if(lineaOriginal.startsWith("if")){
					pilaDeIf.add(aux);
				}
				else if(lineaOriginal.startsWith("switch")){
					pilaDeSwitch.add(aux);
				}
				if(lineaOriginal.contains("else")){
					aux = aux + " DE " + pilaDeIf.remove(pilaDeIf.size() - 1);
				}
				else{
					if(lineaOriginal.startsWith("case") || lineaOriginal.startsWith("default")){
						aux = aux + " DE " + pilaDeSwitch.get(pilaDeSwitch.size() - 1);
					}
				}
				pilaDeBloquesAbiertos.add(aux);
				lineaConMarca = lineaOriginal;
			}
			else{
				if(cierraBloque(lineaOriginal.charAt(lineaOriginal.length() - 1))){
					String marca = "//CIERRA EL BLOQUE DE " + pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					lineaConMarca = lineaOriginal + marca;
					if(marca.startsWith("//CIERRA EL BLOQUE DE switch")){
						pilaDeSwitch.remove(pilaDeSwitch.size() - 1);
					}
				}
				else{
					if(lineaOriginal.equals("};")){
						String marca = "//CIERRA LA DEFINICI�N DE " + pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						lineaConMarca = lineaOriginal + marca;
					}
					else{
						lineaConMarca = lineaOriginal;
					}
				}
			}
			archivoTransformado.add(lineaConMarca);
		});
		return archivoTransformado;
	}

	private boolean abreBloque(char c) {
		return (c == '{');
	}

	private boolean cierraBloque(char c) {
		return (c == '}');
	}

}
