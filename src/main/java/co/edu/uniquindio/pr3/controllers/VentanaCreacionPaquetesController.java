package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.PaqueteExisteException;
import co.edu.uniquindio.pr3.exceptions.PaqueteUnoDiferenciaException;
import co.edu.uniquindio.pr3.exceptions.PaqueteVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Destino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private ListView<String> listViewDestinos;

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

    private ArrayList<String> listaDestinosPaquete = new ArrayList<>();
    private Properties prop;
    private FileInputStream input;

    @FXML
    void crearPaqueteTuristico(ActionEvent event) {
        String nombre = txtFieldNombre.getText();
        int duracion = Integer.parseInt(txtFieldDuracion.getText());
        String servicios = txtAreaServicios.getText();
        double precio = Double.parseDouble(txtFieldPrecio.getText());
        int cupoMaximo = Integer.parseInt(txtFieldCupoMaximo.getText());

        LocalDate selectedDate = datePickerFecha.getValue();
        int selectedHour = spinnerHora.getValue();
        int selectedMinute = spinnerMin.getValue();
        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

        LocalDateTime fecha = LocalDateTime.of(selectedDate, selectedTime);

        try {
            agenciaViajes.crearPaqueteTuristico(nombre, duracion, servicios, precio, cupoMaximo, fecha, pasarStringDestino());
        } catch (PaqueteVacioException | PaqueteUnoDiferenciaException | PaqueteExisteException e) {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("Error"), prop.getProperty("Error"), e.getMessage());
        }
    }

    @FXML
    void agregarDestino(ActionEvent event) {
        Destino destinoSeleccionado = comboBoxDestinos.getValue();
        if (destinoSeleccionado != null && !listaDestinosPaquete.contains(destinoSeleccionado.getNombre())) {
            listaDestinosPaquete.add(destinoSeleccionado.getNombre());
            actualizarListView();
        }
    }

    @FXML
    void eliminarDestino(ActionEvent event) {
        String destinoSeleccionado = listViewDestinos.getSelectionModel().getSelectedItem();
        if (destinoSeleccionado != null) {
            listaDestinosPaquete.remove(destinoSeleccionado);
            actualizarListView();
        }
    }

    private void actualizarListView() {
        ObservableList<String> observableListaDestinos = FXCollections.observableArrayList(listaDestinosPaquete);
        listViewDestinos.setItems(observableListaDestinos);
    }

    private ArrayList<Destino> pasarStringDestino(){
        ArrayList<Destino> listaDestinos = new ArrayList<>();
        for (String nombreDestino : listaDestinosPaquete) {
            listaDestinos.add(agenciaViajes.obtenerDestino(nombreDestino, 0));
        }
        return  listaDestinos;
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);

        Destino[] listaDestinosCombo = new Destino[agenciaViajes.getListaDestinos().size()];
        for (int i = 0; i < listaDestinosCombo.length; i++) {
            listaDestinosCombo[i] = agenciaViajes.getListaDestinos().get(i);
        }

        comboBoxDestinos.getItems().addAll(listaDestinosCombo);

        spinnerHora = new Spinner<>(0, 24, 0);
        spinnerMin = new Spinner<>(0, 59, 0);
    }

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
