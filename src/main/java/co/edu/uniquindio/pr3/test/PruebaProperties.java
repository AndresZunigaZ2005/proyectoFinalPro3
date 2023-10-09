package co.edu.uniquindio.pr3.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PruebaProperties{

    public static void main(String[] args) {
        Properties properties =  new Properties();
        InputStream stream = null;
        try{
            stream = new FileInputStream("config/textos_en.properties");

            properties.load(stream);

            System.out.println(properties.getProperty("titulo"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
