package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class VentanaMostrarReservasController implements Initializable {

    @FXML
    private TableView<Reserva> tableViewReserva;

    @FXML
    private TableColumn<Reserva, LocalDate> fechaSolicitudColumn;

    @FXML
    private TableColumn<Reserva, LocalDateTime> fechaViajeColumn;

    @FXML
    private TableColumn<Reserva, Integer> cantPersonasColumn;

    @FXML
    private TableColumn<Reserva, PaqueteTuristico> paqueteTuristicoColumn;

    @FXML
    private TableColumn<GuiaTuristico, String> guiaTuristicoColumn;

    @FXML
    private TableColumn<Reserva, EstadoReserva> estadoReservaColumn;

    @FXML
    private Button btnCancelarReserva;

    @FXML
    private Slider sliderCalificarPaquete;

    @FXML
    private Slider sliderCalificarGuia;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private SingletonController singletonController = SingletonController.getInstance();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private ArrayList<Reserva> listaReservasCliente = new ArrayList<>();

    private ObservableList<Reserva> listaReserva;

    @FXML
    void cancelarReserva(ActionEvent event){
        Reserva reservaSeleccionada = tableViewReserva.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            reservaSeleccionada.cambiarEstadoReserva(EstadoReserva.CANCELADA);
            PaqueteTuristico paqueteTuristico = reservaSeleccionada.getPaqueteTuristico();
            paqueteTuristico.setCupoMaximo(reservaSeleccionada.getCantPersonas()+paqueteTuristico.getCupoMaximo());
            tableViewReserva.refresh();
            agenciaViajes.escribirReservas();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Estado de reserva cambiado a Cancelada", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seleccione una reserva para cambiar su estado", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void calificacionGuia(MouseEvent event) {
        AtomicInteger rating = new AtomicInteger((int) sliderCalificarGuia.getValue());
        sliderCalificarGuia.valueProperty().addListener((observable, oldValue, newValue) -> {
            rating.set(newValue.intValue());
        });
        System.out.println(rating);
        Reserva reservaSeleccionada = tableViewReserva.getSelectionModel().getSelectedItem();
        if(reservaSeleccionada!=null && reservaSeleccionada.getGuiaTuristico()!=null && reservaSeleccionada.getEstadoReserva().equals(EstadoReserva.PASADA)) {
            agenciaViajes.calificarGuia(reservaSeleccionada.getGuiaTuristico(), rating.get());
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), "Gracias por calificar");
        }else{
            showAlert(Alert.AlertType.WARNING, prop.getProperty("warning"), prop.getProperty("warning"), "Seleccione una reserva");
        }
    }

    @FXML
    void calificacionPaquete(MouseEvent event) {
        int rating = (int)sliderCalificarPaquete.getValue();
        System.out.println(rating);
        Reserva reservaSeleccionada = tableViewReserva.getSelectionModel().getSelectedItem();
        if(reservaSeleccionada!=null && reservaSeleccionada.getEstadoReserva().equals(EstadoReserva.PASADA)) {
            agenciaViajes.calificarPaquete(reservaSeleccionada.getPaqueteTuristico(), rating);
            System.out.println(reservaSeleccionada.getPaqueteTuristico().mostrarCalificacionPaquete());
            showAlert(Alert.AlertType.INFORMATION, prop.getProperty("information"), prop.getProperty("information"), "Gracias por calificar");
        }else{
            showAlert(Alert.AlertType.WARNING, prop.getProperty("warning"), prop.getProperty("warning"), "Seleccione una reserva");
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
        String imagePath = "/Imagenes/estrella.png"; // Ajusta la ruta según tu estructura de directorios

        fechaSolicitudColumn.setCellValueFactory(new PropertyValueFactory<>("fechaSolicitud"));
        fechaViajeColumn.setCellValueFactory(new PropertyValueFactory<>("fechaViaje"));
        cantPersonasColumn.setCellValueFactory(new PropertyValueFactory<>("cantPersonas"));
        paqueteTuristicoColumn.setCellValueFactory(new PropertyValueFactory<>("paqueteTuristico"));
        guiaTuristicoColumn.setCellValueFactory(new PropertyValueFactory<>("guiaTuristico"));
        estadoReservaColumn.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));

        ArrayList<Reserva> reservasEncontradas = agenciaViajes.buscarReservaCliente(singletonController.getCliente(), listaReservasCliente, 0);
        listaReserva = FXCollections.observableArrayList(reservasEncontradas);

        tableViewReserva.getItems().addAll(listaReserva);
    }

}
