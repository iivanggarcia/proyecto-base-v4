package com.ipn.mx.utileria;

/**
 *
 * @author L450
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utileria {

    public void enviarMail(String correoDestinatario, String asunto, String textoCorreo) {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");

            // Inicializar la Sesion  la sesion
            Session session = Session.getDefaultInstance(props);
            // el mensaje
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correoDestinatario)
            );
            message.addRecipient(
                    Message.RecipientType.BCC,
                    new InternetAddress(correoDestinatario)
            );
            //CC A quien s ele envia una copia 
            //BCC A quien s ele envia una copia Oculta
            message.setSubject(asunto);
            message.setText(textoCorreo);
            // envio MEnsaje.
            Transport trasporte = session.getTransport("smtp");
            trasporte.connect("albertopeagarcia@hotmail.com","duque67");
            trasporte.sendMessage(message, message.getAllRecipients());
            // Cierre.
            trasporte.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utileria u = new Utileria();
        u.enviarMail("gabinas2011@gmail.com", "Hola", "hola, buenos días");
    }
}