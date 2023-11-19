package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.ClienteExisteException;
import co.edu.uniquindio.pr3.exceptions.ClienteVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.ResourceBundle;

public class RegistroClienteController implements Initializable {

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private PasswordField contrasenaField;

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

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private SingletonController singletonController = SingletonController.getInstance();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;
    private Image selectedImage = null;

    @FXML
    void registrarCliente(ActionEvent event) {
        String nombre = nombreField.getText();
        String idetificacion = identificacionField.getText();
        String telefono = telefonoField.getText();
        String direccion = direccionField.getText();
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();

        try{
            String mensajeRegistroExitoso = prop.getProperty("bodyEmailSuccesfulRegistration");
            mensajeRegistroExitoso = mensajeRegistroExitoso.replace("{nombreCliente}", nombre);
            mensajeRegistroExitoso = mensajeRegistroExitoso.replace("{correoElectronico}", correo);
            mensajeRegistroExitoso = mensajeRegistroExitoso.replace("{numeroTelefono}", telefono);
            mensajeRegistroExitoso = mensajeRegistroExitoso.replace("{Direccion}", direccion);

            agenciaViajes.anadirCliente(nombre, idetificacion, correo, telefono, direccion, contrasena, prop.getProperty("RUTA_IMAGENES_CLIENTE"));
            String finalMensajeRegistroExitoso = mensajeRegistroExitoso;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Mail.mail(prop.getProperty("issueEmailSuccesfulRegistration") , finalMensajeRegistroExitoso,correo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            if (selectedImage != null) {
                // Guardar la imagen en la carpeta deseada
                File outputFile = new File(prop.getProperty("RUTA_IMAGENES_CLIENTE")+identificacionField.getText()+".png");

                try (FileInputStream input = new FileInputStream(new File(selectedImage.getUrl().replace("file:/", "")));
                     FileOutputStream output = new FileOutputStream(outputFile);
                     FileChannel inChannel = input.getChannel();
                     FileChannel outChannel = output.getChannel()) {

                    inChannel.transferTo(0, inChannel.size(), outChannel);
                    System.out.println("Imagen registrada en: " + outputFile.getAbsolutePath());

                } catch (IOException e) {
                    e.printStackTrace();
                    // Manejar errores al guardar la imagen
                }
            }
            else{
                showAlert(Alert.AlertType.WARNING, prop.getProperty("warning"), prop.getProperty("warning"), "Escoja una foto de perfil");
            }
            nombreField.clear();
            identificacionField.clear();
            telefonoField.clear();
            direccionField.clear();
            correoField.clear();
            contrasenaField.clear();


        } catch (ClienteExisteException | ClienteVacioException e) {
            showAlert(Alert.AlertType.ERROR, prop.getProperty("error"), prop.getProperty("error"), e.getMessage());
        }
    }


    @FXML
    private void seleccionarImagen() {
        Stage stage = (Stage) imagenButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
        }
    }


    private Image selectedImageProperty() {
        return selectedImage;
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
