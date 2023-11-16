package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PaqueteTuristico implements Serializable {

    private String nombre;
    private int duracion;
    private String servicios;
    private double precio;
    private int cupoMaximo;
    private LocalDateTime fecha;
    private ArrayList<Destino> listaDestinos;

    @Override
    public String toString() {
        return nombre;
    }
}
