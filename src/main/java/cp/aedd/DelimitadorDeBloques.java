package main.java.cp.aedd;

import java.util.ArrayList;
import java.util.List;

import main.java.cp.model.Archivo;
import main.java.cp.model.ComponenteDeProcesamiento;

/**
 * Este ComponenteDeProcesamiento se encarga de agregar un comentario luego de que un
 * bloque cierra, donde dicho comentario indica que bloque es el que esta cerrando y donde se abrio.
 *
 * Por ejemplo:
 *
 * 42	while(t<5) {
 * 43		t++;
 * 44	} // CIERRA EL BLOQUE DE while(t<5) EN LINEA 42
 * 45	switch(t+1) {
 * 46		case 0: break;
 * 47		default: {
 * 48		break;
 * 49	} // CIERRA EL BLOQUE DE default EN LINEA 47 DE switch(t+1) EN LINEA 45
 * 50	} // CIERRA EL BLOQUE DE switch(t+1) EN LINEA 45
 *
 */
public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public Archivo<?> ejecutar(Archivo<?> archivo) {
		ArchivoCMasMas archivoTransformado = (ArchivoCMasMas) archivo;
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<LineaCMasMas> pilaDeBloquesAbiertos = new ArrayList<>();
		List<LineaCMasMas> pilaDeIf = new ArrayList<>();
		List<LineaCMasMas> pilaDeSwitch = new ArrayList<>();
		//Por cada linea del archivo original
		archivoTransformado.getLineas().forEach(lineaOriginal -> {
			if(lineaOriginal.abreBloque()){
				//Si la linea abre un bloque (abre llave), voy a tener que añadirla a alguna de las 3 pilas
				int numLinea = lineaOriginal.getNumeroLinea();
				LineaCMasMas lineaEnPila = new LineaCMasMas("", lineaOriginal.getCodigoLineaSinFinal(), numLinea);
				if(lineaOriginal.esIf()){
					//si es un if, la apilo en la pila de ifs abiertos
					pilaDeIf.add(lineaEnPila);
				} else if(lineaOriginal.esSwitch()){
					//si es en cambio un switch, la apilo en la pila de switchs abiertos
					pilaDeSwitch.add(lineaEnPila);
				}
				if(lineaOriginal.tieneElse()){
					//si abre un else, agrego como marca de la linea que voy a apilar el numero de linea en la que se
					// abrio el else y el if al que corresponde el else
					String aux = "EN LINEA " + numLinea + " DE " + pilaDeIf.remove(pilaDeIf.size() - 1).getMarcaConNumero();
					lineaEnPila.addMarca(aux);
					lineaEnPila.setNumeroLineaYaMostrado(true);
				}
				else{
					//si no abre un else...
					if(lineaOriginal.esCase() || lineaOriginal.esDefault()){
						//y es una linea que indica un "case" o el "default" de un switch, agrego como marca de la linea
						//que voy a apilar el numero de linea en la que se abrio el case/deffault y el switch al que corresponde
						String aux = "EN LINEA " + numLinea + " DE " + pilaDeSwitch.get(pilaDeSwitch.size() - 1).getMarcaConNumero();
						lineaEnPila.addMarca(aux);
						lineaEnPila.setNumeroLineaYaMostrado(true);
					}
				}
				if(!lineaOriginal.esDo()){
					// finalmente, si la linea no corresponde a un "do", la apilo en la pila de bloques abiertos.
					pilaDeBloquesAbiertos.add(lineaEnPila);
				}
			}
			else{
				//si la linea cierra un bloque, voy a tener que desapilar y agregar la marca
				if(lineaOriginal.cierraBloque()){
					LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					lineaOriginal.setMarca("CIERRA EL BLOQUE DE " + lineaTopePila.getMarcaConNumero());
					if(lineaTopePila.getMarca().startsWith("switch")){
						pilaDeSwitch.remove(pilaDeSwitch.size() - 1);
					}
				}
				else{
					// si lo que cierra es un struct, la marca es diferente
					if(lineaOriginal.cierraStruct()){
						LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						lineaOriginal.setMarca("CIERRA LA DEFINICION DE " + lineaTopePila.getMarcaConNumero());
					}
				}
			}
		});
		return archivo;
	}
}
