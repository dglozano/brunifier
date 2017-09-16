package cp.pdp.st;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;

/**
 * Importa código Pharo al formato de GNU Smalltalk.
 * Limitación: no soporta variables de clase.
 *
 * @author Pablo Marchetti
 */
public class ConversorPharoToGNUst extends ComponenteDeProcesamiento {

	private static final String inicioClase = PharoToGNUSmalltalk.Marca.inicioClase.toString();
	private static final String aperturaBloque = PharoToGNUSmalltalk.Marca.aperturaBloque.toString();
	private static final String cierreBloque = PharoToGNUSmalltalk.Marca.cierreBloque.toString();
	private static final String variablesInstancia = PharoToGNUSmalltalk.Marca.variablesInstacia.toString();
	private static final String marcaMetodos = PharoToGNUSmalltalk.Marca.metodos.toString();
	private static final String marcaClase = PharoToGNUSmalltalk.Marca.clase.toString();

	@Override
	public List<String> ejecutar(List<String> archivo) {

		List<String> archivoTransformado = new LinkedList<>();
		Iterator<String> entrada = archivo.iterator();

		String linea = null, clase = null, superclase = null;
		String[] pal = null;

		while(entrada.hasNext()){
			linea = entrada.next();
			if(!linea.isEmpty()){
				pal = linea.trim().split("\\s+");
				if(pal.length == 3 && pal[1].equals(inicioClase)){
					if(clase != null){
						archivoTransformado.add(cierreBloque + " \"final de clase " + clase + "\"");
						archivoTransformado.add("");
						archivoTransformado.add("");
					}
					clase = pal[2].substring(1);
					superclase = pal[0];
					archivoTransformado.add(superclase + " " + inicioClase + " " + clase + " " + aperturaBloque);

					linea = entrada.next();
					pal = linea.trim().split("\\s+");
					if(pal.length > 1 && pal[0].equals(variablesInstancia)){
						archivoTransformado.add("");
						archivoTransformado.add("\"Declaración de variables de instancia\"");

						String aux = "";
						for(int i = 1; i < pal.length; i++){
							String p = pal[i];
							p = p.replaceAll("'", "|");
							aux += p + " ";
						}

						archivoTransformado.add(aux);
						archivoTransformado.add("");
					}
				}
				if((pal.length > 2) && pal[1].equals(marcaMetodos)){
					this.procesarMetodo("", entrada, archivoTransformado);
				}
				if((pal.length > 3) && pal[1].equals(marcaClase) && pal[2].equals(marcaMetodos)){
					this.procesarMetodo(clase + " " + marcaClase + " >> ", entrada, archivoTransformado);
				}
			}
		}
		if(clase != null){
			archivoTransformado.add(cierreBloque + " \"final de clase " + clase + "\"");
		}

		return archivoTransformado;
	}

	private void procesarMetodo(String prefijo, Iterator<String> entrada, List<String> archivoTransformado) {
		String linea = null, selector = null, fin = null;
		String[] pal = null;
		linea = entrada.next();
		pal = linea.trim().split("\\s+");
		if(linea.contains(":")){
			selector = Arrays.stream(pal).filter(p -> p.charAt(p.length() - 1) == ':').reduce("", (s1, s2) -> s1 + s2);
		}
		else{
			selector = pal[0];
		}
		archivoTransformado.add(prefijo + linea + " " + aperturaBloque);
		linea = entrada.next();
		while(!linea.contains("!")){
			archivoTransformado.add(linea);
			linea = entrada.next();
		}
		fin = linea.replaceAll("!", "");
		archivoTransformado.add(fin);
		archivoTransformado.add(cierreBloque + " \"final de método " + prefijo + selector + "\"");
		archivoTransformado.add("");
	}
}
