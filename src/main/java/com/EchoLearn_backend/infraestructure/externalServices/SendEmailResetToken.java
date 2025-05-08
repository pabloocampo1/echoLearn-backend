package com.EchoLearn_backend.infraestructure.externalServices;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

@Service
public class SendEmailResetToken {
    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendHtmlEmail( Integer token, String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "Recuperación de contraseña - GymManager";


            String htmlBody = """
<html>
  <body style="margin:0; padding:0; background-color:#f4f4f4; font-family: Arial, sans-serif;">
    <div style="max-width:600px; margin:0 auto; background-color:white; padding:40px; border-radius:10px; box-shadow:0 4px 8px rgba(0,0,0,0.1);">
      <h2 style="color:#333; text-align:center;">🔐 Recuperación de Contraseña</h2>
      <p style="font-size:16px; color:#555; text-align:center;">
        Hola <strong> 
        """ + to + """ 
        </strong>,<br><br>
        Usa el siguiente código para recuperar tu contraseña. Este código expirará en <strong>5 minutos</strong>:
      </p>
      <div style="text-align:center; margin:30px 0;">
        <span style="display:inline-block; font-size:32px; letter-spacing:12px; background-color:#eee; padding:15px 30px; border-radius:10px; font-weight:bold; color:#333;">
          """ + token + """
        </span>
      </div>
      <p style="font-size:14px; color:#999; text-align:center;">
        Si tú no solicitaste este código, puedes ignorar este mensaje sin problemas.
      </p>
    </div>
    <div style="text-align:center; font-size:12px; color:#bbb; margin-top:20px;">
      EchoLearn © 2025. Todos los derechos reservados.
    </div>
  </body>
</html>
""";



            helper.setFrom("gymmman3@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            System.out.println("se envio a: " + to);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }
}
