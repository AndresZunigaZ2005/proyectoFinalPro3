package co.edu.uniquindio.pr3.controllers;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/InicioSesion.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    void cambiarVentanaCreacionDestinos(ActionEvent event){
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ventanaCreacionDestinos.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cambiarVentanaCreacionGuia(ActionEvent event){
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearGuia.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cambiarVentanaActualizarPerfil(ActionEvent event){
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaActualizarCliente.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaVerListaClientes.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cambiarVentanaCrearPaquetes(ActionEvent event){
        try {
            System.out.println("se presiono el boton");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCreacionPaquetes.fxml"));
            Parent nuevaVentana= loader.load();
            panelDinamico.setCenter(nuevaVentana);
        } catch (IOException e) {
            throw new RuntimeException(e);
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
