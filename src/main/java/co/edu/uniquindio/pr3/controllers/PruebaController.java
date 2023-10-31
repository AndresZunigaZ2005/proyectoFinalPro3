package co.edu.uniquindio.pr3.controllers;


import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PruebaController {
    @FXML
    private ImageView imageView;

    private boolean isMouseOver = false;

    @FXML
    void handleMouseEntered() {
        if (!isMouseOver) {
            isMouseOver = true;
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), imageView);
            transition.setByY(-25); // Cambia la posición vertical para centrar la imagen
            transition.setOnFinished(event -> imageView.setVisible(true));
            transition.play();
        }
    }

    @FXML
    void handleMouseExited() {
        if (isMouseOver) {
            isMouseOver = false;
            imageView.setVisible(false);
            imageView.setTranslateY(0);
        }
    }
}


