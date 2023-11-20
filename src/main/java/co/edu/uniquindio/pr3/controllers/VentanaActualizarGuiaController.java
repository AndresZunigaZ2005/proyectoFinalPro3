package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.GuiaTuristicoExisteException;
import co.edu.uniquindio.pr3.exceptions.GuiaTuristicoVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.GuiaTuristico;
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

public class VentanaActualizarGuiaController implements Initializable {

    @FXML
    private Button btnActualizarGuia;

    @FXML
    private Button btnAgregarIdiomas;

    @FXML
    private Button btnEliminarIdiomas;

    @FXML
    private ComboBox<Lengua> comboBoxIdiomas;

    @FXML
    private ComboBox<GuiaTuristico> comboBoxSeleccionarGuia;

    @FXML
    private ListView<Lengua> listViewIdiomas = new ListView<>();

    @FXML
    private TextField txtFieldExp;

    @FXML
    private TextField txtFieldIdentificacion;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private ArrayList<Lengua> listaLenguas = new ArrayList<>();

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private ObservableList<GuiaTuristico> listaGuias = FXCollections.observableArrayList(agenciaViajes.getListaGuiasTuristicos());

    @FXML
    void actualizarGuia(ActionEvent event) {
        String nombre = comboBoxSeleccionarGuia.getValue().getNombre();
        String identificacion = txtFieldIdentificacion.getText();
        int expereiencia = Integer.parseInt(txtFieldExp.getText());
        try {
            agenciaViajes.actualizarGuiaTuristico(nombre, identificacion, expereiencia, listaLenguas);
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), "Se ha actualizado correctamente");
        } catch (GuiaTuristicoVacioException | GuiaTuristicoExisteException e) {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void agregarIdioma(ActionEvent event) {
        Lengua idiomaSeleccionado = comboBoxIdiomas.getValue();
        if (idiomaSeleccionado != null && !listaLenguas.contains(idiomaSeleccionado)) {
            listaLenguas.add(idiomaSeleccionado);
            actualizarListView();
        }else {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), "Escoja otro idioma");
        }
    }

    @FXML
    void eliminarIdioma(ActionEvent event) {
        Lengua idiomaSeleccionado = listViewIdiomas.getSelectionModel().getSelectedItem();
        if (idiomaSeleccionado != null) {
            listaLenguas.remove(idiomaSeleccionado);
            actualizarListView();
        }
        else{
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), prop.getProperty("NotSelectedLanguage"));
        }
    }

    @FXML
    void mostrarGuia(ActionEvent event) {
        GuiaTuristico guiaObtenido = comboBoxSeleccionarGuia.getValue();
        txtFieldIdentificacion.setText(guiaObtenido.getIdentificacion());
        txtFieldExp.setText(guiaObtenido.getExperiencia()+"");
        listaLenguas.addAll(guiaObtenido.getListaLenguas());
        actualizarListView();
    }

    private void actualizarListView() {
        ObservableList<Lengua> observableListaIdiomas = FXCollections.observableArrayList(listaLenguas);
        listViewIdiomas.setItems(observableListaIdiomas);
    }

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);
        listViewIdiomas.setEditable(false);

        Lengua[] lengua = Lengua.values();
        comboBoxIdiomas.getItems().addAll(lengua);

        comboBoxSeleccionarGuia.getItems().addAll(listaGuias);
    }
}

