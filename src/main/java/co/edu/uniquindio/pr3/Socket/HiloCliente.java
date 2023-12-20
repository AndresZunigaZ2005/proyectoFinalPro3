package co.edu.uniquindio.pr3.Socket;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCliente implements Runnable{
    private final Socket socket;
    private final AgenciaViajes agencia;
    public HiloCliente(Socket socket, AgenciaViajes agencia){
        this.socket = socket;
        this.agencia = agencia;
    }
    @Override
    public void run() {
        try {
            //Se crean flujos de datos de entrada y salida para comunicarse a través del socket
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream() );
            //Se lee el mensaje enviado por el cliente
            Mensaje mensaje = (Mensaje) in.readObject();
            //Se captura el tipo de mensaje
            String tipo = mensaje.getTipo();
            //Se captura el contenido del mensaje
            Object contenido = mensaje.getContenido();
            //Según el tipo de mensaje se invoca el método correspondiente
            switch (tipo) {
                case "anadirCliente":
                    anadirCliente((Cliente) contenido, out);
                    break;
                case "crearDestino":

                    break;
            }
            //Se cierra la conexión del socket para liberar los recursos asociados
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void anadirCliente(Cliente cliente, ObjectOutputStream out) throws IOException {
        try {
            //agencia.anadirCliente(cliente);
            out.writeObject("Cliente agregado correctamente");
        }catch (Exception e){
            out.writeObject(e.getMessage());
        }
    }
}
