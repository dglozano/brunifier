package cp.aedd;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

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
					LineaCMasMas topePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					if(pilaDeBloquesAbiertos.isEmpty()){
						/*
						 * Si la pila queda vacia, es porque la linea removida corresponde a la cabecera de funcion
						 * Entonces, reemplazo esa linea del archivo por la misma mas la marca y dejo de apilar
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