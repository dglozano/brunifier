package cp.aedd;

import java.util.ArrayList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.Linea;

public class DelimitadorDeFunciones extends ComponenteDeProcesamiento {
	
	//TODO Hacer que la Clase Linea tenga atributo marca, atributo linea original y metodos utiles comunes a todos los cp
	
	@Override
	public List<String> ejecutar(List<String> archivo) {
		// Uso Array List porque es mas eficiente la operacion get(i) y no necesito hacer add()
		List<String> archivoTransformado = new ArrayList<>(archivo);
		List<Linea> pilaDeBloquesAbiertos = new ArrayList<>();
		boolean apilar = false;
		int numLinea = 1;
		for(String lineaOriginal: archivo) {
			if(abreBloque(lineaOriginal.charAt(lineaOriginal.length() - 1))){
				Linea lineaEnPila = new Linea(numLinea,"");
				String aux = lineaOriginal.substring(0, lineaOriginal.length() - 2);
				if(esCabeceraDeFuncion(lineaOriginal)){
					//Solo si es una cabecera de funcion, empiezo a apilar bloques
					apilar = true;
					pilaDeBloquesAbiertos.add(lineaEnPila.setCodLinea(aux));
				}
				else {
					if(apilar && !lineaOriginal.startsWith("do")) {
						pilaDeBloquesAbiertos.add(lineaEnPila.setCodLinea(aux));
					}
				}
			}
			else{
				if(apilar && lineaOriginal.startsWith("}")){
					Linea topePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					if(pilaDeBloquesAbiertos.size() == 0) {
						/*
						 * Si la pila queda vacia, es porque la linea removida corresponde a la cabecera de funcion
						 * Entonces, reemplazo esa linea del archivo por la misma mas la marca y dejo de apilar
						 */
						String aux = topePila.getCodLinea();
						aux += "// FUNCION DEFINIDA ENTRE LAS LINEAS " + topePila.getNumLinea();
						aux += " Y " + numLinea;
						archivoTransformado.set(topePila.getNumLinea()-1, aux);
						apilar = false;
					}
				}
			}
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
	
	private boolean esCabeceraDeFuncion(String l) {
		boolean esCabecera = false;
		//Tiene que tener un parentesis que abre y otro que cierra despues del que abre
		int parentesisAbrePos = l.indexOf('(');
		int parentesisCierraPos = l.indexOf(')', parentesisAbrePos);
		if(parentesisAbrePos != -1 && parentesisCierraPos != -1) {
			// Tiene que tener dos palabras separadas por un espacio antes de los parentesis
			String primeraParte = l.substring(0, parentesisAbrePos);
			esCabecera = contarPalabras(primeraParte) == 2;
		}
		return esCabecera;
	}
	
	private int contarPalabras(String s) {
		String trim = s.trim();
		if (trim.isEmpty())
		    return 0;
		return trim.split("\\s+").length;
	}
}