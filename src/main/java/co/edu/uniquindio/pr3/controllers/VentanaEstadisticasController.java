package co.edu.uniquindio.pr3.controllers;

import co.edu.uniquindio.pr3.model.AgenciaViajes;
import co.edu.uniquindio.pr3.model.Destino;
import co.edu.uniquindio.pr3.model.GuiaTuristico;
import co.edu.uniquindio.pr3.model.PaqueteTuristico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaEstadisticasController implements Initializable {

    @FXML
    private Button btnGenerarDestinosReservados;

    @FXML
    private Button btnGenerarMejoresGuias;

    @FXML
    private Button btnGenerarMejoresPaquetes;

    private AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private SingletonController singletonController = SingletonController.getInstance();

    private ArrayList<GuiaTuristico> listaOrdenadaCalificacionGuias = new ArrayList<>();

    private ArrayList<PaqueteTuristico> listaOrdenadaCalificacionPaquetes = new ArrayList<>();

    private ArrayList<Destino> listaDestinosMasReservados = new ArrayList<>();

    private final String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    private Properties prop;
    private FileInputStream input;

    @FXML
    void generarDestinosReservados(ActionEvent event) {
        generarDestinosMasReservados();
    }

    @FXML
    void generarMejoresGuias(ActionEvent event) {
        generarExcelGuia();
    }

    @FXML
    void generarMejoresPaquetes(ActionEvent event) {
        generarExcelPaquete();
    }

    private void generarDestinosMasReservados() {
        // Crear un nuevo libro de trabajo de Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            // Crear una hoja en el libro de trabajo
            Sheet sheet = workbook.createSheet("Destinos Mas Reservados");

            // Crear la primera fila como encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Ciudad");
            headerRow.createCell(2).setCellValue("Clima");

            // Llenar el resto de las filas con datos de GuiaTuristico
            for (int i = 0; i < listaDestinosMasReservados.size(); i++) {
                Destino destino = listaDestinosMasReservados.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(destino.getNombre());
                row.createCell(1).setCellValue(destino.getCiudad());
                row.createCell(2).setCellValue(destino.getClima().toString());

            }

            // Mostrar el cuadro de diálogo de guardado de archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Guardar el libro de trabajo en el archivo seleccionado
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarExcelGuia() {
        // Crear un nuevo libro de trabajo de Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            // Crear una hoja en el libro de trabajo
            Sheet sheet = workbook.createSheet("Guías Turísticos");

            // Crear la primera fila como encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Identificacion");
            headerRow.createCell(2).setCellValue("Experiencia");
            headerRow.createCell(3).setCellValue("Idiomas");
            headerRow.createCell(4).setCellValue("Calificacion");

            // Llenar el resto de las filas con datos de GuiaTuristico
            for (int i = 0; i < listaOrdenadaCalificacionGuias.size(); i++) {
                GuiaTuristico guia = listaOrdenadaCalificacionGuias.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(guia.getNombre());
                row.createCell(1).setCellValue(guia.getIdentificacion());
                row.createCell(2).setCellValue(guia.getExperiencia());

                Cell idiomasCell = row.createCell(3);
                idiomasCell.setCellValue(String.join(", ",guia.getListaLenguas().toString()));

                row.createCell(4).setCellValue(guia.mostrarCalificacion());
            }

            // Mostrar el cuadro de diálogo de guardado de archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Guardar el libro de trabajo en el archivo seleccionado
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarExcelPaquete() {
        // Crear un nuevo libro de trabajo de Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            // Crear una hoja en el libro de trabajo
            Sheet sheet = workbook.createSheet("Paquetes Turisticos");

            // Crear la primera fila como encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nombre");
            headerRow.createCell(1).setCellValue("Duración");
            headerRow.createCell(2).setCellValue("servicios");
            headerRow.createCell(3).setCellValue("precio");
            headerRow.createCell(4).setCellValue("cupoMaximo");
            headerRow.createCell(5).setCellValue("fecha");
            headerRow.createCell(6).setCellValue("listaDestinos");
            headerRow.createCell(7).setCellValue("calificaciones");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


            // Llenar el resto de las filas con datos de GuiaTuristico
            for (int i = 0; i < listaOrdenadaCalificacionPaquetes.size(); i++) {
                PaqueteTuristico paqueteTuristico = listaOrdenadaCalificacionPaquetes.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(paqueteTuristico.getNombre());
                row.createCell(1).setCellValue(paqueteTuristico.getDuracion());
                row.createCell(2).setCellValue(paqueteTuristico.getServicios());
                row.createCell(3).setCellValue(paqueteTuristico.getPrecio());
                row.createCell(4).setCellValue(paqueteTuristico.getCupoMaximo());
                row.createCell(5).setCellValue(paqueteTuristico.getFecha().format(formatter));

                Cell idiomasCell = row.createCell(6);
                idiomasCell.setCellValue(String.join(", ",paqueteTuristico.getListaDestinos().toString()));

                row.createCell(7).setCellValue(paqueteTuristico.mostrarCalificacionPaquete());
            }

            // Mostrar el cuadro de diálogo de guardado de archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Guardar el libro de trabajo en el archivo seleccionado
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        agenciaViajes.ordenarGuiaCalificacionMayorMenor();
        listaOrdenadaCalificacionGuias = agenciaViajes.getListaGuiasTuristicos();

        agenciaViajes.ordernarListaPaquetesMayorMenor();
        listaOrdenadaCalificacionPaquetes = agenciaViajes.getListaPaquetesTuristicos();

        listaDestinosMasReservados = agenciaViajes.ordenarDestinosMasReservados();
    }
}
