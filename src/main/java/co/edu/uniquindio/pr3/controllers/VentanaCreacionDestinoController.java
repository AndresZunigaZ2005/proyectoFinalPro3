package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Clima;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaCreacionDestinoController implements Initializable {

    @FXML
    private Button btnAnadirDestino;

    @FXML
    private Button btnBuscarImagenesDestino;

    @FXML
    private TextField ciudadDestinoField;

    @FXML
    private ComboBox<Clima> comboBoxClimaDestino;

    @FXML
    private TextField nombreDestinoField;

    @FXML
    private Button btnVerImagenes;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private ArrayList<String> rutasDeImagenes;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @FXML
    void AnadirDestino(ActionEvent event) {
        Clima clima = comboBoxClimaDestino.getValue();
        if(comboBoxClimaDestino.getValue() != null) {
            String nombre = nombreDestinoField.getText();
            String ciudad = ciudadDestinoField.getText();
            //agenciaViajes.crerDestino(nombre, ciudad);
        }
    }

    @FXML
    void buscarImagenes(ActionEvent event) {

    }

    @FXML
    void VerImagenesAnadidas(ActionEvent event){
        if(rutasDeImagenes.size() == 0){
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("showingImagesDestinationError"));
        }else {
            Stage stage = new Stage();
            stage.setTitle("Imágenes");

            GridPane gridPane = new GridPane();
            int columnCount = 0;

            for (String ruta : rutasDeImagenes) {
                Image image = new Image("file:" + ruta);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);

                gridPane.add(imageView, columnCount++, 0);
            }

            Scene scene = new Scene(gridPane, 400, 150);
            stage.setScene(scene);
            stage.show();
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rutasDeImagenes = new ArrayList<>();
        Clima [] clima = Clima.values();
        comboBoxClimaDestino.getItems().addAll(clima);

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