package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Reserva implements Serializable {

    private LocalDate fechaSolicitud;
    private LocalDateTime fechaViaje;
    private int cantPersonas;
    private Cliente clienteReserva;
    private PaqueteTuristico paqueteTuristico;
    private GuiaTuristico guiaTuristico;
    private EstadoReserva estadoReserva;

    public void cancelarReserva() {
        if (estadoReserva == EstadoReserva.PASADA) {
            estadoReserva = EstadoReserva.CANCELADA;
            System.out.println("Reserva cancelada correctamente.");
        } else {
            System.out.println("La reserva no puede ser cancelada en su estado actual.");
        }
    }

    public void confirmarReserva() {
        if (estadoReserva == EstadoReserva.PASADA) {
            estadoReserva = EstadoReserva.CONFIRMADA;
            System.out.println("Reserva confirmada correctamente.");
        } else {
            System.out.println("La reserva no puede ser confirmada en su estado actual.");
        }
    }

    public boolean yaPasoFecha () {
        LocalDateTime ahora = LocalDateTime.now();
        return fechaViaje.isBefore(ahora);
    }
}
