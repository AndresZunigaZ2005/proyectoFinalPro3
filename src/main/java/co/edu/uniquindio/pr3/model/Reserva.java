package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Reserva {

    private LocalDate fechaSolicitud;
    private LocalDate fechaViaje;
    private int cantPersonas;
    private Cliente clienteReserva;
    private PaqueteTuristico paqueteTuristico;
    private EstadoReserva estadoReserva;
}
