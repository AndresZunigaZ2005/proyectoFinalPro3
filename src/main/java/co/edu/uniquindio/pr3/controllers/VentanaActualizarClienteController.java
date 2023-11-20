package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.ClienteVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaActualizarClienteController implements Initializable {

    @FXML
    private Hyperlink btnActualizarFoto;

    @FXML
    private Button btnActualizarPerfil;

    @FXML
    private TextField txtFieldCorreo;

    @FXML
    private TextField txtFieldDireccion;

    @FXML
    private TextField txtFieldIdentificacion;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldTelefono;

    @FXML
    private ImageView imageViewPerfil;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();
    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private File selectedImageFile;

    @FXML
    void actualizarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        // Obtener la ventana actual para configurar el FileChooser
        Stage stage = (Stage) btnActualizarFoto.getScene().getWindow();

        // Mostrar el cuadro de diálogo y obtener el archivo seleccionado
        selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            // Mostrar la imagen en el ImageView
            try (FileInputStream input = new FileInputStream(selectedImageFile)) {
                Image image = new Image(input);
                imageViewPerfil.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void actualizarPerfil(ActionEvent event) {
        String nuevoNombre = txtFieldNombre.getText();
        String identificacion = txtFieldIdentificacion.getText();
        String nuevoCorreo = txtFieldCorreo.getText();
        String nuevoTelefono = txtFieldTelefono.getText();
        String nuevaDireccion = txtFieldDireccion.getText();
        try {
            agenciaViajes.actualizarCliente(nuevoNombre, identificacion, nuevoCorreo, nuevoTelefono, nuevaDireccion, prop.getProperty("RUTA_IMAGENES_CLIENTE")+identificacion);
            saveImage();
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("messageSucessfulUpdateDataClient"));
        } catch (ClienteVacioException e) {
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("error"), prop.getProperty("error"), e.getMessage());
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Guardar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
    }

    private void saveImage() {
        if (selectedImageFile != null) {
            File destinationFile = new File(prop.getProperty("RUTA_IMAGENES_CLIENTE")+txtFieldIdentificacion.getText()+".jpg");

            try {
                // Copiar la imagen seleccionada a la nueva ubicación
                Files.copy(selectedImageFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Aquí puedes realizar operaciones adicionales, como cambiar el nombre de la imagen, etc.

                System.out.println("Imagen guardada en: " + destinationFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        txtFieldIdentificacion.setText(singletonController.getCliente().getIdentificacion());
        txtFieldIdentificacion.setEditable(false);

        txtFieldCorreo.setText(singletonController.getCliente().getCorreo());
        txtFieldDireccion.setText(singletonController.getCliente().getDireccion());
        txtFieldTelefono.setText(singletonController.getCliente().getTelefono());
        txtFieldNombre.setText(singletonController.getCliente().getNombre());
        Image image = new Image("file:" + singletonController.getCliente().getImagen());
        imageViewPerfil.setImage(image);
    }
}
