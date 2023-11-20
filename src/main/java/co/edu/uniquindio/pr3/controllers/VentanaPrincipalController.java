package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipalController implements Initializable {

    @FXML
    private Button btnIniciarSesion = new Button();

    @FXML
    private Button button;

    @FXML
    private Button btnRegistrarClientes = new Button();

    @FXML
    private Button btnCrearDestinos = new Button();

    @FXML
    private Button btnCrearGuia = new Button();

    @FXML
    private Button btnActualizarPerfil = new Button();

    @FXML
    private Button btnVerDestinos = new Button();

    @FXML
    private Button btnVerListaClientes = new Button();

    @FXML
    private Button btnCrearPaquetes = new Button();

    @FXML
    private Button btnMostrarPaquetes = new Button();

    @FXML
    private Button btnMostrarReservasCliente = new Button();

    @FXML
    private Button btnMostrarEstadisticas = new Button();

    @FXML
    private Button btnActualizarGuia= new Button();

    @FXML
    private Button btnActualizarDestinos = new Button();

    @FXML
    private Button btnGraficas= new Button();

    @FXML
    private VBox menu;

    @FXML
    private BorderPane ventanaPrincipal;

    @FXML
    private BorderPane panelDinamico;

    private boolean estaDesplegado= false;

    private SingletonController singletonController = SingletonController.getInstance();

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private static VentanaPrincipalController instance;


    public VentanaPrincipalController() {

    }

    public static VentanaPrincipalController getInstance() {
        if (instance == null) {
            instance = new VentanaPrincipalController();
        }
        return instance;
    }

    @FXML
    void desplegableEvent(ActionEvent event) {
        menu.setPrefWidth(0);
        System.out.println("Se apreto el boton");
        final double startHeight = estaDesplegado ? 100 : 0;
        final double endHeight = estaDesplegado ? 0 : 100;
        Transition transition= new Transition () {
            {
                setCycleDuration(Duration.millis(250));
            }

            @Override
            protected void interpolate(double frac) {
                double tam = startHeight + (endHeight - startHeight) * frac;
                menu.setPrefHeight(tam);
            }
        };
        transition.play();
       estaDesplegado= !estaDesplegado;
    }


    @FXML
    void cambiarVentanaLogIn(ActionEvent event) {
        if(singletonController.getCliente() == null && singletonController.getAdministrador() == null) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/InicioSesion.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "No necesita iniciar seción");
        }
    }


    @FXML
    void cambiarVentanaSingUp(ActionEvent event) {

            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaRegistroCliente.fxml"));
                Parent nuevaVentana= loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void cambiarVentanaCreacionDestinos(ActionEvent event) {
        if (obtenerInstanciaCliente()){
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ventanaCreacionDestinos.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaCreacionGuia(ActionEvent event){
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearGuia.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaActualizarPerfil(ActionEvent event){
        if(!obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaActualizarCliente.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para clientes");
        }
    }

    @FXML
    void cambiarVentanaVerDestinos(ActionEvent event) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaVerDestinos.fxml"));
                Parent nuevaVentana= loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void cambiarVentanaVerListaClientes(ActionEvent event){
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaVerListaClientes.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaCrearPaquetes(ActionEvent event){
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCreacionPaquetes.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaMostrarPaquetes(ActionEvent event) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaMostrarPaquetes.fxml"));
                Parent nuevaVentana= loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    void cambiarVentanaMostrarReservasCliente(ActionEvent event) {
        if(!obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaMostrarReservas.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para clientes");
        }
    }

    @FXML
    void cambiarVentanaMostrarEstadisticas(ActionEvent event) {
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaEstadisticas.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaActualizarGuia(ActionEvent event) {
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaActualizarGuia.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaActualizarDestinos(ActionEvent event){
        if(obtenerInstanciaCliente()) {
            try {
                System.out.println("se presiono el boton");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaActualizarDestinos.fxml"));
                Parent nuevaVentana = loader.load();
                panelDinamico.setCenter(nuevaVentana);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
        }
    }

    @FXML
    void cambiarVentanaGraficas(){
         if(obtenerInstanciaCliente()) {
             try {
                 System.out.println("se presiono el boton");
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGraficas.fxml"));
                 Parent nuevaVentana = loader.load();
                 panelDinamico.setCenter(nuevaVentana);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
         else {
             showAlert(Alert.AlertType.ERROR, "Error", "Error", "Exclusivo para admins");
         }
    }

    public boolean obtenerInstanciaCliente(){
        if(singletonController.getCliente() == null){
            return true;
        }
        else{
            return false;
        }
    }

    public Button getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public Button getBtnRegistroCliente() {
        return btnRegistrarClientes;
    }

    public Button getBtnCrearDestinos() {
        return btnCrearDestinos;
    }

    public Button getBtnCrearGuia() {
        return btnCrearGuia;
    }

    public Button getBtnActualizarPerfil() {
        return btnActualizarPerfil;
    }

    public Button getBtnVerDestinos() {
        return btnVerDestinos;
    }

    public Button getBtnVerListaClientes() {
        return btnVerListaClientes;
    }

    public Button getBtnCrearPaquetes() {
        return btnCrearPaquetes;
    }

    public Button getBtnMostrarReservasCliente() {
        return btnMostrarReservasCliente;
    }

    public Button getBtncambiarVentanaGraficas(){return btnGraficas;};

    public void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("si se inicializo");

        FontAwesomeIconView signInButton = new FontAwesomeIconView(FontAwesomeIcon.SIGN_IN);
        signInButton.setSize("2em");

        btnIniciarSesion.setGraphic(signInButton);

        FontAwesomeIconView signUpButton = new FontAwesomeIconView(FontAwesomeIcon.REGISTERED);
        signUpButton.setSize("2em");

        btnRegistrarClientes.setGraphic(signUpButton);

        FontAwesomeIconView createDestinationButton = new FontAwesomeIconView(FontAwesomeIcon.PLANE);
        createDestinationButton.setSize("2em");

        btnCrearDestinos.setGraphic(createDestinationButton);

        FontAwesomeIconView createGuideButton = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        createGuideButton.setStyle("-fx-fill: orange;");
        createGuideButton.setSize("2em");

        btnCrearGuia.setGraphic(createGuideButton);
    }
}
