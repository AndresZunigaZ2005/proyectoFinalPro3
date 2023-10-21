package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Reserva {

    private LocalDate fechaSolicitud;
    private LocalDateTime fechaViaje;
    private int cantPersonas;
    private Cliente clienteReserva;
    private PaqueteTuristico paqueteTuristico;
    private GuiaTuristico guiaTuristico;
    private EstadoReserva estadoReserva;
}
