package com.malex.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author malex
 */
public class MailService {

	/**
	 * Object for search of values in a file
	 */
	private PropertiesReaderAppService propertiesReaderAppService;

	/**
	 * Initial the property
	 */
	public MailService() {
		propertiesReaderAppService = new PropertiesReaderAppService();
	}

	/**
	 * Send email
	 */
	public void send(String recipient, String subjectMessage, String textMessage) {
		Properties props = new Properties();

		props.put("mail.smtp.auth", propertiesReaderAppService.getProperty("mail.SMTP_AUTH"));
		props.put("mail.smtp.starttls.enable", propertiesReaderAppService.getProperty("mail.START_TLS"));
		props.put("mail.smtp.host", propertiesReaderAppService.getProperty("mail.HOST_EMAIL"));
		props.put("mail.smtp.port", propertiesReaderAppService.getProperty("mail.SMTP_PORT"));

		// Get the Session object.
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					  protected PasswordAuthentication getPasswordAuthentication() {
						  return new PasswordAuthentication(propertiesReaderAppService.getProperty("mail.userEmail"),
									 propertiesReaderAppService.getProperty("mail.passwordEmail"));
					  }
				  });

		try {

			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Create a body of the message
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(propertiesReaderAppService.getProperty("mail.sender")));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					  InternetAddress.parse(recipient));

			// Set Subject: header field
			message.setSubject(subjectMessage);

			// Now set the actual message
			message.setText(textMessage);

			String template = readTemplate("Alex Test", "bla@bla.com");

			messageBodyPart.setContent(template, "text/html");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// Send message
			Transport.send(message);

		} catch (MessagingException ex) {
			System.out.println("UserController | send (....) | message: messageError send email | exception: " + ex);
		}
	}

	/**
	 * Templates in the page
	 */
	private final  static String TEMPLATE_EMAIL = "TEMPLATE_EMAIL";
	private final  static String TEMPLATE_NAME = "TEMPLATE_NAME";

	/**
	 * Read the template from the resource folder
	 * template url: https://beefree.io/
	 */
	private String readTemplate(String name, String email) {

		byte[] encoded = new byte[0];

		try {
			encoded = Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("template_complete_form_sign_up.html").getPath()));
		} catch (IOException ex) {
			System.out.println("UserController | readTemplate (....) | message: messageError send email | exception: " + ex);
		}

		String template = new String(encoded, Charset.defaultCharset());

		return template.replace(TEMPLATE_EMAIL, email).replace(TEMPLATE_NAME, name);
	}

}