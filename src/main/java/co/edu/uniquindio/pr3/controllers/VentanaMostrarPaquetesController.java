package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.exceptions.ReservaExisteException;
import co.edu.uniquindio.pr3.exceptions.ReservaVaciaException;
import co.edu.uniquindio.pr3.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VentanaMostrarPaquetesController implements Initializable {

    @FXML
    private Button btnNextImage;

    @FXML
    private Button btnPrevImage;

    @FXML
    private Button btnReserva;

    @FXML
    private ComboBox<PaqueteTuristico> comboBoxPaquete;

    @FXML
    private ComboBox<GuiaTuristico> comboBoxSelectGuia;

    @FXML
    private ImageView imageViewDestinos;

    @FXML
    private ListView<Destino> listViewDestinos;

    @FXML
    private TextArea txtAreaServicios;

    @FXML
    private TextField txtFieldCuposDisp;

    @FXML
    private TextField txtFieldDuracion;

    @FXML
    private TextField txtFieldFecha;

    @FXML
    private TextField txtFieldNombrePaquete;

    @FXML
    private TextField txtFieldPrecio;

    @FXML
    private ComboBox<String> comboBoxFIltro;

    @FXML
    private ComboBox<Destino> comboBoxFiltroDestino;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private ObservableList<PaqueteTuristico> listaPaquetes;

    private ObservableList<Destino> destinos;

    private ObservableList<Destino> listaDestinosOriginal;

    private ObservableList<GuiaTuristico> listaGuiasTuristicos;

    private List<Image> listaImagenes= new ArrayList<>();

    private List<File> imagenes;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    private int indice = 0;
    @FXML
    void nextImageDestino(ActionEvent event) {
        if (!listaImagenes.isEmpty()) {
            indice = (indice + 1) % listaImagenes.size();
            mostrarImagenActual();
        }
    }

    @FXML
    void prevImageDestino(ActionEvent event) {
        if (!listaImagenes.isEmpty()) {
            indice = (indice - 1 + listaImagenes.size()) % listaImagenes.size();
            mostrarImagenActual();
        }
    }

    @FXML
    void mostrarPaquete(ActionEvent event) {
        PaqueteTuristico p = comboBoxPaquete.getValue();
        txtFieldCuposDisp.setPromptText(p.getCupoMaximo()+"");

        destinos = FXCollections.observableArrayList(p.getListaDestinos());
        txtFieldNombrePaquete.setText(p.getNombre());
        txtFieldDuracion.setText(p.getDuracion()+"");
        txtAreaServicios.setText(p.getServicios());
        txtFieldPrecio.setText(p.getPrecio()+"");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        txtFieldFecha.setText(p.getFecha().format(formatter));



        listViewDestinos.setItems(destinos);
        listViewDestinos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Destino destino = listViewDestinos.getSelectionModel().getSelectedItem();
                obtenerImagenes(destino);
            }
        });
    }


    private void obtenerImagenes(Destino destino){
         File [] files = new File("src/main/resources/persistencia/FotosDestinos").listFiles();

         for (File file : files){
             System.out.println(file);
             System.out.println(file.getPath());
             System.out.println(file.getName());
             System.out.println(file.toURI().toString());
         }

        imagenes= Arrays.asList(files);
        for (File archivo: imagenes) {
            System.out.println(archivo);
            if(archivo.getName().contains(destino.getNombre())){
                Image imagen= new Image(archivo.toURI().toString());
                listaImagenes.add(imagen);
            }
        }
    }

    @FXML
    private void mostrarImagenActual() {

        if (!listaImagenes.isEmpty() && indice >= 0 && indice < listaImagenes.size()) {
            Image imagenAcual = listaImagenes.get(indice);
            imageViewDestinos.setImage(imagenAcual);
        } else {
            imageViewDestinos.setImage(null); //No hau imagenes en la lista
        }
    }



    @FXML
    void filtrarPredeterminados(ActionEvent event){

    }

    @FXML
    void filtrarDestinos(ActionEvent event){

    }

    private ObservableList<Destino> ponerDestinoPaquete(PaqueteTuristico paqueteTuristico, ObservableList<Destino> destinos, int i){
        if(i == listaPaquetes.size()){
            return destinos;
        }
        destinos.add(paqueteTuristico.getListaDestinos().get(i));
        return ponerDestinoPaquete(paqueteTuristico, destinos, i+1);
    }

    @FXML
    void crearReserva(ActionEvent event) {
        if (singletonController.getCliente()!=null) {
            PaqueteTuristico p = comboBoxPaquete.getValue();
            GuiaTuristico guia = comboBoxSelectGuia.getValue();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String cuerpoCorreo = prop.getProperty("EmailMessageReservation");

                cuerpoCorreo = cuerpoCorreo.replace("yyyy-MM-dd", LocalDateTime.now().format(formatter));
                cuerpoCorreo = cuerpoCorreo.replace("@¡?¨*[_:;]!yyyy-MM-dd HH:mm:ss", p.getFecha().format(formatter));
                System.out.println(p.getFecha().format(formatter) + "fecha sin formatear" + p.getFecha());
                cuerpoCorreo = cuerpoCorreo.replace("HH:mm:ss", "");
                cuerpoCorreo = cuerpoCorreo.replace("[Número de Personas: X]", txtFieldCuposDisp.getText());
                cuerpoCorreo = cuerpoCorreo.replace("Cliente.getNombre()", singletonController.getCliente().getNombre());
                cuerpoCorreo = cuerpoCorreo.replace("PaqueteTuristico.getNombre()", p.getNombre());

                if (guia != null) {
                    cuerpoCorreo= cuerpoCorreo.replace("GuiaTuristico.getNombre()", guia.getNombre());
                    agenciaViajes.crearReserva(p.getFecha(), Integer.parseInt(txtFieldCuposDisp.getText()), singletonController.getCliente().getIdentificacion(), p.getNombre(), guia.getIdentificacion());

                } else {
                    cuerpoCorreo= cuerpoCorreo.replace("Guía Turístico: [Nombre del Guía Turístico: GuiaTuristico.getNombre()]", "");
                    agenciaViajes.crearReserva(p.getFecha(), Integer.parseInt(txtFieldCuposDisp.getText()), singletonController.getCliente().getIdentificacion(), p.getNombre(), null);
                }
                showAlert(Alert.AlertType.ERROR, prop.getProperty("information"), prop.getProperty("information"), prop.getProperty("ReservationCreatedSuccessfully"));
                String finalCuerpoCorreo = cuerpoCorreo;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Mail.mail(prop.getProperty("ThanksForTheConfidenceMessage"), finalCuerpoCorreo, singletonController.getCliente().getCorreo());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
                p.setCupoMaximo(p.getCupoMaximo() - (Integer.parseInt(txtFieldCuposDisp.getText())));
                agenciaViajes.escribirPaqueteTuristico();
            } catch (ReservaExisteException | ReservaVaciaException e) {
                showAlert(Alert.AlertType.ERROR, prop.getProperty("Error"), prop.getProperty("Error"), e.getMessage());
            }
        }
        else{
            showAlert(Alert.AlertType.ERROR, prop.getProperty("information"), prop.getProperty("information"), "Inicie Sesion Primero");
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
        Slider slider = new Slider(0, 5, 2.5);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);

        slider.setMinWidth(300);
        slider.setMaxWidth(300);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double roundedValue = Math.round(newValue.doubleValue() * 2) / 2.0;
            slider.setValue(roundedValue);
        });


        listaGuiasTuristicos = FXCollections.observableArrayList(agenciaViajes.getListaGuiasTuristicos());
        listaDestinosOriginal = FXCollections.observableArrayList(agenciaViajes.getListaDestinos());
        destinos = FXCollections.observableArrayList();
        listaPaquetes = FXCollections.observableArrayList(agenciaViajes.getListaPaquetesTuristicos());
        imagenes= new ArrayList<>();

        comboBoxPaquete.setItems(listaPaquetes);
        comboBoxFiltroDestino.setItems(listaDestinosOriginal);
        comboBoxSelectGuia.setItems(listaGuiasTuristicos);
    }

}
