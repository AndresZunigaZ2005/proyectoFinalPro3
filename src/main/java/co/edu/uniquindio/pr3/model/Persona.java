package co.edu.uniquindio.pr3.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Persona implements Serializable {

    private String nombre;
    private String identificacion;

    @Override
    public String toString() {
        return nombre;
    }
}
