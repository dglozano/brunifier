package main.java.cp.pdp.st;

import main.java.cp.model.Linea;

public class LineaGNUSmalltalk extends Linea {

	private String marca;

	@Override
	public String toString() {
		return (!this.marca.isEmpty()) ? this.getCodigoLinea() + " // " + this.marca : this.getCodigoLinea();
	}

	public LineaGNUSmalltalk() {
		super();
		this.marca = "";
	}

	public LineaGNUSmalltalk(String codigoLinea, String marca, int numLinea) {
		super();
		this.setCodigoLinea(codigoLinea);
		this.setNumeroLinea(numLinea);
		this.marca = marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public boolean abreBloque() {
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == '[';
	}

	public boolean cierraBloque() {
		return this.getCodigoLinea().charAt(getCodigoLinea().length() - 1) == ']';
	}

	public void addMarca(String nuevaMarca) {
		if(this.marca.isEmpty()){
			this.setMarca(nuevaMarca);
		}
		else{
			this.marca += " " + nuevaMarca;
		}
	}
}
