package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Destino implements Serializable {

    private String nombre;
    private String ciudad;
    private ArrayList<String> imagenes;
    private Clima clima;
}
