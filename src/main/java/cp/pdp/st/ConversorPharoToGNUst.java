package main.java.cp.pdp.st;

import java.util.Arrays;
import java.util.Iterator;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;
import main.java.cp.model.Linea;

/**
 * Importa código Pharo al formato de GNU Smalltalk.
 *
 * @author Pablo Marchetti
 */
public class ConversorPharoToGNUst extends ComponenteDeProcesamiento {

	private static final String inicioClase = PharoToGNUSmalltalk.Marca.inicioClase.toString();
	private static final String aperturaBloque = PharoToGNUSmalltalk.Marca.aperturaBloque.toString();
	private static final String cierreBloque = PharoToGNUSmalltalk.Marca.cierreBloque.toString();
	private static final String variablesInstancia = PharoToGNUSmalltalk.Marca.variablesInstacia.toString();
	private static final String variablesClase = PharoToGNUSmalltalk.Marca.variablesClase.toString();
	private static final String marcaMetodos = PharoToGNUSmalltalk.Marca.metodos.toString();
	private static final String marcaClase = PharoToGNUSmalltalk.Marca.clase.toString();

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoGNUSmalltalk archivoTransformado = new ArchivoGNUSmalltalk();
		Iterator<? extends Linea> entrada = archivo.getLineas().iterator();

		String linea = null, clase = null, superclase = null;
		String[] pal = null;

		while(entrada.hasNext()){
			linea = entrada.next().getCodigoLinea();
			if(!linea.isEmpty()){
				pal = linea.trim().split("\\s+");
				if(pal.length == 3 && pal[1].equals(inicioClase)){
					if(clase != null){
						archivoTransformado.addLinea(cierreBloque + " \"final de clase " + clase + "\"");
						archivoTransformado.addLinea("");
						archivoTransformado.addLinea("");
					}
					clase = pal[2].substring(1);
					superclase = pal[0];
					archivoTransformado.addLinea(superclase + " " + inicioClase + " " + clase + " " + aperturaBloque);

					linea = entrada.next().getCodigoLinea();
					pal = linea.trim().split("\\s+");
					if(pal.length > 1 && (pal.length > 2 || pal[1].length() > 2) && pal[0].equals(variablesInstancia)){
						archivoTransformado.addLinea("");
						archivoTransformado.addLinea("\"Declaración de variables de instancia\"");

						String aux = "";
						for(int i = 1; i < pal.length; i++){
							String p = pal[i];
							p = p.replaceAll("'", "|");
							aux += p + " ";
						}

						archivoTransformado.addLinea(aux);
						archivoTransformado.addLinea("");
					}

					linea = entrada.next().getCodigoLinea();
					pal = linea.trim().split("\\s+");
					if(pal.length > 1 && (pal.length > 2 || pal[1].length() > 2) && pal[0].equals(variablesClase)){
						archivoTransformado.addLinea("");
						archivoTransformado.addLinea("\"Declaración de variables de clase\"");

						for(int i = 1; i < pal.length; i++){
							String p = pal[i];
							p = p.replaceAll("'", "");
							archivoTransformado.addLinea(p + " := nil.");
						}

						archivoTransformado.addLinea("");
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
			archivoTransformado.addLinea(cierreBloque + " \"final de clase " + clase + "\"");
		}

		return archivoTransformado;
	}

	private void procesarMetodo(String prefijo, Iterator<? extends Linea> entrada, ArchivoGNUSmalltalk archivoTransformado) {
		String linea = null, selector = null, fin = null;
		String[] pal = null;
		linea = entrada.next().getCodigoLinea();
		pal = linea.trim().split("\\s+");
		if(linea.contains(":")){
			selector = Arrays.stream(pal).filter(p -> p.charAt(p.length() - 1) == ':').reduce("", (s1, s2) -> s1 + s2);
		}
		else{
			selector = pal[0];
		}
		archivoTransformado.addLinea(prefijo + linea + " " + aperturaBloque);
		linea = entrada.next().getCodigoLinea();
		while(!linea.contains("!")){
			archivoTransformado.addLinea(linea);
			linea = entrada.next().getCodigoLinea();
		}
		fin = linea.replaceAll("!", "");
		archivoTransformado.addLinea(fin);
		archivoTransformado.addLinea(cierreBloque + " \"final de método " + prefijo + selector + "\"");
		archivoTransformado.addLinea("");
	}
}
