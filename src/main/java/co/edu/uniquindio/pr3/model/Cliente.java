package co.edu.uniquindio.pr3.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Cliente extends Persona{

    private String correo;
    private String telefono;
    private String direccion;
    private String contrasenia;

    public Cliente(String nombre, String identificacion, String correo, String telefono, String direccion, String contrasenia) {
        super(nombre,identificacion);
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasenia = contrasenia;
    }
}
