package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Cliente extends Persona implements Serializable {

    private String correo;
    private String telefono;
    private String direccion;
    private String contrasenia;
    private String imagen;

    public Cliente(String nombre, String identificacion, String correo, String telefono, String direccion, String contrasenia, String imagen) {
        super(nombre,identificacion);
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasenia = contrasenia;
        this.imagen = imagen;
    }
}
