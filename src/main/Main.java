package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import cp.ComponenteDeProcesamiento;
import cp.aedd.DelimitadorDeBloques;
import cp.aedd.FormatoEstandar;

public class Main {
	
	private static int CMasMasProcedural = 0;
	
	public static void main(String[] args) {
		String nombre = "";
		LinkedList<String> archivo = new LinkedList<String>();
		LinkedList<ComponenteDeProcesamiento> proceso = new LinkedList<ComponenteDeProcesamiento>();
		generarConfiguracion(proceso,elegirLenguajeOrigen());
		while(nombre.isEmpty()) nombre = pedirNombreArchivoOrigen();
		leerArchivo(nombre,archivo);
		ejecutarComponentesDeProcesamiento(proceso,archivo);
		escribirArchivo(nombre,archivo);
	}
	
	private static int elegirLenguajeOrigen(){
		//TODO Hacer un menú que permita elegir el lenguaje de origen (valido), por ahora retorno 0 que es C++ procedural.
		return 0;
	}
	
	private static void generarConfiguracion(LinkedList<ComponenteDeProcesamiento> proceso,int lenguaje){
		if(lenguaje==CMasMasProcedural) {
			proceso.addLast(new FormatoEstandar());
			proceso.addLast(new DelimitadorDeBloques()); 
		}
		//TODO Completar para otros lenguajes. La lista debe generarse en el orden de procesamiento requerido.
	}
	
	private static String pedirNombreArchivoOrigen(){
		try {
			InputStreamReader isr = new InputStreamReader(System.in); //TODO Mejorar esto para que tenga una interfaz muy simple de donde se lea el nombre del archivo.
			BufferedReader br = new BufferedReader (isr);	
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace(); //TODO Manejar excepción.
			return "";
		}
	}
	
	private static boolean leerArchivo(String nombre,LinkedList<String> archivo) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			file = new File ("C:\\"+nombre); //TODO Ver ubicación - Tal vez, generar una carpeta específica donde almacenarlos?
		    fr = new FileReader (file);
		    br = new BufferedReader(fr);
		    String linea;
		    while((linea=br.readLine())!=null) archivo.addLast(linea);
		}
		catch(Exception e){
			e.printStackTrace(); //TODO Manejar excepción.
		}
		finally {
			try {                    
				if( null != fr ) fr.close();
				return true;
		    }
			catch (Exception e2){ 
				e2.printStackTrace(); //TODO Manejar excepción.
			}
		}
		return false;
	}
	
	private static void ejecutarComponentesDeProcesamiento(LinkedList<ComponenteDeProcesamiento> proceso,LinkedList<String> archivo){
		int size = proceso.size();
		for(int i=0;i<size;i++) {
			ComponenteDeProcesamiento actual = proceso.removeFirst();
			actual.ejecutar(archivo);
		}
	}
			
	private static boolean escribirArchivo(String nombre,LinkedList<String> archivo) {
		FileWriter fichero = null;
	    PrintWriter pw = null;
		try {
			fichero = new FileWriter("D:\\" + nombre.substring(0,nombre.indexOf(".")) + "ConMarcas" + nombre.substring(nombre.indexOf("."),nombre.length())); //TODO Ver ubicación (por ahora está en D:) - Tal vez, generar una carpeta específica donde almacenarlos?
			pw = new PrintWriter(fichero);
			int cant = archivo.size();
			for (int i=0;i<cant;i++) pw.println(archivo.removeFirst());
		} 
    	catch (Exception e) {
            e.printStackTrace(); //TODO Manejar excepción.
        } 
    	finally {
    		try {
    			if (null != fichero) fichero.close();
    			return true;
    		} 
    		catch (Exception e2) {
    			e2.printStackTrace(); //TODO Manejar excepción.
		    }
    	}
		return false;
	}
	
}