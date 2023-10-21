package co.edu.uniquindio.pr3.controllers;

import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

public class Propiedades {
    private static Propiedades instance;
    private SimpleObjectProperty<ResourceBundle> propiedadIdioma;
    private static final String RUTA = "config/textos";

    private Propiedades(){
        propiedadIdioma = new SimpleObjectProperty<>(ResourceBundle.getBundle(RUTA));
    }
    public static Propiedades getInstance(){
        if (instance==null) instance = new Propiedades();
        return instance;
    }
    public void setIdioma(Locale locale) {
        propiedadIdioma.setValue(ResourceBundle.getBundle(RUTA,locale));
    }
    public void addListener(Traducible traducible) {
        propiedadIdioma.addListener((observable, oldValue, newValue) -> traducible.cambiarIdioma(newValue));
    }
    public static interface Traducible {
        public void cambiarIdioma(ResourceBundle bundle);
    }

}
