package me.nathancole.api.email;


import me.nathancole.api.user.UserModel;
import me.nathancole.api.user.UserVerifyEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {

    static final String FROM = "no-reply@nighthawkstudios.dev";
    static final String FROMNAME = "The Social Hawk";

    static final String TO = "me@nonevale.me";

    static final String SMTP_USERNAME = "SMTP_Injection";

    static final String SMTP_PASSWORD = "c4aff8f9055eb9c458f69e73ad56023ccb04c0f4";

    static final String CONFIGSET = "ConfigSet";

    static final String HOST = "smtp.sparkpostmail.com";

    static final int PORT = 587;

    static final String SUBJECT = "The Social Hawk Test Email";

    static final String BODY = String.join(
            System.getProperty("line.separator"),
            "<html>\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"font-family: Arial; font-size: 12px;\">\n" +
                    "<div>\n" +
                    "    <p>\n" +
                    "        You have requested a password reset, please enter the code below to reset your password.\n" +
                    "    </p>\n" +
                    "    <p>\n" +
                    "        Please ignore this email if you did not request a password change.\n" +
                    "    </p>\n" +
                    "\n" +
                    "    <p>\n" +
                    "        <p>\n" +
                    "            Use this code to reset your password.\n" +
                    "        </p>\n" +
                    "    </p>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>"
    );

    public static void send() throws Exception {
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY, "text/html");

        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        Transport transport = session.getTransport();

        try
        {
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
        }
        catch (Exception ex) {
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            transport.close();
        }
    }

    public static void sendEmail(EmailType type, UserModel userModel) throws Exception {

        String subject = "", body = "";
        switch (type) {
            case REGISTRATION:
                UserVerifyEmail.createVerifyLink(userModel);
                subject = "Registration - Hawkeye";
                body = String.join(System.getProperty("line.separator"),
                        "<html>" +
                                "" +
                                "<body style=\"font-family: Arial; font-size: 12px;\">" +
                                "<div>" +
                                "    <p>" +
                                "        Thank you for taking your time to register for our app, please follow the link below to verify your email address.  This verification link will expire in 30 minutes." +
                                "    </p>" +
                                "    <p>" +
                                "        If you were not the one that registered for this account please contact us at support@hawkeyestudios.dev to have this account disabled or removed." +
                                "    </p>" +
                                "" +
                                "    <p>" +
                                "        <a href=\"" + UserVerifyEmail.getVerificationLink(userModel) + "\'>" +
                                "            Follow this link to verify your email address.\n" +
                                "        </a>" +
                                "    </p>" +
                                "</div>" +
                                "</body>" +
                                "</html>");
                break;
            case FORGOT_PASSWORD:
                subject = "Forgot Password - Hawkeye";
                body = String.join(System.getProperty("line.separator"),
                        "<html>" +
                                "" +
                                "<body style=\"font-family: Arial; font-size: 12px;\">" +
                                "<div>" +
                                "    <p>" +
                                "        You have requested a password reset, please enter the code below to reset your password.  This code will expire in 10 minutes." +
                                "    </p>" +
                                "    <p>" +
                                "        If you did not request a password reset, then feel free to ignore this email or contact us at support@hawkeyestudios.dev." +
                                "    </p>" +
                                "" +
                                "    <p>" +
                                "        <h1>" +
                                "            " + userModel.getForgotPasswordCode() + "" +
                                "        </h1>" +
                                "        <p>" +
                                "            Use this code to reset your password.\n" +
                                "        </p>" +
                                "    </p>" +
                                "</div>" +
                                "</body>" +
                                "</html>");
                break;
            case TEST:
                subject = "Test Email - Hawkeye";
                body = String.join(System.getProperty("line.separator"),
                        "<h1>Registration Email</h1>",
                        "<p>UwU this is test email from Hawkeye, the social media app, I'm sure you've heard about it. uwu</p>");
                break;
        }


        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@nighthawkstudios.dev", "Hawkeye"));
        System.out.println(userModel.getEmail());
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userModel.getEmail()));
        message.setSubject(subject);
        message.setContent(body, "text/html");

        Transport transport = session.getTransport();

        try
        {
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (Exception ex) {
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            transport.close();
        }
    }
}
