package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.GuiaTuristicoExisteException;
import co.edu.uniquindio.pr3.exceptions.GuiaTuristicoVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Lengua;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaCrearGuiaController implements Initializable {

    @FXML
    private Button btnAnadirGuia;

    @FXML
    private Button btnAnadirLengua;

    @FXML
    private Button btnEliminarLengua;

    @FXML
    private ComboBox<Lengua> comboBoxLenguas;

    @FXML
    private ListView<Lengua> listViewLenguas;

    @FXML
    private TextField txtFieldExp;

    @FXML
    private TextField txtFieldIdentificacion;

    @FXML
    private TextField txtFieldNombre;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private ArrayList<Lengua> listaLenguas = new ArrayList<>();

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    @FXML
    void AnadirGuiaTuristico(ActionEvent event) {
        String nombre = txtFieldNombre.getText();
        String identificacion = txtFieldIdentificacion.getText();
        int experiencia = Integer.parseInt(txtFieldExp.getText());
        try {
            agenciaViajes.crearGuiaTuristico(nombre, identificacion, experiencia, listaLenguas);
        } catch (GuiaTuristicoVacioException | GuiaTuristicoExisteException e) {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("Error"), prop.getProperty("Error"), e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AnadirLengua(ActionEvent event) {
        Lengua idiomaSeleccionado = comboBoxLenguas.getValue();
        if (idiomaSeleccionado != null && !listaLenguas.contains(idiomaSeleccionado)) {
            listaLenguas.add(idiomaSeleccionado);
            actualizarListView();
        }else {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), prop.getProperty("NotSelectedLanguage"));
        }
    }

    @FXML
    void EliminarLengua(ActionEvent event) {
        Lengua idiomaSeleccionado = listViewLenguas.getSelectionModel().getSelectedItem();
        if (idiomaSeleccionado != null) {
            listaLenguas.remove(idiomaSeleccionado);
            actualizarListView();
        }
    }

    private void actualizarListView() {
        ObservableList<Lengua> observableListaIdiomas = FXCollections.observableArrayList(listaLenguas);
        listViewLenguas.setItems(observableListaIdiomas);
    }
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);
        listViewLenguas.setEditable(false);

        Lengua[] lengua = Lengua.values();
        comboBoxLenguas.getItems().addAll(lengua);
    }

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

