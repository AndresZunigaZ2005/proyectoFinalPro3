package co.edu.uniquindio.pr3.model;

import co.edu.uniquindio.pr3.controllers.SingletonController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaGraficasController implements Initializable {

    @FXML
    private BarChart<String, Number> chBarras;

    @FXML
    private PieChart chTorta;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();
    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<GuiaTuristico> datoPieChart= agenciaViajes.getListaGuiasTuristicos();
        ArrayList<PaqueteTuristico> datoBarChart= agenciaViajes.getListaPaquetesTuristicos();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        chTorta= new PieChart();
        chBarras= new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series= new XYChart.Series<>();



        for (GuiaTuristico dato : datoPieChart) {
            chTorta.getData().setAll(new PieChart.Data(dato.getNombre(), dato.mostrarCalificacion()));
        }

        for (PaqueteTuristico p: datoBarChart) {
            series.getData().setAll(new XYChart.Data<>(p.getNombre(), p.mostrarCalificacionPaquete()));
        }
        chBarras.getData().add(series);

        }
    }

