package fr.uge.poo.newsletter.question4;

import java.util.Objects;

import com.evilcorp.eemailer.EEMailer;

public class EEmailerAdapter implements Mailer {
	
	private final EEMailer eemailer = new EEMailer();

	@Override
	public void sendMail(String email, String subject, String content) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(subject);
		Objects.requireNonNull(content);
		eemailer.send(new EEMailer.Mail(email, subject, content));
	}

}
