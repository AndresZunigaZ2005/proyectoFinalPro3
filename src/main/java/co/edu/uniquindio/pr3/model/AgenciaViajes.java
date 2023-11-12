package co.edu.uniquindio.pr3.model;

import co.edu.uniquindio.pr3.exceptions.*;
import co.edu.uniquindio.pr3.utils.ArchivoUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Getter
@Setter
public class AgenciaViajes {
    private ArrayList<Destino> listaDestinos;
    private final String RUTA_DESTINOS;
    private ArrayList<Reserva> listaReservas;
    private final String RUTA_RESERVAS;
    private ArrayList<PaqueteTuristico> listaPaquetesTuristicos;
    private final String RUTA_PAQUETETURISTICO;
    private ArrayList<Cliente> listaClientes;
    private final String RUTA_CLIENTE;
    private ArrayList<GuiaTuristico> listaGuiasTuristicos;
    private final String RUTA_GUIATURISTICO;
    private ArrayList<Administrador> listaAdministrador;
    private final String RUTA_ADMINISTRADOR;

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";
    //LOGGERS
    private static final Logger LOGGER =Logger.getLogger(AgenciaViajes.class.getName());


    //Variable para el SINGLETON
    private static AgenciaViajes agenciaViajes;

    /*
    Constructores
     */

    private AgenciaViajes() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        LOGGER.log(Level.INFO, "Se crea una nueva instanica de Agencia de viajes");

        System.out.println("Aqui funciona");
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(RUTA_PROPIEDADES);
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Aqui funciona 2");

        RUTA_DESTINOS = prop.getProperty("RUTA_DESTINOS");
        RUTA_RESERVAS = prop.getProperty("RUTA_RESERVAS");
        RUTA_PAQUETETURISTICO = prop.getProperty("RUTA_PAQUETETURISTICO");
        RUTA_CLIENTE = prop.getProperty("RUTA_CLIENTE");
        RUTA_GUIATURISTICO = prop.getProperty("RUTA_GUIATURISTICO");
        RUTA_ADMINISTRADOR = prop.getProperty("RUTA_ADMINISTRADOR");

        this.listaDestinos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaPaquetesTuristicos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaGuiasTuristicos = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();

        leerDestinos();
        leerReserva();
        leerPaqueteTuristico();
        leerClienteSerializable();
        leerGuiaTuristico();
        leerAdministrador();

    }

    public static AgenciaViajes getInstance() {
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
     * @param correo
     * @return Cliente
     */
    public Cliente obtenerCliente(String correo) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCorreo().equals(correo)) {
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
                              String telefono, String direccion, String contrasenia, String imagen) throws ClienteVacioException,ClienteExisteException{
        if (nombre == null || identificacion == null || correo == null || telefono == null || direccion == null || contrasenia == null
                || nombre.isBlank() || identificacion.isBlank() || correo.isBlank() || telefono.isBlank() || direccion.isBlank() || contrasenia.isBlank()) {
                throw new ClienteVacioException("El cliente se agrego con vacios, por favor, llene todos los valores obligatoriamente");
        } else {
            if (obtenerCliente(identificacion) != null) {
                throw new ClienteExisteException("El cliente que desea agregar ya existe, por favor revise su identificación");
            }else{
                Cliente nuevoCliente = new Cliente(nombre, identificacion ,correo,telefono,direccion, contrasenia, imagen);
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
     * Metodos del administrador
     */

    public Administrador obtenerAdministrador(String correo, int i){
        if(i>=listaAdministrador.size()){
            return null;
        }
        if(listaAdministrador.get(i).getCorreo().equals(correo)){
            return listaAdministrador.get(i);
        }
        return obtenerAdministrador(correo, i+1);
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

    public void leerAdministrador(){
        try(Scanner scanner = new Scanner(new File("src/main/resources/persistencia/administrador.txt"))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");
                if( datos.length == 6 ) {
                    this.listaAdministrador.add(new Administrador.AdministradorBuilder()
                            .nombre(datos[0])
                            .correo(datos[1])
                            .contrasenia(datos[2])
                            .build());
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
