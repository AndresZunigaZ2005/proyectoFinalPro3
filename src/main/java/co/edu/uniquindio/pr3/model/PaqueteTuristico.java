package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PaqueteTuristico implements Serializable, Comparable<PaqueteTuristico>{

    private String nombre;
    private int duracion;
    private String servicios;
    private double precio;
    private int cupoMaximo;
    private LocalDateTime fecha;
    private ArrayList<Destino> listaDestinos;
    private HashMap<String, Integer> calificaciones;

    @Override
    public String toString() {
        return nombre;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    @Override
    public int compareTo(PaqueteTuristico otro) {
        return Double.compare(otro.precio, this.precio);
    }

    public double mostrarCalificacionPaquete(){
        return (double) calificaciones.get("Calificaciones") /calificaciones.get("Cantidad de Calificaciones");
    }
}
