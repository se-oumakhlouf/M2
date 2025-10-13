package fr.uge.poo.newsletter.question5;

import java.util.List;
import java.util.Objects;

import com.goodcorp.gmailer.GMailer;

public class GMailerAdapter implements Mailer {

	private final GMailer gmailer = new GMailer();
	
	@Override
	public void sendMail(String email, String subject, String content) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(subject);
		Objects.requireNonNull(content);
		gmailer.send(email, new GMailer.Mail(subject, content));
	}
	
	@Override
	public void sendMailAll(List<String> emails, String subject, String content) {
		Objects.requireNonNull(emails);
		Objects.requireNonNull(subject);
		Objects.requireNonNull(content);
		gmailer.sendBulk(emails, new GMailer.Mail(subject, content));
	}
	
}
