package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class CambiarContrasenaController implements Initializable {

    @FXML
    private TextField codigoVerificacionField;

    @FXML
    private PasswordField nuevaContrasenaField;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @FXML
    void cambiarContrasena(ActionEvent event) {
        String correo = singletonController.getCorreoCambiarContrasena();
        String codigo = codigoVerificacionField.getText();
        String nuevaContrasena = nuevaContrasenaField.getText();
        if(codigo.equals(singletonController.getCodigoRecuperacionCorreo())){
            Cliente cliente = agenciaViajes.obtenerClienteCorreo(correo, 0);
            if(cliente.getContrasenia().equals(nuevaContrasena)) {
                showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("samePasswordError"));
            }
            else{
                cliente.setContrasenia(nuevaContrasena);
                showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("passwordChangedSuccessfully"));
            }
        }
        else{
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("error"), prop.getProperty("error"), prop.getProperty("wrongCodeEmailPassword"));
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prop = new Properties();
        input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);
    }
}
