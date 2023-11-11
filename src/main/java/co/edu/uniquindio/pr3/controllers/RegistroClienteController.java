package co.edu.uniquindio.pr3.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegistroClienteController {

    @FXML
    private Hyperlink btnIniciarSesion;

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private PasswordField contraseñaField;

    @FXML
    private Label contraseñaLabel;

    @FXML
    private TextField correoField;

    @FXML
    private Label correoLabel;

    @FXML
    private TextField direccionField;

    @FXML
    private Label direccionLabel;

    @FXML
    private GridPane grid;

    @FXML
    private TextField identificacionField;

    @FXML
    private Label identificacionLabel;

    @FXML
    private Button imagenButton;

    @FXML
    private Label imagenLabel;

    @FXML
    private TextField nombreField;

    @FXML
    private Label nombreLabel;

    @FXML
    private TextField telefonoField;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label title;

    private SingletonController singletonController = SingletonController.getInstance();

    @FXML
    void cambiarVentanaIniciarSesion(ActionEvent event) {

    }

    @FXML
    void registrarCliente(ActionEvent event) {

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {

    }

}
