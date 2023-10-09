package co.edu.uniquindio.pr3.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Cliente extends Persona{

    private String correo;
    private String telefono;
    private String direccion;
}
