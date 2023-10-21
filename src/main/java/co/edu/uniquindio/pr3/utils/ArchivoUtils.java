package co.edu.uniquindio.pr3.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchivoUtils {

    /**
     * Serializa un objeto en disco
     * @param ruta Ruta del archivo donde se va a serializar el objeto
     * @param objeto Objeto a serializar
     * @throws IOException
     */
    public static void serializarObjeto(String ruta, Object objeto) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(ruta));
        os.writeObject(objeto);
        os.close();
    }

    /**
     * Deserializa un objeto que está guardado en disco
     * @param ruta Ruta del archivo a deserializar
     * @return Objeto deserializado
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Object deserializarObjeto(String ruta) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = is.readObject();
        is.close();

        return objeto;
    }

    public static ArrayList<String> leerArchivoScanner(String ruta) throws IOException{

        ArrayList<String> lista = new ArrayList<>();
        Scanner sc = new Scanner(new File(ruta));

        while(sc.hasNextLine()) {
            lista.add(sc.nextLine());
        }

        sc.close();

        return lista;
    }
}
