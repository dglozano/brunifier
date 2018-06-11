package main.java.cp.pdp.lisp;

import main.java.cp.model.Linea;

public class LineaScheme extends Linea {

	private String marca = "";

	private String comentario = "";

	@Override
	public String toString() {
		return this.getCodigoLinea() + ((!this.marca.isEmpty()) ? " ; " + this.marca : "") +
				((!this.comentario.isEmpty()) ? this.comentario : "");
	}

	public LineaScheme() {
		super();
		this.marca = "";
	}

	public LineaScheme(String codigoLinea, String comentario, String marca, int numLinea) {
		super();
		this.setCodigoLinea(codigoLinea);
		this.setNumeroLinea(numLinea);
		this.comentario = comentario;
		this.marca = marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer abreBloque(int desde) {
		return this.getCodigoLinea().indexOf(Scheme.Marca.aperturaBloque.toString(), desde);
	}

	public Integer cierraBloque(int desde) {
		return this.getCodigoLinea().indexOf(Scheme.Marca.cierreBloque.toString(), desde);
	}

	public void addMarca(String nuevaMarca) {
		if(this.marca.isEmpty()){
			this.setMarca(nuevaMarca);
		}
		else{
			this.marca += " " + nuevaMarca;
		}
	}

	public String getNombreFuncion(int desde) {
		return this.getCodigoLinea().substring(desde).trim().split("\\s+")[0];
	}
}
