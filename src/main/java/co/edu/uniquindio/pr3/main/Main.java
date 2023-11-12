package co.edu.uniquindio.pr3.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage primaryStage)  {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ventanas/VentanaPrincipal.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Agencia de Viajes UQ");
            primaryStage.show();
            primaryStage.getIcons().add(new Image("/Imagenes/ImagenIconoAvion.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        launch(Main.class, args);
    }
}
