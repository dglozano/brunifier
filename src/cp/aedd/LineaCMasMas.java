package cp.aedd;

import cp.Linea;

public class LineaCMasMas extends Linea {
	
	private boolean numeroLineaYaMostrado;
	private String marca;
	
	@Override
	public String toString() {
		return (!this.marca.isEmpty()) ? this.getCodigoLinea() + " // " + this.marca : this.getCodigoLinea();
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
	
	public String getMarca() { return marca; }
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public boolean abreBloque() {
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == '{';
	}
	
	public boolean cierraBloque() {
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == '}';
	}
	
	public String getCodigoLineaSinFinal() {
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
		if(this.marca.isEmpty()) {
			this.setMarca(nuevaMarca);
		} else {
			this.marca += " " + nuevaMarca;
		}
	}
	
	public String getMarcaConNumero() {
		return numeroLineaYaMostrado ? this.getMarca() : this.getMarca() + " EN LINEA " + this.getNumeroLinea();
	}
	
	public boolean esCabeceraDeFuncion() {
		boolean esCabecera = false;
		String l = this.getCodigoLinea();
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
	
	public static int contarPalabras(String l) {
		String trim = l.trim();
		if (trim.isEmpty())
		    return 0;
		return trim.split("\\s+").length;
	}
}
