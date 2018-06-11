package main.java.cp.aedd;

import main.java.cp.model.Linea;

public class LineaCMasMas extends Linea {

	private boolean numeroLineaYaMostrado;
	private String marca;

	@Override
	public String toString() {
		return this.getCodigoLinea() + ((!this.marca.isEmpty()) ? "// " + this.marca : "");
	}

	public LineaCMasMas() {
		super();
		this.marca = "";
		this.numeroLineaYaMostrado = false;
	}

	public LineaCMasMas(String codigoLinea, String marca, int numLinea) {
		super();
		this.setCodigoLinea(codigoLinea);
		this.setNumeroLinea(numLinea);
		this.marca = marca;
		this.numeroLineaYaMostrado = false;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	// Una Linea de C++ abre un bloque si tiene una llave abriendo al final
	public boolean abreBloque() {
        if(esLineaVacia())
            return false;
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == '{';
	}

	// Una Linea de C++ cierra un bloque si tiene una llave cerrando al final
	public boolean cierraBloque() {
        if(esLineaVacia())
            return false;
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == '}';
	}

	// Devuelve el string de la linea sin el ; final. Si es un case/default de un switch, le quita el :
	public String getCodigoLineaSinFinal() {
        if(esLineaVacia())
            return "";
		String aux = this.getCodigoLinea().substring(0, this.getCodigoLinea().length() - 2);
		if(aux.endsWith(":")){
			aux = aux.substring(0, aux.length() - 1);
		}
		return aux;
	}

	public boolean esIf() {
		return this.getCodigoLinea().startsWith("if");
	}

	public boolean esSwitch() {
		return this.getCodigoLinea().startsWith("switch");
	}

	public boolean esDo() {
		return this.getCodigoLinea().startsWith("do");
	}

	public boolean tieneElse() {
		return this.getCodigoLinea().contains("else");
	}

	//Este flag sirve para no mostrar varias veces el numero de linea en los comentarios de las estructuras anidadas
	public void setNumeroLineaYaMostrado(boolean flag) {
		this.numeroLineaYaMostrado = flag;
	}

	public boolean getNumeroLineaYaMostrado() {
		return this.numeroLineaYaMostrado;
	}

	public boolean esCase() {
		return this.getCodigoLinea().startsWith("case");
	}

	public boolean esDefault() {
		return this.getCodigoLinea().startsWith("default");
	}

	public boolean cierraStruct() {
		return this.getCodigoLinea().equals("};");
	}

	public void addMarca(String nuevaMarca) {
		if(this.marca.isEmpty()){
			this.setMarca(nuevaMarca);
		}
		else{
			this.marca += " " + nuevaMarca;
		}
	}

	public String getMarcaConNumero() {
		return numeroLineaYaMostrado ? this.getMarca() : this.getMarca() + " EN LINEA " + this.getNumeroLinea();
	}

	//Determina si una linea es la cabecera de una funcion.
	public boolean esCabeceraDeFuncion() {
	    if(esLineaVacia())
	        return false;
		boolean esCabecera = false;
		String l = this.getCodigoLinea();
		//Tiene que tener un parentesis que abre y otro que cierra despues del que abre
		int parentesisAbrePos = l.indexOf('(');
		int parentesisCierraPos = l.indexOf(')', parentesisAbrePos);
		if(parentesisAbrePos != -1 && parentesisCierraPos != -1){
			// Tiene que tener dos palabras separadas por un espacio antes de los parentesis
			String primeraParte = l.substring(0, parentesisAbrePos);
			esCabecera = contarPalabras(primeraParte) == 2;
		}
		return esCabecera;
	}

	public static int contarPalabras(String l) {
		String trim = l.trim();
		if(trim.isEmpty()){
			return 0;
		}
		return trim.split("\\s+").length;
	}

	public boolean esLineaVacia(){
		return this.getCodigoLinea().length() == 0;
	}
}
