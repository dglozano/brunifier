package main;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//TODO Hacer un men� que permita elegir el lenguaje de origen (valido), por ahora C++ procedural.
		//TODO se podria hacer una interface que pregunte el lenguaje y la cantidad de archivos a procesar
		Integer veces = 1;
		List<String> args = getParameters().getRaw();
		if(args.size() == 1){
			try{
				veces = new Integer(args.get(0));
			} catch(Exception e){

			}
		}
		for(int i = 0; i < veces; i++){
			new Procesamiento(Lenguaje.CMasMasProcedural, primaryStage).run();
		}

		Platform.exit();
	}

}
