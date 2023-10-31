package co.edu.uniquindio.pr3.model;

import co.edu.uniquindio.pr3.exceptions.*;
import co.edu.uniquindio.pr3.utils.ArchivoUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AgenciaViajes {


    private ArrayList<Destino> listaDestinos;
    private final String RUTA_DESTINOS = "src/main/resources/persistencia/destinos.data";
    private ArrayList<Reserva> listaReservas;
    private final String RUTA_RESERVAS = "src/main/resources/persistencia/reserva.data";
    private ArrayList<PaqueteTuristico> listaPaquetesTuristicos;
    private final String RUTA_PAQUETETURISTICO = "src/main/resources/persistencia/paqueteTuristico.data";
    private ArrayList<Cliente> listaClientes;
    private final String RUTA_CLIENTE = "src/main/resources/persistencia/cliente.data";
    private ArrayList<GuiaTuristico> listaGuiasTuristicos;
    private final String RUTA_GUIATURISTICO = "src/main/resources/persistencia/guiaTuristico.data";
    private ArrayList<Administrador> listaAdministrador;
    private final String RUTA_ADMINISTRADOR = "src/main/resources/persistencia/administrador.data";

    //LOGGERS
    private static final Logger LOGGER =Logger.getLogger(AgenciaViajes.class.getName());


    //Variable para el SINGLETON
    private static AgenciaViajes agenciaViajes;

    /*
    Constructores
     */

    private AgenciaViajes() throws IOException {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        LOGGER.log(Level.INFO, "Se crea una nueva instanica de Agencia de viajes");

        this.listaDestinos = new ArrayList<>();
        leerDestinos();

        this.listaReservas = new ArrayList<>();
        leerReserva();

        this.listaPaquetesTuristicos = new ArrayList<>();
        leerPaqueteTuristico();

        this.listaClientes = new ArrayList<>();
        leerClienteSerializable();

        this.listaGuiasTuristicos = new ArrayList<>();
        leerGuiaTuristico();

        this.listaAdministrador = new ArrayList<>();
        leerAdministrador();

    }

    public static AgenciaViajes getInstance() throws IOException {
        if (agenciaViajes == null){
            agenciaViajes = new AgenciaViajes();
        }
        return agenciaViajes;
    }

    /*
    Metodos relacionados con los clientes
     */

    /**
     * Metodo para obtener un cliente mediante su identificacion
     * @param identificacion
     * @return Cliente
     */
    public Cliente obtenerCliente(String identificacion) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getIdentificacion().equals(identificacion)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Metodo para crear un cliente con todos los atributos de la clase cliente
     * @param nombre
     * @param identificacion
     * @param correo
     * @param telefono
     * @param direccion
     * @param contrasenia
     * @throws ClienteVacioException
     * @throws ClienteExisteException
     */

    public void anadirCliente(String nombre, String identificacion, String correo,
                              String telefono, String direccion, String contrasenia) throws ClienteVacioException,ClienteExisteException{
        if (nombre == null || identificacion == null || correo == null || telefono == null || direccion == null || contrasenia == null
                || nombre.isBlank() || identificacion.isBlank() || correo.isBlank() || telefono.isBlank() || direccion.isBlank() || contrasenia.isBlank()) {
                throw new ClienteVacioException("El cliente se agrego con vacios, por favor, llene todos los valores obligatoriamente");
        } else {
            if (obtenerCliente(identificacion) != null) {
                throw new ClienteExisteException("El cliente que desea agregar ya existe, por favor revise su identificación");
            }else{
                Cliente nuevoCliente = new Cliente(nombre, identificacion ,correo,telefono,direccion, contrasenia);
                LOGGER.log(Level.INFO, "El cliente de identificación "+identificacion+" se ha registrado");
                listaClientes.add(nuevoCliente);
                escribirClientes();
            }
        }
    }

    /**
     * Metodo para actualizar los datos de un cliente, se puede actuzalizar todo menos la
     * identifiacion
     * @param nombre
     * @param identificacion
     * @param correo
     * @param telefono
     * @param direccion
     */
    public void actualizarCliente(String nombre, String identificacion ,String correo, String telefono, String direccion) throws ClienteVacioException {
        Cliente clienteActualizar = obtenerCliente(identificacion);
        if(clienteActualizar.getNombre().equals(nombre) && clienteActualizar.getCorreo().equals(correo)
        && clienteActualizar.getTelefono().equals(telefono)){
            throw new ClienteVacioException("El cliente se agrego con vacios, por favor, llene todos los valores obligatoriamente");
        }
        else if (nombre == null || correo == null || telefono == null || direccion == null
                || nombre.isBlank() || correo.isBlank() || telefono.isBlank() || direccion.isBlank()) {
            throw new ClienteVacioException("El cliente se agrego con vacios, por favor, llene todos los valores obligatoriamente");
        }
        else {
            clienteActualizar.setNombre(nombre);
            clienteActualizar.setCorreo(correo);
            clienteActualizar.setDireccion(direccion);
            clienteActualizar.setTelefono(telefono);
            LOGGER.log(Level.INFO, "Se ha actualizado el cliente de identificación "+identificacion);
        }
    }

    /**
     * Metodos CRUD para la creacion de destinos
     */


    /**
     * Metodos CRUD para la obtener destinos mediante la ciudad
     * @param ciudad
     * @return
     */
    public Destino obtenerDestino(String ciudad){
        for (Destino destino: listaDestinos) {
            if(destino.getCiudad().equals(ciudad)){
                return destino;
            }
        }
        return null;
    }


    /**
     * Metodo para crear destinos, constructor con todos los atributos
     * @param nombre
     * @param ciudad
     * @param imagenes
     * @param clima
     * @throws DestinoVacioException
     * @throws DestinoExisteException
     */
    public void crerDestino(String nombre,String ciudad, ArrayList<String> imagenes, Clima clima) throws DestinoVacioException, DestinoExisteException {
        if(nombre==null||ciudad==null || nombre.isBlank() || ciudad.isBlank()){
            throw new DestinoVacioException("El destino que usted desea agregar se añadio con vacios, por favor, ingrese los datos en todos los campos");
        }else if(obtenerDestino(ciudad)!=null){
            throw new DestinoExisteException("El destino que usted desea agreagar ya se añadio con anterioridad, por favor, ingrese otro");
        }else{
            Destino destino = new Destino.DestinoBuilder()
                    .nombre(nombre)
                    .ciudad(ciudad)
                    .imagenes(imagenes)
                    .clima(clima)
                    .build();
            LOGGER.log(Level.INFO, "El Destino "+ciudad+" se ha creado");
            listaDestinos.add(destino);
            escribirDestinos();
        }
    }

    /*
    Metodos para la creacion de un paquete turistico
     */

    /**
     *
     * @param nombre
     * @return
     */
    public PaqueteTuristico obtenerPaqueteTuristico(String nombre){
        for (PaqueteTuristico paqueteTuristico : listaPaquetesTuristicos) {
            if(paqueteTuristico.getNombre().equals(nombre)){
                return paqueteTuristico;
            }
        }
        return null;
    }

    /**
     *
     * @param nombre
     * @param duracion
     * @param servicios
     * @param precio
     * @param cupoMaximo
     * @param fecha
     * @param listaDestinos
     */
    public void crearPaqueteTuristico(String nombre, int duracion, String servicios,
                                      double precio, int cupoMaximo, LocalDate fecha, ArrayList<Destino> listaDestinos) throws PaqueteVacioException, PaqueteExisteException {
        if(nombre==null || nombre.isBlank() || duracion<=0 || servicios==null ||
        servicios.isBlank() || precio<=0 || cupoMaximo<=0 || fecha.isAfter(LocalDate.now())){
            throw new PaqueteExisteException("El paquete turistico que desea ingresar, tiene campos vacios, por favor ingrese todos los campos");
        } else if (obtenerPaqueteTuristico(nombre)!=null) {

        }else {
            PaqueteTuristico p = new PaqueteTuristico.PaqueteTuristicoBuilder()
                    .nombre(nombre)
                    .duracion(duracion)
                    .servicios(servicios)
                    .precio(precio)
                    .cupoMaximo(cupoMaximo)
                    .fecha(fecha)
                    .listaDestinos(listaDestinos)
                    .build();
            LOGGER.log(Level.INFO, "El paquete turistico "+nombre+" se ha creado");
            listaPaquetesTuristicos.add(p);
            escribirPaqueteTuristico();
        }
    }

    /*
    Metodos para la creacion de reservas
     */

    /**
     *
     * @param identificacion
     * @param fechaReserva
     * @return
     */
    public Reserva obtenerReserva(String identificacion, LocalDateTime fechaReserva){
        Cliente cliente = obtenerCliente(identificacion);
        for (Reserva reserva: listaReservas){
            if(reserva.getClienteReserva().equals(cliente) && reserva.getFechaViaje().equals(fechaReserva)){
                return reserva;
            }
        }
        return null;
    }

    /**
     *
     * @param fechaViaje
     * @param cantPersonas
     * @param identificacionCliente
     * @param nombrePaqueteTuristico
     * @param identificacionGuia
     * @param estadoReserva
     */
    public void crearReserva(LocalDateTime fechaViaje, int cantPersonas,
            String identificacionCliente, String nombrePaqueteTuristico, String identificacionGuia,
            EstadoReserva estadoReserva) throws ReservaExisteException,ReservaVaciaException{
        if(fechaViaje == null || cantPersonas<=0 || identificacionCliente == null || identificacionCliente.isBlank() ||
                identificacionGuia==null || identificacionGuia.isBlank()){
            throw new ReservaVaciaException("La reserva que desea crear es vacia, por favor, llene todos los campos");
        }
        if(obtenerReserva(identificacionCliente, fechaViaje) != null){
            throw new ReservaExisteException("La reserva que desea crear ya existe, por favor intente de nuevo");
        }else{
            Reserva newReserva = new Reserva.ReservaBuilder()
                    .fechaSolicitud(LocalDate.now())
                    .fechaViaje(fechaViaje)
                    .cantPersonas(cantPersonas)
                    .clienteReserva(obtenerCliente(identificacionCliente))
                    .paqueteTuristico(obtenerPaqueteTuristico(nombrePaqueteTuristico))
                    .guiaTuristico(obtenerGuiaTuristico(identificacionGuia))
                    .estadoReserva(EstadoReserva.PENDIENTE)
                    .build();
            LOGGER.log(Level.INFO, "El cliente de cedula "+identificacionCliente+" ha creado una reserva");
            listaReservas.add(newReserva);
            escribirReservas();
        }
    }

    /*
    Metodos para la creacion de guias turisticos
    */
    public GuiaTuristico obtenerGuiaTuristico(String identificacionGuia) {
        for (GuiaTuristico guiaTuristico: listaGuiasTuristicos) {
            if(guiaTuristico.getIdentificacion().equals(identificacionGuia)){
                return guiaTuristico;
            }
        }
        return null;
    }

    public void crearGuiaTuristico(String nombre, String identificacion, int experciencia, ArrayList<Lengua> lenguas) throws GuiaTuristicoVacioException, GuiaTuristicoExisteException, IOException {
        if(nombre == null || nombre.isBlank() || identificacion == null || identificacion.isBlank() ||
                experciencia<0 || lenguas == null){
            throw new GuiaTuristicoVacioException("El guía turistico que desea añadir tiene tiene campos vacios, por favor rellene todos los espacios");
        }
        if(obtenerGuiaTuristico(identificacion)!=null){
            throw new GuiaTuristicoExisteException("El guía turistico que dese añadir ya existe");
        }else{
            GuiaTuristico guiaTuristico = new GuiaTuristico(nombre, identificacion, experciencia, lenguas);
            LOGGER.log(Level.INFO, "El Guia Turistico de identificación "+identificacion+" se ha registrado");
            listaGuiasTuristicos.add(guiaTuristico);
            escribirGuiaTuristico();
        }
    }




    /**
     * /////////////////////////////////////////////////////////////////////////////////
     */


    /**
     * Escribir datos
     */
    public void escribirDestinos(){
        try {
            ArchivoUtils.serializarObjeto(RUTA_DESTINOS, listaDestinos);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    
    public void escribirClientes(){
        try {
            ArchivoUtils.serializarObjeto(RUTA_CLIENTE, listaClientes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    public void escribirPaqueteTuristico(){
        try {
            ArchivoUtils.serializarObjeto(RUTA_PAQUETETURISTICO, listaPaquetesTuristicos);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    public void escribirReservas(){
        try {
            ArchivoUtils.serializarObjeto(RUTA_RESERVAS, listaReservas);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    public void escribirGuiaTuristico(){
        try {
            ArchivoUtils.serializarObjeto(RUTA_GUIATURISTICO, listaGuiasTuristicos);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    /**
     * leer datos
     */

    public void leerClienteSerializable(){
        try{
            this.listaClientes = (ArrayList<Cliente>)ArchivoUtils.deserializarObjeto(RUTA_CLIENTE);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void leerDestinos(){
        try{
            this.listaDestinos = (ArrayList<Destino>)ArchivoUtils.deserializarObjeto(RUTA_DESTINOS);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void leerReserva(){
        try{
            this.listaReservas = (ArrayList<Reserva>)ArchivoUtils.deserializarObjeto(RUTA_RESERVAS);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void leerGuiaTuristico(){
        try{
            this.listaGuiasTuristicos = (ArrayList<GuiaTuristico>)ArchivoUtils.deserializarObjeto(RUTA_GUIATURISTICO);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void leerPaqueteTuristico(){
        try{
            this.listaPaquetesTuristicos = (ArrayList<PaqueteTuristico>)ArchivoUtils.deserializarObjeto(RUTA_PAQUETETURISTICO);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void leerAdministrador(){
        try{
            this.listaAdministrador = (ArrayList<Administrador>)ArchivoUtils.deserializarObjeto(RUTA_ADMINISTRADOR);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
