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
        mailMessage.setText("<!DOCTYPE html\n" +
                "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>İKolay'a Hoşgeldiniz</title>\n" +
                "    <!-- GOOGLE FONTS -->\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link\n" +
                "        href=\"https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\"\n" +
                "        rel=\"stylesheet\">\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Force Hotmail to display emails at full width */\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        /* Force Hotmail to display normal line spacing.  More on that: http://www.emailonacid.com/forum/viewthread/43/ */\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "            line-height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        /* Take care of image borders and formatting */\n" +
                "        img {\n" +
                "            max-width: 600px;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: block;\n" +
                "        }\n" +
                "\n" +
                "        a img {\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        #outlook a {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .ReadMsgBody {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .backgroundTable {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 0;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table td {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass * {\n" +
                "            line-height: 115%;\n" +
                "        }\n" +
                "\n" +
                "        /* General styling */\n" +
                "        td {\n" +
                "            font-family: 'Rubik', sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            -webkit-text-size-adjust: none;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            font-weight: 400;\n" +
                "            font-size: 18px;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #4baad4;\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "\n" +
                "        .desktop-hide {\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        .hero-bg {\n" +
                "            background: -webkit-linear-gradient(90deg, #2991bf 0%, #7ecaec 100%);\n" +
                "            background-color: #4baad4;\n" +
                "        }\n" +
                "\n" +
                "        .force-full-width {\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .body-padding {\n" +
                "            padding: 0 75px;\n" +
                "        }\n" +
                "\n" +
                "        .force-width-80 {\n" +
                "            width: 80% !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @media screen {\n" +
                "\n" +
                "            /* Thanks Outlook 2013! http://goo.gl/XLxpyl */\n" +
                "            * {\n" +
                "                font-family: 'Arial', 'sans-serif' !important;\n" +
                "            }\n" +
                "\n" +
                "            .w280 {\n" +
                "                width: 280px !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                "        /* Mobile styles */\n" +
                "        @media only screen and (max-width: 480px) {\n" +
                "            table[class*=\"w320\"] {\n" +
                "                width: 320px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"w320\"] {\n" +
                "                width: 280px !important;\n" +
                "                padding-left: 20px !important;\n" +
                "                padding-right: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class*=\"w320\"] {\n" +
                "                height: 40px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-spacing\"] {\n" +
                "                padding-top: 10px !important;\n" +
                "                padding-bottom: 10px !important;\n" +
                "            }\n" +
                "\n" +
                "            *[class*=\"mobile-hide\"] {\n" +
                "                display: none !important;\n" +
                "            }\n" +
                "\n" +
                "            .desktop-hide {\n" +
                "                display: block !important;\n" +
                "            }\n" +
                "\n" +
                "            *[class*=\"mobile-br\"] {\n" +
                "                font-size: 12px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-w20\"] {\n" +
                "                width: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class*=\"mobile-w20\"] {\n" +
                "                width: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-center\"] {\n" +
                "                text-align: center !important;\n" +
                "            }\n" +
                "\n" +
                "            table[class*=\"w100p\"] {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"activate-now\"] {\n" +
                "                padding-right: 0 !important;\n" +
                "                padding-top: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-resize\"] {\n" +
                "                font-size: 22px !important;\n" +
                "                padding-left: 15px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-hide\"] {\n" +
                "                display: none;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body offset=\"0\" class=\"body externalClass\"\n" +
                "    style=\"padding:0; margin:0; display:block; -webkit-text-size-adjust:none; background:linear-gradient(to bottom, #04182D, #003C6B);\">\n" +
                "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n" +
                "        <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"100%\">\n" +
                "                <center>\n" +
                "                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" valign=\"top\">\n" +
                "                                <table class=\"mobile-hide\" style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <tr>\n" +
                "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"hero-bg\">\n" +
                "                                            <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\"\n" +
                "                                                        class=\"desktop-hide\">&nbsp;</td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td\n" +
                "                                                        style=\"font-size:40px; font-weight: 400; color: #ffffff; text-align:center;\">\n" +
                "                                                        <div style=\"margin: 0 auto; margin-top: 40px;\">\n" +
                "                                                        İKolay Ailesine Hoş Geldiniz!\n" +
                "                                                        <br>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td\n" +
                "                                                        style=\"font-size:24px; text-align:center; padding: 0 75px; color:#ffffff;\">\n" +
                "                                                        Sizi aramızda görmek çok güzel!\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"50\" style=\"font-size: 50px; line-height: 50px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" class=\"force-full-width\" bgcolor=\"#ffffff\">\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"background-color:#ffffff;\">\n" +
                "                                            <br>\n" +
                "                                            <center>\n" +
                "                                                <table style=\"margin: 0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                    class=\"force-width-80\">\n" +
                "                                                    <tr>\n" +
                "\n" +
                "\n" +
                "                                                        <td align=\"left\"\n" +
                "                                                            style=\"color: #95a5a6; font-size: 15px; font-weight: 500; line-height: 24px;\">\n" +
                "                                                            <div style=\"line-height: 24px\">\n" +
                "                                                                <p>Sitemizden sonuna kadar faydalanabilmeniz için hesabınızı aktif etmeniz gerekli, tabii sizi bu konuda da yalnız bırakmadık! Aşağıdaki butonu tıklayarak hesabınızı aktive edebilirsiniz! </p>\n" +
                "                                                            </div>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "\n" +
                "                                                </table>\n" +
                "                                            </center>\n" +
                "                                            <table style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"10\" width=\"100%\">\n" +
                "                                                <tr>\n" +
                "                                                    <td style=\"text-align:center; margin:0 auto;\">\n" +
                "                                                        <br>\n" +
                "                                                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            align=\"center\">\n" +
                "                                                            <tr>\n" +
                "                                                                <td>\n" +
                "                                                                    <div>\n" +
                "\n" +
                "                                                                        <a href=\""+ urlAdress + model.getToken() +"\"  style=\"background-color:#80c97a;border:1px solid #80c97a;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:14px;line-height:44px;text-align:center;text-decoration:none;width:150px;-webkit-text-size-adjust:none;mso-hide:all;\"> Hesabımı Aktifleştir</a>\n" +
                "                                                                    </div>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                        <br>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                            <table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                bgcolor=\"ffffff\" class=\"force-full-width\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\">\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\">\n" +
                "                                                        <img src=\"https://s3.amazonaws.com/appcues-email-assets/images/wave-inverse.png\"\n" +
                "                                                            style=\"display: block; width: 100%\" width=\"100%\" border=\"0\"\n" +
                "                                                            alt=\"\" />\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\">\n" +
                "                                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                bgcolor=\"4baad4\">\n" +
                "                                                <tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <td align=\"center\">\n" +
                "                                                    <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\"\n" +
                "                                                        cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\">\n" +
                "                                                                <a href=\"mailto:\n" +
                "                                                                ikolayhrmanagement@gmail.com\n" +
                "                                                                \" border=\"0\"\n" +
                "                                                                    style=\"text-decoration: none !important; font-size: 18px; font-family: arial, sans-serif; border-style:none; color:#fff;\">Yardıma\n" +
                "                                                                    mı ihtiyacınız var?\n" +
                "                                                                    <em>Bize ulaşın!</em></a>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td height=\"100\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "    </center>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>", true);
        javaMailSender.send(message);
    }

    private void mailToEmployee(MailModel model) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
        mailMessage.setText("<!DOCTYPE html\n" +
                "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>İKolay'a Hoşgeldiniz</title>\n" +
                "    <!-- GOOGLE FONTS -->\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "    <link\n" +
                "        href=\"https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\"\n" +
                "        rel=\"stylesheet\">\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Force Hotmail to display emails at full width */\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        /* Force Hotmail to display normal line spacing.  More on that: http://www.emailonacid.com/forum/viewthread/43/ */\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "            line-height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        /* Take care of image borders and formatting */\n" +
                "        img {\n" +
                "            max-width: 600px;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: block;\n" +
                "        }\n" +
                "\n" +
                "        a img {\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        #outlook a {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .ReadMsgBody {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .backgroundTable {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 0;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table td {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass * {\n" +
                "            line-height: 115%;\n" +
                "        }\n" +
                "\n" +
                "        /* General styling */\n" +
                "        td {\n" +
                "            font-family: 'Rubik', sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            -webkit-text-size-adjust: none;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            font-weight: 400;\n" +
                "            font-size: 18px;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #4baad4;\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "\n" +
                "        .desktop-hide {\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        .hero-bg {\n" +
                "            background: -webkit-linear-gradient(90deg, #2991bf 0%, #7ecaec 100%);\n" +
                "            background-color: #4baad4;\n" +
                "        }\n" +
                "\n" +
                "        .force-full-width {\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        .body-padding {\n" +
                "            padding: 0 75px;\n" +
                "        }\n" +
                "\n" +
                "        .force-width-80 {\n" +
                "            width: 80% !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @media screen {\n" +
                "\n" +
                "            /* Thanks Outlook 2013! http://goo.gl/XLxpyl */\n" +
                "            * {\n" +
                "                font-family: 'Arial', 'sans-serif' !important;\n" +
                "            }\n" +
                "\n" +
                "            .w280 {\n" +
                "                width: 280px !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                "        /* Mobile styles */\n" +
                "        @media only screen and (max-width: 480px) {\n" +
                "            table[class*=\"w320\"] {\n" +
                "                width: 320px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"w320\"] {\n" +
                "                width: 280px !important;\n" +
                "                padding-left: 20px !important;\n" +
                "                padding-right: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class*=\"w320\"] {\n" +
                "                height: 40px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-spacing\"] {\n" +
                "                padding-top: 10px !important;\n" +
                "                padding-bottom: 10px !important;\n" +
                "            }\n" +
                "\n" +
                "            *[class*=\"mobile-hide\"] {\n" +
                "                display: none !important;\n" +
                "            }\n" +
                "\n" +
                "            .desktop-hide {\n" +
                "                display: block !important;\n" +
                "            }\n" +
                "\n" +
                "            *[class*=\"mobile-br\"] {\n" +
                "                font-size: 12px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-w20\"] {\n" +
                "                width: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class*=\"mobile-w20\"] {\n" +
                "                width: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-center\"] {\n" +
                "                text-align: center !important;\n" +
                "            }\n" +
                "\n" +
                "            table[class*=\"w100p\"] {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"activate-now\"] {\n" +
                "                padding-right: 0 !important;\n" +
                "                padding-top: 20px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-resize\"] {\n" +
                "                font-size: 22px !important;\n" +
                "                padding-left: 15px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-hide\"] {\n" +
                "                display: none;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body offset=\"0\" class=\"body externalClass\"\n" +
                "    style=\"padding:0; margin:0; display:block; -webkit-text-size-adjust:none; background:linear-gradient(to bottom, #04182D, #003C6B);\">\n" +
                "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n" +
                "        <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"100%\">\n" +
                "                <center>\n" +
                "                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"center\" valign=\"top\">\n" +
                "                                <table class=\"mobile-hide\" style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <tr>\n" +
                "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"hero-bg\">\n" +
                "                                            <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\"\n" +
                "                                                        class=\"desktop-hide\">&nbsp;</td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td\n" +
                "                                                        style=\"font-size:40px; font-weight: 400; color: #ffffff; text-align:center;\">\n" +
                "                                                        <div style=\"margin: 0 auto; margin-top: 40px;\">\n" +
                "                                                        İKolay Ailesine Hoş Geldiniz!\n" +
                "                                                        <br>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td\n" +
                "                                                        style=\"font-size:24px; text-align:center; padding: 0 75px; color:#ffffff;\">\n" +
                "                                                        Sizi aramızda görmek çok güzel!\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"50\" style=\"font-size: 50px; line-height: 50px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" class=\"force-full-width\" bgcolor=\"#ffffff\">\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"background-color:#ffffff;\">\n" +
                "                                            <br>\n" +
                "                                            <center>\n" +
                "                                                <table style=\"margin: 0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                    class=\"force-width-80\">\n" +
                "                                                    <tr>\n" +
                "\n" +
                "\n" +
                "                                                        <td align=\"left\"\n" +
                "                                                            style=\"color: #95a5a6; font-size: 15px; font-weight: 500; line-height: 24px;\">\n" +
                "                                                            <div style=\"line-height: 24px\">\n" +
                "                                                                <p>Şirket yöneticinin verdiği                bilgilerle oluşturduğumuz giriş için kullanman gereken şifre ve email adresini alt kısımda bulabilirsin!</p>\n" +
                "                                                            </div>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "\n" +
                "                                                </table>\n" +
                "                                                <table style=\"margin: 0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                    class=\"force-width-80\">\n" +
                "                                                    <tr>\n" +
                "\n" +
                "\n" +
                "                                                        <td align=\"left\"\n" +
                "                                                            style=\"color: #95a5a6; font-size: 15px; font-weight: 500; line-height: 24px;\">\n" +
                "                                                            <div style=\"line-height: 24px\">\n" +
                "                                                                <h3>Giriş bilgilerin:</h3>\n" +
                "                                                                <table>\n" +
                "                                                                    <tr>\n" +
                "                                                                        <th>E-posta:</th>\n" +
                "                                                                        <th>\n" +
                "                                                                            " + model.getCompanyMail() +"\n" +
                "                                                                                                </th>\n" +
                "                                                                    </tr>\n" +
                "                                                                    <tr>\n" +
                "                                                                        <th>Şifre:</th>\n" +
                "                                                                        <th>\n" +
                "                                                                            " + model.getPassword() +"\n" +
                "                                                                                               </th>\n" +
                "                                                                    </tr>\n" +
                "                                                                </table>\n" +
                "                                                            </div>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "\n" +
                "                                                </table>\n" +
                "                                            </center>\n" +
                "                                            \n" +
                "                                            <table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                bgcolor=\"ffffff\" class=\"force-full-width\">\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\">\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td align=\"center\">\n" +
                "                                                        <img src=\"https://s3.amazonaws.com/appcues-email-assets/images/wave-inverse.png\"\n" +
                "                                                            style=\"display: block; width: 100%\" width=\"100%\" border=\"0\"\n" +
                "                                                            alt=\"\" />\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                                <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"center\">\n" +
                "                                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                bgcolor=\"4baad4\">\n" +
                "                                                <tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\">&nbsp;\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <td align=\"center\">\n" +
                "                                                    <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\"\n" +
                "                                                        cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td align=\"center\">\n" +
                "                                                                <a href=\"mailto:\n" +
                "                                                                ikolayhrmanagement@gmail.com\n" +
                "                                                                \" border=\"0\"\n" +
                "                                                                    style=\"text-decoration: none !important; font-size: 18px; font-family: arial, sans-serif; border-style:none; color:#fff;\">Yardıma\n" +
                "                                                                    mı ihtiyacınız var?\n" +
                "                                                                    <em>Bize ulaşın!</em></a>\n" +
                "                                                            </td>\n" +
                "                                                        </tr>\n" +
                "                                                </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td height=\"100\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "    </center>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>", true);
        javaMailSender.send(message);
    }

    private void mailToManager(MailModel model) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(message, true, "UTF-8");
        mailMessage.setFrom(companyMail);
        mailMessage.setTo(model.getEmail());

        if (model.getIsAccepted()) {
            mailMessage.setSubject("IKolay Ailesine Hoşgeldin!");
            mailMessage.setText("<!DOCTYPE html\n" +
                    "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                    "    <title>İKolay'a Hoşgeldiniz</title>\n" +
                    "    <!-- GOOGLE FONTS -->\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                    "    <link\n" +
                    "        href=\"https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\"\n" +
                    "        rel=\"stylesheet\">\n" +
                    "    <style type=\"text/css\">\n" +
                    "        /* Force Hotmail to display emails at full width */\n" +
                    "        .ExternalClass {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* Force Hotmail to display normal line spacing.  More on that: http://www.emailonacid.com/forum/viewthread/43/ */\n" +
                    "        .ExternalClass,\n" +
                    "        .ExternalClass p,\n" +
                    "        .ExternalClass span,\n" +
                    "        .ExternalClass font,\n" +
                    "        .ExternalClass td,\n" +
                    "        .ExternalClass div {\n" +
                    "            line-height: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* Take care of image borders and formatting */\n" +
                    "        img {\n" +
                    "            max-width: 600px;\n" +
                    "            outline: none;\n" +
                    "            text-decoration: none;\n" +
                    "            -ms-interpolation-mode: bicubic;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            display: block;\n" +
                    "        }\n" +
                    "\n" +
                    "        a img {\n" +
                    "            border: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        table {\n" +
                    "            border-collapse: collapse !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        #outlook a {\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ReadMsgBody {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ExternalClass {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .backgroundTable {\n" +
                    "            margin: 0 auto;\n" +
                    "            padding: 0;\n" +
                    "            width: 100% !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        table td {\n" +
                    "            border-collapse: collapse;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ExternalClass * {\n" +
                    "            line-height: 115%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* General styling */\n" +
                    "        td {\n" +
                    "            font-family: 'Rubik', sans-serif;\n" +
                    "        }\n" +
                    "\n" +
                    "        body {\n" +
                    "            -webkit-font-smoothing: antialiased;\n" +
                    "            -webkit-text-size-adjust: none;\n" +
                    "            width: 100%;\n" +
                    "            height: 100%;\n" +
                    "            font-weight: 400;\n" +
                    "            font-size: 18px;\n" +
                    "\n" +
                    "        }\n" +
                    "\n" +
                    "        h1 {\n" +
                    "            margin: 10px 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        a {\n" +
                    "            color: #4baad4;\n" +
                    "            text-decoration: underline;\n" +
                    "        }\n" +
                    "\n" +
                    "        .desktop-hide {\n" +
                    "            display: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        .hero-bg {\n" +
                    "            background: -webkit-linear-gradient(90deg, #2991bf 0%, #7ecaec 100%);\n" +
                    "            background-color: #4baad4;\n" +
                    "        }\n" +
                    "\n" +
                    "        .force-full-width {\n" +
                    "            width: 100% !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .body-padding {\n" +
                    "            padding: 0 75px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .force-width-80 {\n" +
                    "            width: 80% !important;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "    <style type=\"text/css\" media=\"screen\">\n" +
                    "        @media screen {\n" +
                    "\n" +
                    "            /* Thanks Outlook 2013! http://goo.gl/XLxpyl */\n" +
                    "            * {\n" +
                    "                font-family: 'Arial', 'sans-serif' !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            .w280 {\n" +
                    "                width: 280px !important;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                    "        /* Mobile styles */\n" +
                    "        @media only screen and (max-width: 480px) {\n" +
                    "            table[class*=\"w320\"] {\n" +
                    "                width: 320px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"w320\"] {\n" +
                    "                width: 280px !important;\n" +
                    "                padding-left: 20px !important;\n" +
                    "                padding-right: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            img[class*=\"w320\"] {\n" +
                    "                height: 40px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-spacing\"] {\n" +
                    "                padding-top: 10px !important;\n" +
                    "                padding-bottom: 10px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            *[class*=\"mobile-hide\"] {\n" +
                    "                display: none !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            .desktop-hide {\n" +
                    "                display: block !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            *[class*=\"mobile-br\"] {\n" +
                    "                font-size: 12px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-w20\"] {\n" +
                    "                width: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            img[class*=\"mobile-w20\"] {\n" +
                    "                width: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-center\"] {\n" +
                    "                text-align: center !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            table[class*=\"w100p\"] {\n" +
                    "                width: 100% !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"activate-now\"] {\n" +
                    "                padding-right: 0 !important;\n" +
                    "                padding-top: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-resize\"] {\n" +
                    "                font-size: 22px !important;\n" +
                    "                padding-left: 15px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-hide\"] {\n" +
                    "                display: none;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body offset=\"0\" class=\"body externalClass\"\n" +
                    "    style=\"padding:0; margin:0; display:block; -webkit-text-size-adjust:none; background:linear-gradient(to bottom, #04182D, #003C6B);\">\n" +
                    "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" width=\"100%\">\n" +
                    "                <center>\n" +
                    "                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                    "                        <tr>\n" +
                    "                            <td align=\"center\" valign=\"top\">\n" +
                    "                                <table class=\"mobile-hide\" style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                    "                                    width=\"100%\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                    "                                    </tr>\n" +
                    "                                    <tr>\n" +
                    "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td class=\"hero-bg\">\n" +
                    "                                            <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\"\n" +
                    "                                                        class=\"desktop-hide\">&nbsp;</td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td\n" +
                    "                                                        style=\"font-size:40px; font-weight: 400; color: #ffffff; text-align:center;\">\n" +
                    "                                                        <div style=\"margin: 0 auto; margin-top: 40px;\"></div>\n" +
                    "                                                        İKolay Ailesine Hoş Geldiniz!\n" +
                    "                                                        <br>\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td\n" +
                    "                                                        style=\"font-size:24px; text-align:center; padding: 0 75px; color:#ffffff;\">\n" +
                    "                                                        Sizi aramızda görmek çok güzel!\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"50\" style=\"font-size: 50px; line-height: 50px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                            </table>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table cellspacing=\"0\" cellpadding=\"0\" class=\"force-full-width\" bgcolor=\"#ffffff\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td style=\"background-color:#ffffff;\">\n" +
                    "                                            <br>\n" +
                    "                                            <center>\n" +
                    "                                                <table style=\"margin: 0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                    "                                                    class=\"force-width-80\">\n" +
                    "                                                    <tr>\n" +
                    "\n" +
                    "\n" +
                    "                                                        <td align=\"left\"\n" +
                    "                                                            style=\"color: #95a5a6; font-size: 15px; font-weight: 500; line-height: 24px;\">\n" +
                    "                                                            <div style=\"line-height: 24px\">\n" +
                    "                                                                <p>Yapmış olduğunuz başvurunun olumlu olarak\n" +
                    "                                                                    sonuçlandığını size bildirmekten mutluluk duyarız!\n" +
                    "                                                                    IKolay olarak sizi aramızda görmek çok güzel!\n" +
                    "                                                                    Hesabınıza aşağıdaki buton yardımıyla giriş yapıp,\n" +
                    "                                                                    sizler için hazırlamış olduğumuz sistemden\n" +
                    "                                                                    faydalanabilirsiniz!</p>\n" +
                    "                                                            </div>\n" +
                    "                                                        </td>\n" +
                    "                                                    </tr>\n" +
                    "\n" +
                    "                                                </table>\n" +
                    "                                            </center>\n" +
                    "                                            <table style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"10\" width=\"100%\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td style=\"text-align:center; margin:0 auto;\">\n" +
                    "                                                        <br>\n" +
                    "                                                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                    "                                                            align=\"center\">\n" +
                    "                                                            <tr>\n" +
                    "                                                                <td>\n" +
                    "                                                                    <div>\n" +
                    "\n" +
                    "                                                                        <a href=\"http://ikolay.great-site.net/login\"\n" +
                    "                                                                            style=\"background-color:#80c97a;border:1px solid #80c97a;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:18px;line-height:44px;text-align:center;text-decoration:none;width:150px;-webkit-text-size-adjust:none;mso-hide:all;\">Giriş\n" +
                    "                                                                            Yap &rarr;</a>\n" +
                    "                                                                    </div>\n" +
                    "                                                                </td>\n" +
                    "                                                            </tr>\n" +
                    "                                                        </table>\n" +
                    "                                                        <br>\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                            </table>\n" +
                    "                                            <table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                                bgcolor=\"ffffff\" class=\"force-full-width\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td align=\"center\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td align=\"center\">\n" +
                    "                                                        <img src=\"https://s3.amazonaws.com/appcues-email-assets/images/wave-inverse.png\"\n" +
                    "                                                            style=\"display: block; width: 100%\" width=\"100%\" border=\"0\"\n" +
                    "                                                            alt=\"\" />\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td align=\"center\">\n" +
                    "                                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                                bgcolor=\"4baad4\">\n" +
                    "                                                <tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <td align=\"center\">\n" +
                    "                                                    <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\"\n" +
                    "                                                        cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                                <a href=\"mailto:\n" +
                    "                                                                ikolayhrmanagement@gmail.com\n" +
                    "                                                                \" border=\"0\"\n" +
                    "                                                                    style=\"text-decoration: none !important; font-size: 18px; font-family: arial, sans-serif; border-style:none; color:#fff;\">Yardıma\n" +
                    "                                                                    mı ihtiyacınız var?\n" +
                    "                                                                    <em>Bize ulaşın!</em></a>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td height=\"100\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "    </center>\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>", true);

        } else {
            mailMessage.setSubject("IKolay - Malesef Kaydınızı Onaylayamıyoruz!");
            mailMessage.setText("<!DOCTYPE html\n" +
                    "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                    "    <title>İKolay'a Hoşgeldiniz</title>\n" +
                    "    <!-- GOOGLE FONTS -->\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                    "    <link\n" +
                    "        href=\"https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap\"\n" +
                    "        rel=\"stylesheet\">\n" +
                    "    <style type=\"text/css\">\n" +
                    "        /* Force Hotmail to display emails at full width */\n" +
                    "        .ExternalClass {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* Force Hotmail to display normal line spacing.  More on that: http://www.emailonacid.com/forum/viewthread/43/ */\n" +
                    "        .ExternalClass,\n" +
                    "        .ExternalClass p,\n" +
                    "        .ExternalClass span,\n" +
                    "        .ExternalClass font,\n" +
                    "        .ExternalClass td,\n" +
                    "        .ExternalClass div {\n" +
                    "            line-height: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* Take care of image borders and formatting */\n" +
                    "        img {\n" +
                    "            max-width: 600px;\n" +
                    "            outline: none;\n" +
                    "            text-decoration: none;\n" +
                    "            -ms-interpolation-mode: bicubic;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            display: block;\n" +
                    "        }\n" +
                    "\n" +
                    "        a img {\n" +
                    "            border: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        table {\n" +
                    "            border-collapse: collapse !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        #outlook a {\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ReadMsgBody {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ExternalClass {\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .backgroundTable {\n" +
                    "            margin: 0 auto;\n" +
                    "            padding: 0;\n" +
                    "            width: 100% !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        table td {\n" +
                    "            border-collapse: collapse;\n" +
                    "        }\n" +
                    "\n" +
                    "        .ExternalClass * {\n" +
                    "            line-height: 115%;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* General styling */\n" +
                    "        td {\n" +
                    "            font-family: 'Rubik', sans-serif;\n" +
                    "        }\n" +
                    "\n" +
                    "        body {\n" +
                    "            -webkit-font-smoothing: antialiased;\n" +
                    "            -webkit-text-size-adjust: none;\n" +
                    "            width: 100%;\n" +
                    "            height: 100%;\n" +
                    "            font-weight: 400;\n" +
                    "            font-size: 18px;\n" +
                    "\n" +
                    "        }\n" +
                    "\n" +
                    "        h1 {\n" +
                    "            margin: 10px 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        a {\n" +
                    "            color: #4baad4;\n" +
                    "            text-decoration: underline;\n" +
                    "        }\n" +
                    "\n" +
                    "        .desktop-hide {\n" +
                    "            display: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        .hero-bg {\n" +
                    "            background: -webkit-linear-gradient(90deg, #2991bf 0%, #7ecaec 100%);\n" +
                    "            background-color: #4baad4;\n" +
                    "        }\n" +
                    "\n" +
                    "        .force-full-width {\n" +
                    "            width: 100% !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .body-padding {\n" +
                    "            padding: 0 75px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .force-width-80 {\n" +
                    "            width: 80% !important;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "    <style type=\"text/css\" media=\"screen\">\n" +
                    "        @media screen {\n" +
                    "\n" +
                    "            /* Thanks Outlook 2013! http://goo.gl/XLxpyl */\n" +
                    "            * {\n" +
                    "                font-family: 'Arial', 'sans-serif' !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            .w280 {\n" +
                    "                width: 280px !important;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                    "        /* Mobile styles */\n" +
                    "        @media only screen and (max-width: 480px) {\n" +
                    "            table[class*=\"w320\"] {\n" +
                    "                width: 320px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"w320\"] {\n" +
                    "                width: 280px !important;\n" +
                    "                padding-left: 20px !important;\n" +
                    "                padding-right: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            img[class*=\"w320\"] {\n" +
                    "                height: 40px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-spacing\"] {\n" +
                    "                padding-top: 10px !important;\n" +
                    "                padding-bottom: 10px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            *[class*=\"mobile-hide\"] {\n" +
                    "                display: none !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            .desktop-hide {\n" +
                    "                display: block !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            *[class*=\"mobile-br\"] {\n" +
                    "                font-size: 12px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-w20\"] {\n" +
                    "                width: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            img[class*=\"mobile-w20\"] {\n" +
                    "                width: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-center\"] {\n" +
                    "                text-align: center !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            table[class*=\"w100p\"] {\n" +
                    "                width: 100% !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"activate-now\"] {\n" +
                    "                padding-right: 0 !important;\n" +
                    "                padding-top: 20px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-resize\"] {\n" +
                    "                font-size: 22px !important;\n" +
                    "                padding-left: 15px !important;\n" +
                    "            }\n" +
                    "\n" +
                    "            td[class*=\"mobile-hide\"] {\n" +
                    "                display: none;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body offset=\"0\" class=\"body externalClass\"\n" +
                    "    style=\"padding:0; margin:0; display:block; -webkit-text-size-adjust:none; background:linear-gradient(to bottom, #04182D, #003C6B);\">\n" +
                    "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" width=\"100%\">\n" +
                    "                <center>\n" +
                    "                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                    "                        <tr>\n" +
                    "                            <td align=\"center\" valign=\"top\">\n" +
                    "                                <table class=\"mobile-hide\" style=\"margin:0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                    "                                    width=\"100%\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                    "                                    </tr>\n" +
                    "                                    <tr>\n" +
                    "                                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td class=\"hero-bg\">\n" +
                    "                                            <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\"\n" +
                    "                                                        class=\"desktop-hide\">&nbsp;</td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td\n" +
                    "                                                        style=\"font-size:40px; font-weight: 400; color: #ffffff; text-align:center;\">\n" +
                    "                                                        <div style=\"margin: 0 auto; margin-top: 40px;\"></div>\n" +
                    "                                                        Hay Aksi!\n" +
                    "                                                        <br>\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td\n" +
                    "                                                        style=\"font-size:24px; text-align:center; padding: 0 75px; color:#ffffff;\">\n" +
                    "                                                        Kaydınızı onaylayamadık...\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"50\" style=\"font-size: 50px; line-height: 50px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                            </table>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table cellspacing=\"0\" cellpadding=\"0\" class=\"force-full-width\" bgcolor=\"#ffffff\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td style=\"background-color:#ffffff;\">\n" +
                    "                                            <br>\n" +
                    "                                            <center>\n" +
                    "                                                <table style=\"margin: 0 auto;\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                    "                                                    class=\"force-width-80\">\n" +
                    "                                                    <tr>\n" +
                    "\n" +
                    "\n" +
                    "                                                        <td align=\"left\"\n" +
                    "                                                            style=\"color: #95a5a6; font-size: 15px; font-weight: 500; line-height: 24px;\">\n" +
                    "                                                            <div style=\"line-height: 24px\">\n" +
                    "                                                                <p>Yapmış olduğunuz başvurunun olumsuz olarak sonuçlandığını size bildirdiğimiz için üzgünüz! Başvurunuzun reddedilme sebebi aşağıda açıklanmıştır. Eksik bilgi ya da hatalı bilgi girişi sebebiyle red almışsanız bilgilerinizi güncelledikten sonra sizleri aramızda görmekten mutluluk duyacağız.</p>\n" +
                    "<h3>İşte red nedeniniz:</h3>\n"+
                    "<p> " + model.getContent() + " </p>\n"+
                    "                                                                \n" +
                    "                                                            </div>\n" +
                    "                                                        </td>\n" +
                    "                                                    </tr>\n" +
                    "\n" +
                    "                                                </table>\n" +
                    "                                            </center>\n" +
                    "\n" +
                    "                                            <table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                                bgcolor=\"ffffff\" class=\"force-full-width\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td align=\"center\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td align=\"center\">\n" +
                    "                                                        <img src=\"https://s3.amazonaws.com/appcues-email-assets/images/wave-inverse.png\"\n" +
                    "                                                            style=\"display: block; width: 100%\" width=\"100%\" border=\"0\"\n" +
                    "                                                            alt=\"\" />\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td align=\"center\">\n" +
                    "                                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                                                bgcolor=\"4baad4\">\n" +
                    "                                                <tr>\n" +
                    "                                                <tr>\n" +
                    "                                                    <td height=\"45\" style=\"font-size: 45px; line-height: 45px;\">&nbsp;\n" +
                    "                                                    </td>\n" +
                    "                                                </tr>\n" +
                    "                                                <td align=\"center\">\n" +
                    "                                                    <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\"\n" +
                    "                                                        cellspacing=\"0\" bgcolor=\"4baad4\">\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td align=\"center\">\n" +
                    "                                                                <a href=\"mailto:\n" +
                    "                                                                ikolayhrmanagement@gmail.com\n" +
                    "                                                                \" border=\"0\"\n" +
                    "                                                                    style=\"text-decoration: none !important; font-size: 18px; font-family: arial, sans-serif; border-style:none; color:#fff;\">Yardıma\n" +
                    "                                                                    mı ihtiyacınız var?\n" +
                    "                                                                    <em>Bize ulaşın!</em></a>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td height=\"100\" style=\"font-size: 65px; line-height: 65px;\">&nbsp;</td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "    </center>\n" +
                    "    </td>\n" +
                    "    </tr>\n" +
                    "    </table>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>", true);

        }
        javaMailSender.send(message);
    }

}
