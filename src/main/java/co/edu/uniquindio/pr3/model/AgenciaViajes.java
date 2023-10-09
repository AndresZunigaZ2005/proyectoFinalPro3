package co.edu.uniquindio.pr3.model;


import java.util.ArrayList;

public class AgenciaViajes {

    private String nombre;
    private ArrayList<Destino> listaDestinos;
    private ArrayList<Reserva> listaReservas;
    private ArrayList<PaqueteTuristico> listaPaquetesTuristicos;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<GuiaTuristico> listaGuiasTuristicos;

    /*
    Constructores
     */
    public AgenciaViajes() {
        this.listaDestinos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaPaquetesTuristicos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaGuiasTuristicos = new ArrayList<>();
    }

    public AgenciaViajes(String nombre) {
        this.nombre = nombre;
        this.listaDestinos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaPaquetesTuristicos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaGuiasTuristicos = new ArrayList<>();
    }

    public AgenciaViajes(String nombre, ArrayList<Destino> listaDestinos, ArrayList<Reserva> listaReservas, ArrayList<PaqueteTuristico> listaPaquetesTuristicos, ArrayList<Cliente> listaClientes, ArrayList<GuiaTuristico> listaGuiasTuristicos) {
        this.nombre = nombre;
        this.listaDestinos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaPaquetesTuristicos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaGuiasTuristicos = new ArrayList<>();
    }

    /*
        Metodos setter y getter
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Destino> getListaDestinos() {
        return listaDestinos;
    }

    public void setListaDestinos(ArrayList<Destino> listaDestinos) {
        this.listaDestinos = listaDestinos;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public ArrayList<PaqueteTuristico> getListaPaquetesTuristicos() {
        return listaPaquetesTuristicos;
    }

    public void setListaPaquetesTuristicos(ArrayList<PaqueteTuristico> listaPaquetesTuristicos) {
        this.listaPaquetesTuristicos = listaPaquetesTuristicos;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<GuiaTuristico> getListaGuiasTuristicos() {
        return listaGuiasTuristicos;
    }

    public void setListaGuiasTuristicos(ArrayList<GuiaTuristico> listaGuiasTuristicos) {
        this.listaGuiasTuristicos = listaGuiasTuristicos;
    }
}
