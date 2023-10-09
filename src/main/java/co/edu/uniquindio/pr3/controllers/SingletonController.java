package co.edu.uniquindio.pr3.controllers;

import lombok.Getter;

import java.util.ResourceBundle;

public class SingletonController {

    @Getter
    private final ResourceBundle resourceBundle;
    private static SingletonController instancia;
    private SingletonController(){
        this.resourceBundle = ResourceBundle.getBundle("textos");
    }
    public static SingletonController getInstance(){
        if(instancia == null){
            System.out.println("Aquí se invoco la instancia");
            instancia = new SingletonController();
        }
        System.out.println("Aquí se retorno la instancia");
        return instancia;
    }
}
