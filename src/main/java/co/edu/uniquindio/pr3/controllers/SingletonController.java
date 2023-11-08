package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.Administrador;
import co.edu.uniquindio.pr3.model.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingletonController {

    private static SingletonController instance;
    private Cliente cliente;
    private Administrador administrador;

    public static SingletonController getInstance(){
        if(instance == null){
            instance = new SingletonController();
        }
        return instance;
    }
}
