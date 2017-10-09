package cp.aedd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cp.ComponenteDeProcesamiento;
import cp.Linea;

public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public List<Linea> ejecutar(List<Linea> archivo) {
		List<Linea> archivoTransformado = new LinkedList<>();
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<LineaCMasMas> pilaDeBloquesAbiertos = new ArrayList<>();
		List<LineaCMasMas> pilaDeIf = new ArrayList<>();
		List<LineaCMasMas> pilaDeSwitch = new ArrayList<>();
		for(int numLinea=1; numLinea <= archivo.size(); numLinea++){
			LineaCMasMas lineaOriginalCMasMas = (LineaCMasMas)archivo.get(numLinea-1);
			if(lineaOriginalCMasMas.abreBloque()) {
				LineaCMasMas lineaEnPila = new LineaCMasMas();
				lineaEnPila.setNumeroLinea(numLinea);
				lineaEnPila.setCodigoLinea("");
				lineaEnPila.setMarca(lineaOriginalCMasMas.getCodigoLineaSinFinal());
				if(lineaOriginalCMasMas.esIf()) {
					pilaDeIf.add(lineaEnPila);
				}
				else if(lineaOriginalCMasMas.esSwitch()) {
					pilaDeSwitch.add(lineaEnPila);
				}
				if(lineaOriginalCMasMas.tieneElse()) {
					String aux = "EN LINEA " + numLinea + " DE " + pilaDeIf.remove(pilaDeIf.size() - 1).getMarcaConNumero();
					lineaEnPila.addMarca(aux) ;
					lineaEnPila.setNumeroLineaYaMostrado(true);
				}
				else {
					if(lineaOriginalCMasMas.esCase() || lineaOriginalCMasMas.esDefault()) {
						String aux = "EN LINEA " + numLinea + " DE " + pilaDeSwitch.get(pilaDeSwitch.size() - 1).getMarcaConNumero();
						lineaEnPila.addMarca(aux) ;
						lineaEnPila.setNumeroLineaYaMostrado(true);
					}
				}
				if(!lineaOriginalCMasMas.esDo()) {
					pilaDeBloquesAbiertos.add(lineaEnPila);
				}
			}
			else{
				if(lineaOriginalCMasMas.cierraBloque()){
					LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					lineaOriginalCMasMas.setMarca("CIERRA EL BLOQUE DE " + lineaTopePila.getMarcaConNumero());
					if(lineaTopePila.getMarca().startsWith("switch")){
						pilaDeSwitch.remove(pilaDeSwitch.size() - 1);
					}
				}
				else{
					if(lineaOriginalCMasMas.cierraStruct()){
						LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						lineaOriginalCMasMas.setMarca("CIERRA LA DEFINICION DE " + lineaTopePila.getMarcaConNumero());
					}
				}
			}
			archivoTransformado.add(lineaOriginalCMasMas);
		}
		return archivoTransformado;
	}
}
