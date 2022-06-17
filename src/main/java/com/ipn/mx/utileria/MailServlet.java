package com.ipn.mx.utileria;


//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// [END simple_includes]

// [START multipart_includes]
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
// [END multipart_includes]


@SuppressWarnings("serial")
public class MailServlet{

  

  public void sendSimpleMail() {
    // [START simple_example]
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    try {
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("apenaa1600@alumno.ipn.mx", "Hisenberg-22182020;"));
      msg.addRecipient(Message.RecipientType.TO,
                       new InternetAddress("gabinas2011@gmail.com", "Mr. User"));
      msg.setSubject("Your Example.com account has been activated");
      msg.setText("This is a test");
      Transport.send(msg);
    } catch (AddressException e) {
      // ...
    } catch (MessagingException e) {
      // ...
    } catch (UnsupportedEncodingException e) {
      // ...
    }
    // [END simple_example]
  }

  private void sendMultipartMail() throws UnsupportedEncodingException, MessagingException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    String msgBody = "...";

    try {
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("gabidiazroman@gmail.com", "El ADMIN bro"));
      msg.addRecipient(Message.RecipientType.TO,
                       new InternetAddress("gabinas2011@gmail.com", "Mr. User"));
      msg.setSubject("Your Example.com account has been activated");
      msg.setText(msgBody);

      // [START multipart_example]
      String htmlBody = "";          // ...
      byte[] attachmentData = null;  // ...
      Multipart mp = new MimeMultipart();

      MimeBodyPart htmlPart = new MimeBodyPart();
      htmlPart.setContent(htmlBody, "text/html");
      mp.addBodyPart(htmlPart);

      MimeBodyPart attachment = new MimeBodyPart();
      InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
      attachment.setFileName("manual.pdf");
      attachment.setContent(attachmentDataStream, "application/pdf");
      mp.addBodyPart(attachment);

      msg.setContent(mp);
      // [END multipart_example]

      Transport.send(msg);

    }catch (AddressException e) {
      // ...
    }
      // ...
      // ...

  }
  
    public static void main(String[] args) {
        MailServlet ms= new MailServlet();
        ms.sendSimpleMail();
    }
}
