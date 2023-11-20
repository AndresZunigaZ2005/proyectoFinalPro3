package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.DestinoExisteException;
import co.edu.uniquindio.pr3.exceptions.DestinoVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Clima;
import co.edu.uniquindio.pr3.model.Destino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaActualizarDestinosController implements Initializable {

    @FXML
    private ComboBox<Clima> comboBoxCLima;

    @FXML
    private ComboBox<Destino> comboBoxDestino;

    @FXML
    private TextField txtFieldCiudad;

    @FXML
    private TextField txtFieldNombre;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();
    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @FXML
    void mostrarAtributosDestino(ActionEvent event) {
        Destino destinoObtenido = comboBoxDestino.getValue();
        txtFieldNombre.setText(destinoObtenido.getNombre());
        txtFieldCiudad.setText(destinoObtenido.getCiudad());
        comboBoxCLima.setValue(destinoObtenido.getClima());
    }

    @FXML
    void actualizarDestino(ActionEvent event) {
        String nombre = txtFieldNombre.getText();
        String ciudad = txtFieldCiudad.getText();
        Clima clima = comboBoxCLima.getValue();
        try {
            agenciaViajes.actualizarDestino(nombre, ciudad, comboBoxDestino.getValue().getImagenes(), clima);
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), "Se ha actualizado correctamente");
        } catch (DestinoVacioException | DestinoExisteException e) {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), e.getMessage());
        }
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

        Clima[] climas = Clima.values();
        comboBoxCLima.getItems().addAll(climas);

        comboBoxDestino.getItems().addAll(agenciaViajes.getListaDestinos());
    }
}

