package cp.aedd;

import java.util.ArrayList;
import java.util.List;

import cp.Archivo;
import cp.ComponenteDeProcesamiento;

public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public Archivo ejecutar(Archivo archivo) {
		ArchivoCMasMas archivoTransformado = (ArchivoCMasMas) archivo;
		//ArrayList es más eficiente para quitar al final, es decir, como pila
		List<LineaCMasMas> pilaDeBloquesAbiertos = new ArrayList<>();
		List<LineaCMasMas> pilaDeIf = new ArrayList<>();
		List<LineaCMasMas> pilaDeSwitch = new ArrayList<>();
		archivoTransformado.getLineasCMasMas().forEach(lineaOriginal -> {
			if(lineaOriginal.abreBloque()) {
				int numLinea = lineaOriginal.getNumeroLinea();
				LineaCMasMas lineaEnPila = new LineaCMasMas("", lineaOriginal.getCodigoLineaSinFinal(), numLinea);
				if(lineaOriginal.esIf()) {
					pilaDeIf.add(lineaEnPila);
				}
				else if(lineaOriginal.esSwitch()) {
					pilaDeSwitch.add(lineaEnPila);
				}
				if(lineaOriginal.tieneElse()) {
					String aux = "EN LINEA " + numLinea + " DE " + pilaDeIf.remove(pilaDeIf.size() - 1).getMarcaConNumero();
					lineaEnPila.addMarca(aux) ;
					lineaEnPila.setNumeroLineaYaMostrado(true);
				}
				else {
					if(lineaOriginal.esCase() || lineaOriginal.esDefault()) {
						String aux = "EN LINEA " + numLinea + " DE " + pilaDeSwitch.get(pilaDeSwitch.size() - 1).getMarcaConNumero();
						lineaEnPila.addMarca(aux) ;
						lineaEnPila.setNumeroLineaYaMostrado(true);
					}
				}
				if(!lineaOriginal.esDo()) {
					pilaDeBloquesAbiertos.add(lineaEnPila);
				}
			}
			else{
				if(lineaOriginal.cierraBloque()){
					LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
					lineaOriginal.setMarca("CIERRA EL BLOQUE DE " + lineaTopePila.getMarcaConNumero());
					if(lineaTopePila.getMarca().startsWith("switch")){
						pilaDeSwitch.remove(pilaDeSwitch.size() - 1);
					}
				}
				else{
					if(lineaOriginal.cierraStruct()){
						LineaCMasMas lineaTopePila = pilaDeBloquesAbiertos.remove(pilaDeBloquesAbiertos.size() - 1);
						lineaOriginal.setMarca("CIERRA LA DEFINICION DE " + lineaTopePila.getMarcaConNumero());
					}
				}
			}
		});
		return archivoTransformado;
	}
}
