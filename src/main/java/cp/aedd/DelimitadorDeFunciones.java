package main.java.cp.aedd;

import java.util.ArrayList;
import java.util.List;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

/**
 *
 * Este ComponenteDeProcesamiento se encarga de agregar un comentario al INICIO de la definicion de una
 * funcion indicando entre que lineas esta definida la misma. El comentario al CIERRE de la funcion es añadido
 * por el CP DelimitadorDeBloques.
 *
 * Por ejemplo:
 * 11	void funcion() { // FUNCION DEFINIDA ENTRE LAS LINEAS 11 Y 14
 * 12		for(int i=0;i<5;i++)
 * 13			cout << "Hola" << endl;
 * 14	} // CIERRA EL BLOQUE DE void funcion() EN LINEA 11
 *
 */
public class DelimitadorDeFunciones extends ComponenteDeProcesamiento {

	private boolean apilar = false;

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoCMasMas archivoTransformado = (ArchivoCMasMas) archivo;
		List<LineaCMasMas> pilaDeBloquesAbiertos = new ArrayList<>();
		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			int numLinea = lineaOriginal.getNumeroLinea();
			if(lineaOriginal.abreBloque()){
				if(lineaOriginal.esCabeceraDeFuncion()){
					// Solo si la linea es una cabecera de funcion, empiezo a apilar bloques
					apilar = true;
					pilaDeBloquesAbiertos.add(lineaOriginal);
				}
				else{
					/*
					 * Si la linea abre un bloque que no es una cabecera de funcion y apilar esta en "true"
					 * quiere decir que los bloques estan dentro de la implementacion de la funcion, entonces los apilo.
					 */
					if(apilar && !lineaOriginal.esDo()){
						pilaDeBloquesAbiertos.add(lineaOriginal);
					}
				}
			}
			else{
				if(apilar && lineaOriginal.cierraBloque()){
					//Si estoy apilando los bloques de una funcion y cierra un bloque, desapilo uno...
					LineaCMasMas topePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					if(pilaDeBloquesAbiertos.isEmpty()){
						/*
						 * ... y si la pila queda vacia, es porque la linea desapilada corresponde a la cabecera de funcion
						 * Entonces, reemplazo esa linea del archivo por si misma, mas la marca, y dejo de apilar porque
						 * termino la definicion de la funcion
						 */
						topePila.addMarca("FUNCION DEFINIDA ENTRE LAS LINEAS");
						topePila.addMarca(topePila.getNumeroLinea() + " Y " + numLinea);
						archivoTransformado.putLinea(topePila);
						apilar = false;
					}
				}
			}
		});
		return archivo;
	}
}