package com.ikolay.service;

import com.ikolay.rabbitmq.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    @Value("${mail-service.company}")
    private String companyMail;


    private final JavaMailSender javaMailSender;

    public void sendMail(MailModel model) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message,true,"UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
        mailMessage.setText("   <div\n" +
                "    style=\"display: flex; justify-content: center; align-items: center; background-color: #156b8a; width: 100%;height: 100%;padding: 50px 0;text-align: center;\">\n" +
                "    <div style=\"text-align: center;background-color: #1887ab; width: 70%; max-height: 50% ;border: 1px solid black; padding: 20px;border-radius: 20px; max-width: 800px; margin: auto;\">\n" +
                "        <h2>Aramıza Hoşgeldin!</h2>\n" +
                "        <p style=\"margin-bottom: 40px;\">IKolay olarak seni aramızda görmek çok güzel! Sitemizden sonuna kadar faydalanabilmen için hesabını aktif etmen gerekli, tabi seni bu konuda da yalnız bırakmadık! Aşağıdaki butonu tıklayarak hesabını aktive edebilirsin! </p>\n" +
                "       <a href=\"http://localhost:7071/api/v1/auth/activation?token="+model.getToken()+"\" target=\"_blank\" style=\"text-decoration: none; color: white;border: 2px solid black;padding: 10px; border-radius: 10px;background-color: #4f73a1;\">AKTIVE ET!</a>\n" +
                "    </div>\n" +
                "</div>",true);
        javaMailSender.send(message);

    }

}
