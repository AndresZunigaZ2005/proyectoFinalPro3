package co.edu.uniquindio.pr3.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main {

    public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Prueba.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Mi agencia");
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
