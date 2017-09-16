package cp;

public class Linea {

	  private int numLinea;
	  private String codLinea;
	  private boolean numLineaYaMostrado; //Si es una linea con Marcas sobre bloques else o switch, el toString no agrega numLinea al final

	  public Linea(int numLinea, String codLinea) {
	    this.numLinea = numLinea;
	    this.codLinea = codLinea;
	    this.numLineaYaMostrado = false;
	  }

	  public int getNumLinea() { return numLinea; }
	  public String getCodLinea() { return codLinea; }
	  public String toString() {
		  return numLineaYaMostrado ? codLinea : codLinea + " EN LINEA " + numLinea;
	  }
	  
	  public Linea setCodLinea(String codLinea) {
		  this.codLinea = codLinea;
		  return this;
	  }
	  
	  public Linea setNumLinea(int numLinea) {
		  this.numLinea = numLinea;
		  return this;
	  }
	  
	  public void setNumLineaYaMostrado(boolean f) {
		  this.numLineaYaMostrado = f;
	  }
}
