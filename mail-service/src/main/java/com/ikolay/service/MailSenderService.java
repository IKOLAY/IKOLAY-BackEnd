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

    @Value("${mail-service.url}")
    private String urlAdress;


    private final JavaMailSender javaMailSender;

    public void sendMail(MailModel model) throws MessagingException {

        switch (model.getRole()) {
            case MANAGER:
                mailToManager(model);
                break;
            case VISITOR:
                mailToVisitor(model);
                break;
            case EMPLOYEE:
                mailToEmployee(model);
                break;
            default:
                break;

        }

    }

    private void mailToVisitor(MailModel model) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
        mailMessage.setText("   <div\n" +
                "    style=\"display: flex; justify-content: center; align-items: center; background-color: #003C6B; width: 100%;height: 100%;padding: 50px 0;text-align: center;\">\n" +
                "    <div style=\"text-align: center;background-color: #5CE1E6; width: 70%; max-height: 50% ;border: 1px solid black; padding: 20px;border-radius: 20px; max-width: 800px; margin: auto;\">\n" +
                "        <h2>Aramıza Hoşgeldin!</h2>\n" +
                "        <p style=\"margin-bottom: 40px;\">IKolay olarak seni aramızda görmek çok güzel! Sitemizden sonuna kadar faydalanabilmen için hesabını aktif etmen gerekli, tabi seni bu konuda da yalnız bırakmadık! Aşağıdaki butonu tıklayarak hesabını aktive edebilirsin! </p>\n" +
                "       <a href=\""+ urlAdress + model.getToken() + "\" target=\"_blank\" style=\"text-decoration: none; color: white;border: 2px solid black;padding: 10px; border-radius: 10px;background-color: #4f73a1;\">AKTIVE ET!</a>\n" +
                "    </div>\n" +
                "</div>", true);
        javaMailSender.send(message);
    }

    private void mailToEmployee(MailModel model) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
        mailMessage.setText("   <div\n" +
                "        style=\"display: flex; justify-content: center; align-items: center; background-color: #003C6B; width: 100%;height: 100%;padding: 50px 0;text-align: center;\">\n" +
                "        <div\n" +
                "            style=\"text-align: center;background-color: #5CE1E6; width: 70%; max-height: 50% ;border: 1px solid black; padding: 20px;border-radius: 20px; max-width: 800px; margin: auto;\">\n" +
                "            <h2>Aramıza Hoşgeldin!</h2>\n" +
                "            <p style=\"margin-bottom: 40px;\">IKolay olarak seni aramızda görmek çok güzel! Şirket yöneticinin verdiği\n" +
                "                bilgilerle oluşturduğumuz giriş için kullanman gereken şifre ve email adresini alt kısımda bulabilirsin!\n" +
                "                Senin için hazırlamış olduğumuz mail adresi ile giriş yaptıktan sonra şifreni değiştirmeyi unutma! </p>\n" +
                "            <h3>Giriş bilgilerin: </h3>\n" +
                "            <table border=\"1\" style=\"margin: auto; min-width: 200px;\">\n" +
                "                <tr>\n" +
                "                    <th>\n" +
                "                        E-mail:\n" +
                "                    </th>\n" +
                "                    <th>\n" +
                "                        " + model.getCompanyMail() + "\n" +
                "                    </th>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <th>\n" +
                "                        Şifre:\n" +
                "                    </th>\n" +
                "                    <th>\n" +
                "                        " + model.getPassword() + "\n" +
                "                    </th>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>", true);
        javaMailSender.send(message);
    }

    private void mailToManager(MailModel model) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());

        if (model.getIsAccepted()) {
            mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
            mailMessage.setText("   <div\n" +
                    "        style=\"display: flex; justify-content: center; align-items: center; background-color: #003C6B; width: 100%;height: 100%;padding: 50px 0;text-align: center;\">\n" +
                    "        <div\n" +
                    "            style=\"text-align: center;background-color: #5CE1E6; width: 70%; max-height: 50% ;border: 1px solid black; padding: 20px;border-radius: 20px; max-width: 800px; margin: auto;\">\n" +
                    "            <h2>Aramıza Hoşgeldin!</h2>\n" +
                    "            <p style=\"margin-bottom: 40px;\">Yapmış olduğunuz başvurunun olumlu olarak sonuçlandığını size bildirmekten mutluluk duyarız! IKolay olarak sizi aramızda görmek çok güzel! Hesabınıza <a href=\"http://ikolay.great-site.net/login\">giriş</a> yapıp, sizler için hazırlamış olduğumuz sistemden faydalanabilirsiniz!</p>\n" +
                    "        \n" +
                    "        </div>\n" +
                    "    </div>", true);

        } else {
            mailMessage.setSubject("IKolay - Malesef Kaydınızı Onaylayamıyoruz!");
            mailMessage.setText("      <div\n" +
                    "        style=\"display: flex; justify-content: center; align-items: center; background-color: #003C6B; width: 100%;height: 100%;padding: 50px 0;text-align: center;\">\n" +
                    "        <div\n" +
                    "            style=\"text-align: center;background-color: #5CE1E6; width: 70%; max-height: 50% ;border: 1px solid black; padding: 20px;border-radius: 20px; max-width: 800px; margin: auto;\">\n" +
                    "            <h2>Hay aksi!</h2>\n" +
                    "            <p style=\"margin-bottom: 40px;\">Yapmış olduğunuz başvurunun olumsuz olarak sonuçlandığını size bildirdiğimiz için üzgünüz! Başvurunuzun reddedilme sebebi aşağıda açıklanmıştır. Eksik bilgi ya da hatalı bilgi girişi sebebiyle red almışsanız bilgilerinizi güncelledikten sonra sizleri aramızda görmekten mutluluk duyacağız.</p>\n" +
                    "            <h3>İşte <span style=\"color: red;\">red</span> sebebiniz:</h3>\n" +
                    "            <p> " + model.getContent() + " </p>\n" +
                    "        \n" +
                    "        </div>\n" +
                    "    </div>", true);

        }
        javaMailSender.send(message);
    }

}
