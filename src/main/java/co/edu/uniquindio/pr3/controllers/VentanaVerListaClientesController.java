package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaVerListaClientesController implements Initializable {

    @FXML
    private TableColumn<Cliente, String> columnCorreo;

    @FXML
    private TableColumn<Cliente, String> columnDireccion;

    @FXML
    private TableColumn<Cliente, String> columnID;

    @FXML
    private TableColumn<Cliente, String> columnNombre;

    @FXML
    private TableColumn<Cliente, String> columnTelefono;

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TextField txtFieldFIltroNombre;

    @FXML
    private TextField txtFieldFIltroIdentificacion;

    @FXML
    private Button btnLimpiarFiltros;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    private ObservableList<Cliente> listaOriginaClientes = FXCollections.observableArrayList(agenciaViajes.getListaClientes());

    @FXML
    void filtrarNombre(ActionEvent event) {
        aplicarFiltroNombre();
        txtFieldFIltroIdentificacion.clear();
    }

    @FXML
    void filtrarIdentificacion(ActionEvent event){
        aplicarFiltroIdentificacion();
        txtFieldFIltroNombre.clear();
    }

    @FXML
    void limpiarFiltros(ActionEvent event){
        txtFieldFIltroIdentificacion.clear();
        txtFieldFIltroNombre.clear();
        aplicarFiltroNombre();
        aplicarFiltroIdentificacion();
    }

    private void aplicarFiltroNombre() {
        String filtro = txtFieldFIltroNombre.getText().toLowerCase();

        ObservableList<Cliente> personasFiltradas;
        if (filtro.isEmpty()) {
            personasFiltradas = listaOriginaClientes;
        } else {
            personasFiltradas = listaOriginaClientes.filtered(persona ->
                    persona.getNombre().toLowerCase().contains(filtro) ||
                            String.valueOf(persona.getIdentificacion()).contains(filtro));
        }

        tableViewClientes.setItems(personasFiltradas);
    }

    private void aplicarFiltroIdentificacion() {
        String filtro = txtFieldFIltroIdentificacion.getText().toLowerCase();

        ObservableList<Cliente> personasFiltradas;
        if (filtro.isEmpty()) {
            personasFiltradas = listaOriginaClientes;
        } else {
            personasFiltradas = listaOriginaClientes.filtered(persona ->
                    persona.getIdentificacion().toLowerCase().contains(filtro) ||
                            String.valueOf(persona.getIdentificacion()).contains(filtro));
        }

        tableViewClientes.setItems(personasFiltradas);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnID.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        // Limpiar la lista observable antes de agregar nuevos elementos
        listaClientes.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Agregar los clientes al TableView
                listaClientes.addAll(agenciaViajes.getListaClientes());
                tableViewClientes.setItems(listaClientes);
            }
        }).start();

    }
}
