package cp;

public abstract class Linea {

	  private int numeroLinea;
	  private String codigoLinea;
	  private String marca;

	  public int getNumeroLinea() { return numeroLinea; }
	  public String getCodigoLinea() { return codigoLinea; }
	  public String getMarca() { return marca; }
	  
	  public void setCodigoLinea(String codigoLinea) {
		  this.codigoLinea = codigoLinea;
	  }
	  
	  public void setNumeroLinea(int numeroLinea) {
		  this.numeroLinea = numeroLinea;
	  }
	  
	  public void setMarca(String marca) {
		  this.marca = marca;
	  }
	  
	  public abstract String getLineaConMarca();
}
