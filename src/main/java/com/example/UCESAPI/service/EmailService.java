package com.example.UCESAPI.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String FROM_EMAIL = System.getenv("EMAIL_SENDER");
    private final String API_KEY = System.getenv("SENDINBLUE_API_KEY");


    /**EJEMPLO DE EMAIL*/
/*

    public void sendEmail(String toEmail) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(API_KEY);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(FROM_EMAIL);
        sender.setName("UCES UTN");

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(toEmail);
        List<SendSmtpEmailTo> toList = List.of(to);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(toList);
        sendSmtpEmail.setSubject("Mensaje de prueba!");
        sendSmtpEmail.setHtmlContent("<html><body><p>Este es un mensaje de prueba.</p></body></html>");

        CreateSmtpEmail response = new CreateSmtpEmail();
        try {
            response = api.sendTransacEmail(sendSmtpEmail);
        } catch (ApiException e) {
            System.out.println(response.toString());
        }
    }
*/
}
