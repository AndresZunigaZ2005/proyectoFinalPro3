package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Destino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaCreacionPaquetesController implements Initializable {

    @FXML
    private Button btnAgregarDestino;

    @FXML
    private Button btnCrearPaqueteTuristico;

    @FXML
    private Button btnEliminarDestino;

    @FXML
    private ComboBox<Destino> comboBoxDestinos;

    @FXML
    private DatePicker datePickerFecha;

    @FXML
    private ListView<Destino> listViewDestinos;

    @FXML
    private Spinner<Integer> spinnerHora;

    @FXML
    private Spinner<Integer> spinnerMin;

    @FXML
    private TextArea txtAreaServicios;

    @FXML
    private TextField txtFieldCupoMaximo;

    @FXML
    private TextField txtFieldDuracion;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldPrecio;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @FXML
    void agregarDestino(ActionEvent event) {

    }

    @FXML
    void crearPaqueteTuristico(ActionEvent event) {

    }

    @FXML
    void eliminarDestino(ActionEvent event) {

    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);

        Destino[] listaDestinosCombo = (Destino[]) agenciaViajes.getListaDestinos().toArray();
        comboBoxDestinos.getItems().addAll(listaDestinosCombo);
    }

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
