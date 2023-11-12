package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class InicioSesionController implements Initializable {

    @FXML
    private Hyperlink btnRecuperarContrasenia;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();
    @FXML
    void IniciarSesion(ActionEvent event) {
        Cliente cliente = agenciaViajes.obtenerCliente(usernameField.getText());
        if(cliente == null){
            showAlert(Alert.AlertType.WARNING, prop.getProperty("warning"), prop.getProperty("warning"), prop.getProperty("bodywrongDatalogIn"));
        }else{
            singletonController.setCliente(cliente);
        }
    }

    @FXML
    void enviarCorreoRecuperacionContrasenia(ActionEvent event) {
        if(usernameField.getText().isBlank() || usernameField.getText() == null){
            showAlert(Alert.AlertType.INFORMATION,prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("BodyrecoverTheEmailAlert"));
        }else{

        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);
    }

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
