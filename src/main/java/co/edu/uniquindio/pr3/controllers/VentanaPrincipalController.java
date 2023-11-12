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
    private Button btn1;

    @FXML
    private Button button;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private VBox menu;

    @FXML
    private BorderPane ventanaPrincipal;

    @FXML
    private BorderPane panelDinamico;

    private boolean estaDesplegado= false;


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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
