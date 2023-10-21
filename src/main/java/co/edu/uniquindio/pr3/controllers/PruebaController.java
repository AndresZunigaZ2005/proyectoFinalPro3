package co.edu.uniquindio.pr3.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PruebaController {

    @FXML
    private Button btnPrueba;

    @FXML
    void accionarPrueba(ActionEvent event) {

    }

    public void setStage(Stage primaryStage) {
    }

    @Override
    public void cambiarIdioma(ResourceBundle bundle) {
        btnPrueba.setText(bundle.getString("btn"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Propiedades.getInstance().addListener(this);
    }
}
