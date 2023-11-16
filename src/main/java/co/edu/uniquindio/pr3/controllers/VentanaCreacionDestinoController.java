package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.DestinoExisteException;
import co.edu.uniquindio.pr3.exceptions.DestinoVacioException;
import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Clima;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    private ImageView imgVerImagenes;

    @FXML
    private Button btnNextImage;

    @FXML
    private Button btnPrevImage;

    @FXML
    private Button btnEliminarImagen;


    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private ArrayList<String> rutasDeImagenes;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";
    private ArrayList<Image> listaImagenes= new ArrayList<>();

    private Properties prop;
    private FileInputStream input;
    private Image selectedImage;
    private int indice =0;

    @FXML
    void AnadirDestino(ActionEvent event) {
        Clima clima = comboBoxClimaDestino.getValue();
        if(comboBoxClimaDestino.getValue() != null) {
            String nombre = nombreDestinoField.getText();
            String ciudad = ciudadDestinoField.getText();
            try {
                agenciaViajes.crearDestino(nombre, ciudad, saveImagesAndGetPaths(nombre), comboBoxClimaDestino.getValue());
                showAlert(Alert.AlertType.INFORMATION, "information", "information", prop.getProperty("DestinationCreatedSuccessfully"));
            } catch (DestinoVacioException | DestinoExisteException e) {
                showAlert(Alert.AlertType.INFORMATION, "warning", "warning", e.getMessage());
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR, prop.getProperty("warning"), prop.getProperty("warning"), prop.getProperty("NotSelectedWeather"));
        }
    }

    private ArrayList<String> saveImagesAndGetPaths(String nombre) {
        ArrayList<String> imagePaths = new ArrayList<>();

        // Crear la carpeta si no existe una
        File folder = new File(prop.getProperty("RUTA_IMAGENES_DESTINO"));
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int i = 0; i < listaImagenes.size(); i++) {
            Image image = listaImagenes.get(i);
            String imagePath = prop.getProperty("RUTA_IMAGENES_DESTINO") + File.separator + nombre + i + ".png";

            try {
                File outputFile = new File(imagePath);
                ImageIO.write(convertToBufferedImage(image), "png", outputFile);
                imagePaths.add(outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imagePaths;
    }

    private BufferedImage convertToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        PixelReader pixelReader = image.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                int rgba = (int) (color.getRed() * 255) << 16 |
                        (int) (color.getGreen() * 255) << 8 |
                        (int) (color.getBlue() * 255) |
                        (int) (color.getOpacity() * 255) << 24;
                bufferedImage.setRGB(x, y, rgba);
            }
        }

        return bufferedImage;
    }

    @FXML
    void buscarImagenes(ActionEvent event) {
        Stage stage = (Stage) btnBuscarImagenesDestino.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            listaImagenes.add(selectedImage);
        }
    }


    @FXML
    private void mostrarImagenActual() {
        if (!listaImagenes.isEmpty() && indice >= 0 && indice < listaImagenes.size()) {
            Image imagenAcual = listaImagenes.get(indice);
            imgVerImagenes.setImage(imagenAcual);
        } else {
            imgVerImagenes.setImage(null); //No hau imagenes en la lista
        }
    }


    @FXML
    private void nextImage(ActionEvent event) {
        if (!listaImagenes.isEmpty()) {
            indice = (indice + 1) % listaImagenes.size();
            mostrarImagenActual();
        }
    }

    @FXML
    private void previousImage(ActionEvent event) {
        if (!listaImagenes.isEmpty()) {
            indice = (indice - 1 + listaImagenes.size()) % listaImagenes.size();
            mostrarImagenActual();
        }
    }

    @FXML
    private void deleteCurrentImage(ActionEvent event) {
        if (!listaImagenes.isEmpty() && indice >= 0 && indice < listaImagenes.size()) {
            listaImagenes.remove(indice);
            if (indice > 0) {
                indice--;
            }
            mostrarImagenActual();
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