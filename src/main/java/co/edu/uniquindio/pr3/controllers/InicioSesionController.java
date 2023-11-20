package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.Administrador;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;
import co.edu.uniquindio.pr3.model.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
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

    private VentanaPrincipalController ventanaPrincipalController = VentanaPrincipalController.getInstance();
    @FXML

    void IniciarSesion(ActionEvent event) {
        Cliente cliente = agenciaViajes.obtenerClienteCorreo(usernameField.getText(), 0);
        Administrador administrador = agenciaViajes.obtenerAdministrador(usernameField.getText(), 0);
        if(cliente == null && administrador == null) {
            showAlert(Alert.AlertType.WARNING, prop.getProperty("warning"), prop.getProperty("warning"), prop.getProperty("bodywrongDatalogIn"));
        }else if(administrador == null){
            singletonController.setCliente(cliente);
            /*ventanaPrincipalController.getBtnRegistroCliente().setVisible(false);
            ventanaPrincipalController.getBtnIniciarSesion().setVisible(false);
            ventanaPrincipalController.getBtnActualizarPerfil().setVisible(true);*/
            showAlert(Alert.AlertType.WARNING, prop.getProperty("information"), prop.getProperty("information"), "Bienvenido "+cliente.getNombre());
        }else if(cliente == null){
            singletonController.setAdministrador(administrador);
            /*ventanaPrincipalController.getBtnCrearGuia().setVisible(true);
            ventanaPrincipalController.getBtnCrearDestinos().setVisible(true);
            ventanaPrincipalController.getBtnVerListaClientes().setVisible(true);
            ventanaPrincipalController.getBtnCrearPaquetes().setVisible(true);*/
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), "Bienvenido "+administrador.getNombre());
        }
    }

    @FXML
    void enviarCorreoRecuperacionContrasenia(ActionEvent event) {
        String correo = usernameField.getText();
        if(correo.isBlank() || correo == null){
            showAlert(Alert.AlertType.INFORMATION,prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("BodyrecoverTheEmailAlert"));
        } else if (agenciaViajes.obtenerClienteCorreo(correo, 0) == null){
            showAlert(Alert.AlertType.INFORMATION,prop.getProperty("information"), prop.getProperty("information"), "Ingrese un correo valido");
        }
        else{
            singletonController.setCorreoCambiarContrasena(correo);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Mail.mail(prop.getProperty("issueEmailRecoveryPassword"),
                                prop.getProperty("bodyEmailRecoverPassword")+singletonController.generarContenidoHTML(singletonController.generarCodigoAleatorio()),
                                correo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            abrirVentanaRecuperacionContrasena();
        }
    }

    private void abrirVentanaRecuperacionContrasena(){
        try {
            // Cargar la nueva ventana desde un archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/CambiarContrasena.fxml"));
            Parent root = loader.load();

            // Configurar la nueva ventana
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nueva Ventana");
            stage.setScene(new Scene(root));

            // Mostrar la nueva ventana
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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
