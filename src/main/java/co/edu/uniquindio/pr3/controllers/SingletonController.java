package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.Administrador;
import co.edu.uniquindio.pr3.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class SingletonController {

    private static SingletonController instance;
    private Cliente cliente;
    private Administrador administrador;
    private StringBuilder codigoRecuperacionCorreo;

    public SingletonController() {
        this.cliente = null;
        this.administrador = null;
        this.codigoRecuperacionCorreo = null;
    }

    public static SingletonController getInstance(){
        if(instance == null){
            instance = new SingletonController();
        }
        return instance;
    }

    private String generarCodigoAleatorio() {
        codigoRecuperacionCorreo = null;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(caracteres.length());
            codigoRecuperacionCorreo.append(caracteres.charAt(index));
        }

        return codigoRecuperacionCorreo.toString();
    }

    private String generarContenidoHTML(String codigo) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div style=\"display: flex;\">");

        for (int i = 0; i < codigo.length(); i++) {
            htmlBuilder.append("<div style=\"border: 1px solid black; width: 40px; height: 40px; text-align: center; font-size: 20px; margin-right: 10px;\">");
            htmlBuilder.append(codigo.charAt(i));
            htmlBuilder.append("</div>");
        }

        htmlBuilder.append("</div>");

        return htmlBuilder.toString();
    }

}
