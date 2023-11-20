package co.edu.uniquindio.pr3.model;

import co.edu.uniquindio.pr3.exceptions.*;
import co.edu.uniquindio.pr3.utils.ArchivoUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    private static final String HOST = "localhost";
    private static final int PUERTO = 1234;

    /*
    Constructores de agencia de viajes
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
    public Cliente obtenerClienteCorreo(String correo, int i) {
        if(i == listaClientes.size()){
            return null;
        }
        if(listaClientes.get(i).getCorreo().equals(correo)){
            return listaClientes.get(i);
        }
        else{
            return obtenerClienteCorreo(correo, i+1);
        }
    }

    /**
     *
     * @param identificacion
     * @param i siempre se inicializa en 0
     * @return
     */
    public Cliente obtenerClienteIdentificacion(String identificacion, int i) {
        if(i == listaClientes.size()){
            return null;
        }
        if(listaClientes.get(i).getIdentificacion().equals(identificacion)){
            return listaClientes.get(i);
        }
        else{
            return obtenerClienteIdentificacion(identificacion, i+1);
        }
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
            if (obtenerAdministrador(correo, 0) != null || obtenerClienteIdentificacion(identificacion, 0) != null || obtenerClienteCorreo(correo, 0) != null) {
                throw new ClienteExisteException("El cliente que desea agregar ya existe, por favor revise su identificación o el correo");
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
    public void actualizarCliente(String nombre, String identificacion ,String correo, String telefono, String direccion, String imagen) throws ClienteVacioException {
        Cliente clienteActualizar = obtenerClienteIdentificacion(identificacion, 0);
        if(clienteActualizar.getNombre().equals(nombre) && clienteActualizar.getCorreo().equals(correo)
        && clienteActualizar.getTelefono().equals(telefono) && clienteActualizar.getImagen() != null && clienteActualizar.getImagen().equals(imagen) && sonImagenesIguales(clienteActualizar.getImagen(), imagen)){
            throw new ClienteVacioException("No se actulizó, todos los datos son iguales");
        }
        else if (nombre == null || correo == null || telefono == null || direccion == null
                || nombre.isBlank() || correo.isBlank() || telefono.isBlank() || direccion.isBlank()) {
            throw new ClienteVacioException("Por favor, llene todos los valores obligatoriamente");
        }
        else {
            clienteActualizar.setNombre(nombre);
            clienteActualizar.setCorreo(correo);
            clienteActualizar.setDireccion(direccion);
            clienteActualizar.setTelefono(telefono);
            escribirClientes();
            LOGGER.log(Level.INFO, "Se ha actualizado el cliente de identificación "+identificacion);
        }
    }

    private static boolean sonImagenesIguales(String rutaImagen1, String rutaImagen2) {
        try {
            byte[] contenidoImagen1 = Files.readAllBytes(Paths.get(rutaImagen1));
            byte[] contenidoImagen2 = Files.readAllBytes(Paths.get(rutaImagen2));

            return Arrays.equals(contenidoImagen1, contenidoImagen2);
        } catch (IOException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente en tu aplicación
            return false; // En caso de error, asumimos que las imágenes no son iguales
        }
    }

    /**
     * Metodos CRUD para la creacion de destinos
     */


    /**
     * Metodos CRUD para la obtener destinos mediante la ciudad
     * @param nombre
     * @return
     */
    public Destino obtenerDestino(String nombre, int i){
        if(i == listaDestinos.size()){
            return null;
        }
        if(listaDestinos.get(i).getNombre().equals(nombre)){
            return listaDestinos.get(i);
        }
        else{
            return obtenerDestino(nombre, i+1);
        }
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
    public void crearDestino(String nombre,String ciudad, ArrayList<String> imagenes, Clima clima) throws DestinoVacioException, DestinoExisteException {
        if(nombre==null||ciudad==null || nombre.isBlank() || ciudad.isBlank()){
            throw new DestinoVacioException("El destino que usted desea agregar se añadio con vacios, por favor, ingrese los datos en todos los campos");
        }else if(obtenerDestino(nombre,0)!=null){
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

    public void actualizarDestino(String nombre,String ciudad, ArrayList<String> imagenes, Clima clima) throws DestinoVacioException, DestinoExisteException {
        if(nombre == null || ciudad == null || nombre.isBlank() || ciudad.isBlank()){
            throw new DestinoVacioException("El destino que usted desea agregar se añadio con vacios, por favor, ingrese los datos en todos los campos");
        }else{
            Destino destinoActualizar = obtenerDestino(nombre, 0);
            destinoActualizar.setCiudad(ciudad);
            destinoActualizar.setClima(clima);
            destinoActualizar.setImagenes(imagenes);
            LOGGER.log(Level.INFO, "El Destino "+ciudad+" se ha creado");
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
    public PaqueteTuristico obtenerPaqueteTuristico(String nombre, int i){
        if(i == listaPaquetesTuristicos.size()){
            return null;
        }
        if(listaPaquetesTuristicos.get(i).getNombre().equals(nombre)){
            return listaPaquetesTuristicos.get(i);
        }
        else{
            return obtenerPaqueteTuristico(nombre, i+1);
        }
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
                                      double precio, int cupoMaximo, LocalDateTime fecha, ArrayList<Destino> listaDestinos) throws PaqueteVacioException, PaqueteExisteException, PaqueteUnoDiferenciaException {
        if(nombre==null || nombre.isBlank() || duracion<=0 || servicios==null ||
        servicios.isBlank() || precio<=0 || cupoMaximo<=0 || fecha.isBefore(LocalDateTime.now())){
            throw new PaqueteVacioException("El paquete turistico que desea ingresar, tiene campos vacios, por favor ingrese todos los campos");
        } else if (obtenerPaqueteTuristico(nombre, 0)!=null) {
            throw new PaqueteExisteException("El paquete turistico ya existe");
        } else if (ChronoUnit.DAYS.between(LocalDateTime.now(), fecha) <= 1) {
            throw new PaqueteUnoDiferenciaException("El paquete turistico debe de ser creado con un día de anticipación");
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Calificaciones", 0);
            map.put("Cantidad de Calificaciones", 0);
            PaqueteTuristico p = new PaqueteTuristico.PaqueteTuristicoBuilder()
                    .nombre(nombre)
                    .duracion(duracion)
                    .servicios(servicios)
                    .precio(precio)
                    .cupoMaximo(cupoMaximo)
                    .fecha(fecha)
                    .listaDestinos(listaDestinos)
                    .calificaciones(map)
                    .build();

            LOGGER.log(Level.INFO, "El paquete turistico "+nombre+" se ha creado");
            listaPaquetesTuristicos.add(p);
            escribirPaqueteTuristico();
        }
    }



    /*
    Metodo para ordenar la lista de menor a mayor y mayor a menor
     */

    public void ordernarListaPaquetesMenorMayor(){
        Collections.sort(listaPaquetesTuristicos);
    }

    public void ordernarListaPaquetesMayorMenor(){
        Collections.sort(listaPaquetesTuristicos, Collections.reverseOrder());
    }

    public ArrayList<PaqueteTuristico> mostrarPaquetesFechaDisponible(int i, ArrayList<PaqueteTuristico> p){
        if(i == listaPaquetesTuristicos.size()) return p;
        if(listaPaquetesTuristicos.get(i).getFecha().isAfter(LocalDateTime.now())){
            p.add(listaPaquetesTuristicos.get(i));
        }
        return mostrarPaquetesFechaDisponible(i+1, p);
    }

    public void ponerEstadoReservaPasada(int i){
        if(i == listaReservas.size()){

        }else {
            if (listaReservas.get(i).getPaqueteTuristico().getFecha().isBefore(LocalDateTime.now())) {
                listaReservas.get(i).setEstadoReserva(EstadoReserva.PASADA);
            }
            ponerEstadoReservaPasada(i + 1);
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
    public Reserva obtenerReserva(String identificacion, LocalDateTime fechaReserva) {
        return obtenerReservaRecursivo(identificacion, fechaReserva, 0);
    }

    private Reserva obtenerReservaRecursivo(String identificacion, LocalDateTime fechaReserva, int index) {
        if (index >= listaReservas.size()) {
            return null; // No se encontró la reserva
        }

        Cliente cliente = obtenerClienteIdentificacion(identificacion, 0);
        Reserva reserva = listaReservas.get(index);

        if (reserva.getClienteReserva().equals(cliente) && reserva.getFechaViaje().equals(fechaReserva)) {
            return reserva; // Se encontró la reserva
        }

        // Llamada recursiva para buscar en la siguiente reserva
        return obtenerReservaRecursivo(identificacion, fechaReserva, index + 1);
    }

    /**
     *
     * @param fechaViaje
     * @param cantPersonas
     * @param identificacionCliente
     * @param nombrePaqueteTuristico
     * @param identificacionGuia
     */
    public void crearReserva(LocalDateTime fechaViaje, int cantPersonas,
            String identificacionCliente, String nombrePaqueteTuristico, String identificacionGuia) throws ReservaExisteException,ReservaVaciaException{
        if(fechaViaje == null || cantPersonas<=0 || identificacionCliente == null || identificacionCliente.isBlank()){
            throw new ReservaVaciaException("La reserva que desea crear es vacia, por favor, llene todos los campos");
        }
        if(obtenerReserva(identificacionCliente, fechaViaje) != null && obtenerReserva(identificacionCliente, fechaViaje).getEstadoReserva().equals(EstadoReserva.CONFIRMADA)){
            throw new ReservaExisteException("La reserva que desea crear ya existe, por favor intente de nuevo");
        }else{
            Reserva newReserva = new Reserva.ReservaBuilder()
                    .fechaSolicitud(LocalDate.now())
                    .fechaViaje(fechaViaje)
                    .cantPersonas(cantPersonas)
                    .clienteReserva(obtenerClienteIdentificacion(identificacionCliente, 0))
                    .paqueteTuristico(obtenerPaqueteTuristico(nombrePaqueteTuristico,0))
                    .guiaTuristico(obtenerGuiaTuristico(identificacionGuia, 0))
                    .estadoReserva(EstadoReserva.CONFIRMADA)
                    .build();
            LOGGER.log(Level.INFO, "El cliente de cedula "+identificacionCliente+" ha creado una reserva");
            listaReservas.add(newReserva);
            escribirReservas();
        }
    }

    public ArrayList<Reserva> buscarReservaCliente(Cliente cliente, ArrayList<Reserva> lista,  int i){
        if(i == listaReservas.size()){
            return lista;
        }
        if(listaReservas.get(i).getClienteReserva().equals(cliente)){
            lista.add(listaReservas.get(i));
        }
        return buscarReservaCliente(cliente, lista, i+1);
    }

    /*
    Metodos para la creacion de guias turisticos
    */
    public GuiaTuristico obtenerGuiaTuristico(String identificacionGuia, int i) {
        if(i == listaGuiasTuristicos.size()){
            return null;
        }
        if(listaGuiasTuristicos.get(i).getIdentificacion().equals(identificacionGuia)){
            return listaGuiasTuristicos.get(i);
        }
        else{
            return obtenerGuiaTuristico(identificacionGuia, i+1);
        }
    }

    public void crearGuiaTuristico(String nombre, String identificacion, int experciencia, ArrayList<Lengua> lenguas) throws GuiaTuristicoVacioException, GuiaTuristicoExisteException, IOException {
        if(nombre == null || nombre.isBlank() || identificacion == null || identificacion.isBlank() ||
                experciencia<0 || lenguas == null){
            throw new GuiaTuristicoVacioException("El guía turistico que desea añadir tiene tiene campos vacios, por favor rellene todos los espacios");
        }
        if(obtenerGuiaTuristico(identificacion, 0)!=null){
            throw new GuiaTuristicoExisteException("El guía turistico que dese añadir ya existe");
        }else{
            GuiaTuristico guiaTuristico = new GuiaTuristico(nombre, identificacion, experciencia, lenguas);
            LOGGER.log(Level.INFO, "El Guia Turistico de identificación "+identificacion+" se ha registrado");
            listaGuiasTuristicos.add(guiaTuristico);
            escribirGuiaTuristico();
        }
    }

    public void actualizarGuiaTuristico(String nombre, String identificacion, int experciencia, ArrayList<Lengua> lenguas) throws GuiaTuristicoVacioException, GuiaTuristicoExisteException, IOException {
        if(nombre == null || nombre.isBlank() || identificacion == null || identificacion.isBlank() ||
                experciencia<0 || lenguas == null){
            throw new GuiaTuristicoVacioException("El guía turistico que desea actualizar tiene tiene campos vacios, por favor rellene todos los espacios");
        }
        else{
            GuiaTuristico guiaTuristico = obtenerGuiaTuristico(identificacion, 0);
            guiaTuristico.setNombre(nombre);
            guiaTuristico.setExperiencia(experciencia);
            guiaTuristico.setListaLenguas(lenguas);
            LOGGER.log(Level.INFO, "El Guia Turistico de identificación "+identificacion+" se ha registrado");
            escribirGuiaTuristico();
        }
    }


    public void ordenarGuiaCalificacionMayorMenor(){
        Collections.sort(listaGuiasTuristicos);
    }

    public void ordenarGuiaMenorMayor() {
        Collections.sort(listaGuiasTuristicos, Collections.reverseOrder());
    }


    public ArrayList<Destino> ordenarDestinosMasReservados(){
        // Contar la cantidad de reservas para cada destino
        Map<Destino, Integer> contadorDestinos = new HashMap<>();
        for (Reserva reserva : listaReservas) {
            for (Destino destino : reserva.getPaqueteTuristico().getListaDestinos()) {
                contadorDestinos.put(destino, contadorDestinos.getOrDefault(destino, 0) + 1);
            }
        }

        // Ordenar los destinos según la cantidad de reservas (en orden descendente)
        ArrayList<Destino> destinosOrdenados = new ArrayList<>(contadorDestinos.keySet());
        destinosOrdenados.sort((d1, d2) -> contadorDestinos.get(d2).compareTo(contadorDestinos.get(d1)));
        return destinosOrdenados;
    }


    public void calificarPaquete(PaqueteTuristico paqueteTuristico, int calificacion){
        HashMap<String, Integer> mapaNotas = paqueteTuristico.getCalificaciones();
        mapaNotas.put("Calificaciones", mapaNotas.get("Calificaciones") + calificacion);
        mapaNotas.put("Cantidad de Calificaciones", mapaNotas.get("Cantidad de Calificaciones")+1);
        paqueteTuristico.setCalificaciones(mapaNotas);
    }

    public void calificarGuia(GuiaTuristico guiaTuristico, int calificacion){
        HashMap<String, Integer> mapaNotas = guiaTuristico.getCalificacionesGuia();
        mapaNotas.put("sumaCalificaciones", mapaNotas.get("sumaCalificaciones") + calificacion);
        mapaNotas.put("cantidadCalificaciones", mapaNotas.get("cantidadCalificaciones")+1);
        guiaTuristico.setCalificacionesGuia(mapaNotas);
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
            ponerEstadoReservaPasada(0);
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
