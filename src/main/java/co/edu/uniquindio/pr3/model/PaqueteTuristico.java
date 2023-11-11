package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PaqueteTuristico implements Serializable {

    private String nombre;
    private int duracion;
    private String servicios;
    private double precio;
    private int cupoMaximo;
    private LocalDate fecha;
    private ArrayList<Destino> listaDestinos;
}
