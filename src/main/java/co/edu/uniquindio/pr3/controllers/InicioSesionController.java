package co.edu.uniquindio.pr3.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioSesionController implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonAction(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Aquí debes implementar la lógica de autenticación.
        // Por ahora, simplemente imprime los valores ingresados.
        System.out.println("Correo Electrónico: " + email);
        System.out.println("Contraseña: " + password);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

