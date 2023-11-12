package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaVerListaClientesController implements Initializable {

    @FXML
    private TableColumn<Cliente, String> columnCorreo;

    @FXML
    private TableColumn<Cliente, String> columnDireccion;

    @FXML
    private TableColumn<Cliente, ImageView> columnFotoPerfil;

    @FXML
    private TableColumn<Cliente, String> columnID;

    @FXML
    private TableColumn<Cliente, String> columnNombre;

    @FXML
    private TableColumn<Cliente, String> columnTelefono;

    @FXML
    private TableView<Cliente> tableViewClientes;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private SingletonController singletonController = SingletonController.getInstance();

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnFotoPerfil.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        columnID.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        // Limpiar la lista observable antes de agregar nuevos elementos
        listaClientes.clear();

        // Agregar los clientes al TableView
        listaClientes.addAll(agenciaViajes.getListaClientes());
        tableViewClientes.setItems(listaClientes);
    }
}
