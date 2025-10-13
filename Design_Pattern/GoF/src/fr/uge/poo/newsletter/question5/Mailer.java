package fr.uge.poo.newsletter.question5;

import java.util.List;

public interface Mailer {
	
	void sendMail(String email, String subject, String content);
	
	default void sendMailAll(List<String> emails, String subject, String content) {
		for (var email : emails) {
			sendMail(email, subject, content);
		}
	}

}
