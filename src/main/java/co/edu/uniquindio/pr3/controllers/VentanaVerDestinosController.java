package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Destino;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaVerDestinosController implements Initializable {

    @FXML
    private Button btnNextImage;

    @FXML
    private Button btnPreviousImage;

    @FXML
    private ComboBox<String> comboBoxDestino;

    @FXML
    private ImageView imageViewFotosDestinos;

    @FXML
    private TextField txtFieldCiudad;

    @FXML
    private TextField txtFieldClima;

    @FXML
    private TextField txtFieldNombre;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private SingletonController singletonController = SingletonController.getInstance();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private Destino destinoMostrar;

    private int currentIndex = 0;

    private ArrayList<String> nombresDestinos = new ArrayList<>();

    @FXML
    void showNextImage(ActionEvent event) {
        if (currentIndex < destinoMostrar.getImagenes().size() - 1) {
            currentIndex++;
        } else {
            currentIndex = 0; // Vuelve al inicio si alcanza el final de la lista
        }
        showImage();
    }

    @FXML
    void showPreviousImage(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = destinoMostrar.getImagenes().size() - 1; // Va al final si está en el inicio
        }
        showImage();
    }

    @FXML
    void showSelectedDestino(ActionEvent event) {
        destinoMostrar = agenciaViajes.obtenerDestino(comboBoxDestino.getValue(),0);
        showImage();
    }

    private void showImage() {
        if (!destinoMostrar.getImagenes().isEmpty()) {
            String imagePath = destinoMostrar.getImagenes().get(currentIndex);
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageViewFotosDestinos.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontAwesomeIconView arrowIconRight = new FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT);
        arrowIconRight.setSize("2em");

        btnNextImage.setGraphic(arrowIconRight);

        FontAwesomeIconView arrowIconLeft = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        arrowIconRight.setSize("2em");

        btnPreviousImage.setGraphic(arrowIconLeft);

        for (Destino destino : agenciaViajes.getListaDestinos()) {
            nombresDestinos.add(destino.getNombre());
        }
        comboBoxDestino.getItems().addAll(nombresDestinos);
    }
}
