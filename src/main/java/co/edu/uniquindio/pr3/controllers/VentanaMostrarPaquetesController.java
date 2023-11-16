package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Destino;
import co.edu.uniquindio.pr3.model.GuiaTuristico;
import co.edu.uniquindio.pr3.model.PaqueteTuristico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    private ObservableList<String> listaImagenesSeleccionadaListView;

    private ArrayList<Image> listaImagenesSeleccionadaListViewImages;

    private int indice = 0;
    @FXML
    void nextImageDestino(ActionEvent event) {
        if (!listaImagenesSeleccionadaListView.isEmpty()) {
            indice = (indice + 1) % listaImagenesSeleccionadaListView.size();
            mostrarImagenActual();
        }
    }

    @FXML
    void prevImageDestino(ActionEvent event) {
        if (!listaImagenesSeleccionadaListView.isEmpty()) {
            indice = (indice - 1 + listaImagenesSeleccionadaListView.size()) % listaImagenesSeleccionadaListView.size();
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
                // Obtener la instancia de MiClase seleccionada
                Destino destino = listViewDestinos.getSelectionModel().getSelectedItem();

                ObservableList<String> listaImagenes = FXCollections.observableArrayList(destino.getImagenes());

                listaImagenesSeleccionadaListView = listaImagenes;
            }
        });
    }

    private void cambiarFormatoImagenes(int i){
        if(i == listaImagenesSeleccionadaListView.size()){

        }else {
            String path = listaImagenesSeleccionadaListView.get(i);
            String pathSplt[] = path.split("resources");
            System.out.println(pathSplt[1]);
            Image nuevaImagen = new Image(getClass().getResourceAsStream(pathSplt[1]));
            listaImagenesSeleccionadaListViewImages.add(nuevaImagen);
            cambiarFormatoImagenes(i + 1);
        }
    }

    private void mostrarImagenActual() {
        if (!listaImagenesSeleccionadaListViewImages.isEmpty() && indice >= 0 && indice < listaImagenesSeleccionadaListViewImages.size()) {
            Image imagenAcual = listaImagenesSeleccionadaListViewImages.get(indice);
            imageViewDestinos.setImage(imagenAcual);
        } else {
            imageViewDestinos.setImage(null); //No hay imagenes en la lista
        }
    }

    private void actualizarListView() {
        ObservableList<Destino> observableListaIdiomas = FXCollections.observableArrayList(destinos);
        listViewDestinos.setItems(observableListaIdiomas);
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

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        listaImagenesSeleccionadaListView = FXCollections.observableArrayList();
        listaGuiasTuristicos = FXCollections.observableArrayList(agenciaViajes.getListaGuiasTuristicos());
        listaDestinosOriginal = FXCollections.observableArrayList(agenciaViajes.getListaDestinos());
        destinos = FXCollections.observableArrayList();
        listaPaquetes = FXCollections.observableArrayList(agenciaViajes.getListaPaquetesTuristicos());
        listaImagenesSeleccionadaListViewImages = new ArrayList<>();


        comboBoxPaquete.setItems(listaPaquetes);
        comboBoxFiltroDestino.setItems(listaDestinosOriginal);
        comboBoxSelectGuia.setItems(listaGuiasTuristicos);
        //imageViewDestinos.setImage(new Image(getClass().getResourceAsStream("/Imagenes/ImagenIconoAvion.jpg")));
    }

}
