package co.edu.uniquindio.pr3.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder

public class GuiaTuristico extends Persona{

    private int experiancia;
    private ArrayList<Lengua> listaLenguas;
}
