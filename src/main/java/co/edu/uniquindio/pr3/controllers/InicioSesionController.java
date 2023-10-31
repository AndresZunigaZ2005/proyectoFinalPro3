package co.edu.uniquindio.pr3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioSesionController implements Initializable {
    @FXML
    private TextField correoTextField;

    @FXML
    private TextField contrasenaTextField;

    @FXML
    public void iniciarSesion() {
        String correo = correoTextField.getText();
        String contrasena = contrasenaTextField.getText();

        // Lógica para iniciar sesión con los valores de correo y contraseña
        // Puedes agregar tu lógica aquí
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

