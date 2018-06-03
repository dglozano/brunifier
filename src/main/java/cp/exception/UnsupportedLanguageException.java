package main.java.cp.exception;

public class UnsupportedLanguageException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedLanguageException() {
		super("Lenguaje no soportado");
	}
}
