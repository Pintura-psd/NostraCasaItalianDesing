package ncid.fra.nostracasa.service;

import ncid.fra.nostracasa.dto.MessagesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactEmail(MessagesDTO dto) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("elfer.usu@gmail.com");

        message.setSubject("Nuevo mensaje de contacto: " + dto.getSubject());

        message.setText(
                "Nombre: " + dto.getName() + "\n" +
                        "Email: " + dto.getEmail() + "\n" +
                        "Teléfono: " + dto.getPhone() + "\n\n" +
                        "Mensaje:\n" +
                        dto.getMessageText()
        );

        message.setReplyTo(dto.getEmail());

        mailSender.send(message);
    }

}
