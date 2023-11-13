package co.edu.uniquindio.pr3.model;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Mail {

    private final static String RUTA_PROPIEDADES = "src/main/resources/config/textos.properties";

    public static void mail(String asunto, String cuerpo, String destino) throws IOException {
        // Configuración del servidor de correo
        Properties prop = new Properties();
        InputStream input = new FileInputStream(RUTA_PROPIEDADES);
        prop.load(input);
        String host = "smtp.office365.com";
        String port = "587";
        String username = prop.getProperty("usernameEmail");
        String password = prop.getProperty("passwordEmail");
        String imagenUrl = "https://i.ibb.co/ccX0NBc/d29b41e1-6ffb-436d-bf22-26a6f4425316.jpg";
        try {
            // Propiedades de la conexión
            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.live.com");
            props.put("mail.smtp.port", "587");

            props.put("mail.smtp.host", host);
            props.setProperty("mail.smtp.STARTTLS.enable", "true");
            props.put("mail.smtp.ssl.trust", host);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.user", username);
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.ssl.protocols" , "TLSv1.2");
            props.setProperty("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256" );

            // Crear sesión de correo electrónico
            Session session = Session.getDefaultInstance(props);

            // Cuerpo del correo con una imagen en línea
            String htmlBody ="<p style=\"text-align: center;\">"
                    + "<img src=\"" + imagenUrl + "\" width=\"300\">"
                    +"<br>"
                    +cuerpo;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            message.setContent(htmlBody, "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            message.setHeader("Content-Transfer-Encoding", "quoted-printable");
            message.setSubject(asunto);
            message.setText(htmlBody);

            // Establecer el contenido del mensaje como HTML
            message.setContent(htmlBody, "text/html");

            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Mensaje enviado");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
