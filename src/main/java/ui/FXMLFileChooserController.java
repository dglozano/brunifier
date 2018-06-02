package main.java.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.cp.aedd.CMasMasProcedural;
import main.java.cp.exception.UnsupportedLanguageException;
import main.java.cp.model.Archivo;
import main.java.cp.model.Lenguaje;
import main.java.cp.model.Procesamiento;
import main.java.cp.pdp.lisp.Scheme;
import main.java.cp.pdp.st.GNUSmalltalk;
import main.java.cp.pdp.st.PharoToGNUSmalltalk;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLFileChooserController implements Initializable {

    @FXML
    private ComboBox<Lenguaje> cbLenguajes;
    @FXML
    private ListView<File> archivosListView;
    @FXML
    private TextField directorioSalidaInput;
    private File directorioSalida;

    private Stage primaryStage;

    @FXML
    private void salir(ActionEvent event){
        Platform.exit();
    }

    @FXML
    private void aceptar(ActionEvent event){
        Lenguaje lenguaje = cbLenguajes.getValue();
        List<File> archivos = archivosListView.getItems();
        if(archivos !=null && !archivos.isEmpty() && directorioSalida != null){
            procesar(lenguaje, archivos, directorioSalida);
        } else {
            mostrarError("Debe seleccionar al menos un archivo para procesar y un directorio destino.");
        }
    }

    @FXML
    private void seleccionarDestino(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if(selectedDirectory != null){
            directorioSalida = selectedDirectory;
            directorioSalidaInput.setText(selectedDirectory.toString());
        }
    }

    @FXML
    private void seleccionarArchivos(ActionEvent event) {
        //Obtiene lenguaje de los archivos a seleccionar
        Lenguaje lenguaje = cbLenguajes.getValue();
        //Muestra el file chooser para seleccionar el o los archivos a procesar
        List<File> archivos = getFileChooser(lenguaje).showOpenMultipleDialog(primaryStage);
        //Los agrega al list view
        if(archivos !=null && !archivos.isEmpty()){
            ObservableList<File> data = FXCollections.observableArrayList();
            data.addAll(archivos);
            archivosListView.setItems(data);
        }
    }

    private void procesar(Lenguaje lenguaje, List<File> archivos, File destino) {
        try{
            //Un Procesamiento consiste en una cadena de ComponenteDeProcesamiento aplicadas al lenguaje
            Procesamiento procesamiento = new Procesamiento(lenguaje);

            if(archivos != null){
                //Procesa en paralelo cada archivo seleccionado
                archivos.parallelStream().forEach(fileIn -> {
                    //Crea el archivo de salida con el nombre compuesto por el nombre y extension original,
                    //pero añadiendo "ConMarcas" al final del mismo.
                    String[] nombreExtension = this.stripExtension(fileIn.getName());
                    File fileOut = new File(destino, nombreExtension[0] + "ConMarcas" + nombreExtension[1]);
                    if(fileOut.exists()){
                        //Si ya existe un archivo de salida con ese nombre lo borra
                        fileOut.delete();
                    }
                    //Ejecuta en un nuevo hilo el procesamiento del archivo
                    new Thread(() -> procesamiento.run(fileIn, fileOut)).start();
                });
            }
            Platform.exit();
        } catch(UnsupportedLanguageException e){
            e.printStackTrace();
            mostrarError(e.getMessage());
        }
    }

    // Devuelve un array con dos strings: el nombre del archivo hasta el "." y el nombre de la extension
    private String[] stripExtension(String str) {

        // Handle null case specially.
        if(str == null){
            return null;
        }

        // Get position of last '.'.
        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if(pos == -1){
            return new String[] { str, "" };
        }

        // Otherwise return the string, up to the dot.
        return new String[] { str.substring(0, pos), str.substring(pos) };
    }

    // Retorna un FileChooser con los filtros especificos para un Lenguaje dado (por ejemplo C++ --> .cpp)
    private FileChooser getFileChooser(Lenguaje lenguaje) {
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter(lenguaje.getNombreFiltro(), lenguaje.getTiposFiltro());

        FileChooser archivoSeleccionado = new FileChooser();
        archivoSeleccionado.getExtensionFilters().add(filtro);

        return archivoSeleccionado;
    }

    // Muestra un alert con un mensaje de error
    private void mostrarError(String errorMsg) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        try{
            error.initOwner(primaryStage);
        } catch(NullPointerException exc){
            exc.printStackTrace();
        }
        error.setContentText(errorMsg);
        error.setHeaderText(null);
        error.setTitle("ERROR: " + errorMsg);
        error.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbLenguajes.getItems().addAll(this.lenguajesSoportados());
        cbLenguajes.getSelectionModel().select(0);
        cbLenguajes.setMaxWidth(Double.MAX_VALUE);
    }

    //TODO al agregar un lenguaje, agregarlo a la lista del método
    public List<Lenguaje> lenguajesSoportados() {
        List<Lenguaje> lenguajesSoportados = new ArrayList<>();
        lenguajesSoportados.add(new CMasMasProcedural());
        lenguajesSoportados.add(new GNUSmalltalk());
        lenguajesSoportados.add(new PharoToGNUSmalltalk());
        lenguajesSoportados.add(new Scheme());
        return lenguajesSoportados;
    }
}
