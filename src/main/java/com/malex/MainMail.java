package com.malex;

import com.malex.service.MailService;

/**
 * Create a letter and send it to the e-mail
 * Service: www.mochahost.com
 *
 * @author malex
 */
public class MainMail {

	public static void main(String[] args) {

		String email = "alexey.m@implemica.com";
		String title = "Description";
		String text = "Message";

		MailService service = new MailService();
		service.send(email, title, text);

	}

}
