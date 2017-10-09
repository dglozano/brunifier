package cp.aedd;

import cp.Linea;

public class LineaCMasMas extends Linea {
	
	private boolean numeroLineaYaMostrado;

	@Override
	public String getLineaConMarca() {
		return (!this.getMarca().isEmpty()) ? this.getCodigoLinea() + "// " + this.getMarca() : this.getCodigoLinea();
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
		String viejaMarca = this.getMarca();
		this.setMarca(viejaMarca + " " + nuevaMarca);
	}
	
	public String getMarcaConNumero() {
		return numeroLineaYaMostrado ? this.getMarca() : this.getMarca() + " EN LINEA " + this.getNumeroLinea();
	}
}
