package co.edu.uniquindio.pr3.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@SuperBuilder

public class GuiaTuristico extends Persona implements Serializable {

    private int experiencia;
    private ArrayList<Lengua> listaLenguas;
    private HashMap<String, Integer> calificacionesGuia;

    public GuiaTuristico(String nombre, String identificacion, int experiencia ,ArrayList<Lengua> lenguas) {
        super(nombre,identificacion);
        this.experiencia = experiencia;
        this.listaLenguas = lenguas;
        HashMap<String, Integer> calificacion = new HashMap<>();
        calificacion.put("sumaCalificaciones",0);
        calificacion.put("cantidadCalificaciones",0);
        this.calificacionesGuia = calificacion;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
