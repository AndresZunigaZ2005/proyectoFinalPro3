package co.edu.uniquindio.pr3.model;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class Mail {

    public static String mail(String asunto, String introduccionCuerpo, String finalCuerpo, String destino) throws Exception {
        // Configuración del servidor de correo
        String host = "smtp.office365.com";
        String port = "587";
        String username = "LGZConcessionaire@hotmail.com";
        String password = "RobinsonMiAmor1";
        String imagenUrl = "https://i.ibb.co/cF2Vp5L/Logo-concesionario.png";
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

            // Generar un código aleatorio de 5 letras
            String codigo = generarCodigoAleatorio();
            String codigoAux = codigo;

            // Cuerpo del correo con una imagen en línea y el código de verificación
            String htmlBody ="<p style=\"text-align: center;\">"
                    + "<img src=\"" + imagenUrl + "\" width=\"300\">"
                    + "<h2>Estimado Usuario,</h2>"
                    + introduccionCuerpo+ generarContenidoHTML(codigo) + "</p>"
                    + finalCuerpo
                    + "<p>Si necesita asistencia adicional o tiene alguna pregunta, no dude en contactarnos a trav&eacutes de LGZConcessionaire@hotmail.com</p>"
                    + "<p>Atentamente,</p>"
                    + "<p>Equipo Londo&ntildeo, Garc&iacutea, Z&uacute&ntildeiga</p>"
                    + "<p>LGZConcessionaire</p>";
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
            return codigoAux;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    // Generar un código aleatorio de letras
    private static String generarCodigoAleatorio() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }

        return codigo.toString();
    }

    private static String generarContenidoHTML(String codigo) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div style=\"display: flex;\">");

        for (int i = 0; i < codigo.length(); i++) {
            htmlBuilder.append("<div style=\"border: 1px solid black; width: 40px; height: 40px; text-align: center; font-size: 20px; margin-right: 10px;\">");
            htmlBuilder.append(codigo.charAt(i));
            htmlBuilder.append("</div>");
        }

        htmlBuilder.append("</div>");

        return htmlBuilder.toString();
    }
}
