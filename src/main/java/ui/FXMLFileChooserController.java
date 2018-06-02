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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.Main;
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
    private void salirEnter(KeyEvent event){
        if(KeyCode.ENTER == event.getCode()){
            Platform.exit();
        }
    }

    @FXML
    private void aceptar(ActionEvent event){
        aceptar();
    }

    @FXML
    private void aceptarEnter(KeyEvent event){
        if (KeyCode.ENTER == event.getCode()) {
            aceptar();
        }
    }

    @FXML
    private void seleccionarDestino(ActionEvent event){
        seleccionarDestino();
    }

    @FXML
    private void seleccionarDestinoEnter(KeyEvent event){
        if (KeyCode.ENTER == event.getCode()) {
            seleccionarDestino();
        }
    }

    @FXML
    private void seleccionarArchivos(ActionEvent event) {
        seleccionarArchivos();
    }

    @FXML
    private void seleccionarArchivosEnter(KeyEvent event){
        if (KeyCode.ENTER == event.getCode()) {
            seleccionarArchivos();
        }
    }

    private void seleccionarArchivos() {
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

    private void seleccionarDestino(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if(selectedDirectory != null){
            directorioSalida = selectedDirectory;
            directorioSalidaInput.setText(selectedDirectory.toString());
        }
    }

    private void aceptar(){
        Lenguaje lenguaje = cbLenguajes.getValue();
        List<File> archivos = archivosListView.getItems();
        if(archivos !=null && !archivos.isEmpty() && directorioSalida != null){
            procesar(lenguaje, archivos, directorioSalida);
        } else {
            mostrarError("Debe seleccionar al menos un archivo para procesar y un directorio destino.");
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
        error.setTitle("ERROR");
        error.showAndWait();
    }

    private void mostrarAyuda() {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        try{
            error.initOwner(primaryStage);
        } catch(NullPointerException exc){
            exc.printStackTrace();
        }

        StringBuilder str = new StringBuilder();
        str.append("CTRL + C: Selecciona lenguaje C++ \n");
        str.append("CTRL + G: Selecciona lenguaje GNU Smalltalk\n");
        str.append("CTRL + P: Selecciona lenguaje Pharo \n");
        str.append("CTRL + S: Selecciona lenguaje Scheme \n");
        str.append("F1: Seleccionar archivos \n");
        str.append("F2: Seleccionar carpeta destino \n");
        str.append("F3: Procesar \n");
        str.append("F8: Mostrar ayuda \n");
        str.append("ESC: Salir");

        error.setContentText(str.toString());
        error.setHeaderText(null);
        error.setTitle("INFORMACION");
        error.showAndWait();
    }

    public void setStageAndInitializeKeyCombinations(Stage primaryStage) {
        this.primaryStage = primaryStage;

        setKeyCombinationForLanguage(KeyCode.C, 0); // C++ index 0
        setKeyCombinationForLanguage(KeyCode.G, 1); // GNU Smalltalk index 1
        setKeyCombinationForLanguage(KeyCode.P, 2); // Pharo index 2
        setKeyCombinationForLanguage(KeyCode.S, 3); // Scheme index 3

        primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.F1 == event.getCode()) {
                seleccionarArchivos();
            }
            else if(KeyCode.F2 == event.getCode()){
                seleccionarDestino();
            }
            else if(KeyCode.F3 == event.getCode()){
                aceptar();
            }
            else if(KeyCode.F8 == event.getCode()){
                mostrarAyuda();
            }
        });
    }

    private void setKeyCombinationForLanguage(KeyCode keycode, int i){
        this.primaryStage.getScene().getAccelerators().put(
                new KeyCodeCombination(keycode, KeyCombination.CONTROL_ANY),
                new Runnable() {
                    @FXML public void run() {
                        cbLenguajes.getSelectionModel().select(i);
                    }
                }
        );
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