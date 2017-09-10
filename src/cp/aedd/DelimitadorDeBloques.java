package cp.aedd;

import java.util.LinkedList;

import cp.ComponenteDeProcesamiento;

public class DelimitadorDeBloques extends ComponenteDeProcesamiento {

	@Override
	public void ejecutar(LinkedList<String> archivo) {
		LinkedList<String> archivoTransformado = new LinkedList<String>();
		LinkedList<String> pilaDeBloquesAbiertos = new LinkedList<String>();
		LinkedList<String> pilaDeIf = new LinkedList<String>();
		LinkedList<String> pilaDeSwitch = new LinkedList<String>();
		int cantidadLineas = archivo.size();
		for(int i=0;i<cantidadLineas;i++){
			String lineaConMarca="", lineaOriginal = archivo.removeFirst();
			if(abreBloque(lineaOriginal.charAt(lineaOriginal.length()-1))){
				String aux = lineaOriginal.substring(0,lineaOriginal.length()-2);
				if(aux.endsWith(":")) aux = aux.substring(0,aux.length()-1);
				if(lineaOriginal.startsWith("if")) pilaDeIf.addLast(aux);
				else if(lineaOriginal.startsWith("switch")) pilaDeSwitch.addLast(aux);
				if(lineaOriginal.contains("else")) {
					aux = aux + " DE " + pilaDeIf.removeLast();
				}
				else {
					if(lineaOriginal.startsWith("case")||lineaOriginal.startsWith("default")){
						aux = aux + " DE " + pilaDeSwitch.getLast();
					}
				}
				pilaDeBloquesAbiertos.addLast(aux);	
				lineaConMarca = lineaOriginal;				
			}
			else {
				if(cierraBloque(lineaOriginal.charAt(lineaOriginal.length()-1))){
					String marca = "//CIERRA EL BLOQUE DE " + pilaDeBloquesAbiertos.removeLast();
					lineaConMarca = lineaOriginal + marca;
					if(marca.startsWith("//CIERRA EL BLOQUE DE switch")) pilaDeSwitch.removeLast();
				}
				else {
					if(lineaOriginal.equals("};")) {
						String marca = "//CIERRA LA DEFINICIÓN DE " + pilaDeBloquesAbiertos.removeLast();
						lineaConMarca = lineaOriginal + marca;
					}
					else lineaConMarca = lineaOriginal;
				}
			}
			archivoTransformado.addLast(lineaConMarca);
		}	
		archivo.addAll(archivoTransformado);
	}
	
	private boolean abreBloque(char c){
		return (c=='{');
	}

	private boolean cierraBloque(char c){
		return (c=='}');
	}
	
}
