package co.edu.uniquindio.pr3.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Persona {

    private String nombre;
    private String identificacion;
}
