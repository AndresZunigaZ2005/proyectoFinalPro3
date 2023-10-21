package co.edu.uniquindio.pr3.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder

public class GuiaTuristico extends Persona implements Serializable {

    private int experiencia;
    private ArrayList<Lengua> listaLenguas;

    public GuiaTuristico(String nombre, String identificacion, int experiencia ,ArrayList<Lengua> lenguas) {
        super(nombre,identificacion);
        this.experiencia = experiencia;
        this.listaLenguas = lenguas;
    }
}
