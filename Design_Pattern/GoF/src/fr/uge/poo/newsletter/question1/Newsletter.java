package fr.uge.poo.newsletter.question1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.evilcorp.eemailer.EEMailer;
import com.evilcorp.eemailer.EEMailer.Mail;

import fr.uge.poo.newsletter.question1.User.Nationality;

public class Newsletter {
	
	static class NewsletterBuilder {
		private String name;
		private List<Nationality> nationality = new ArrayList<>();
		
		public NewsletterBuilder name(String name) {
			this.name = Objects.requireNonNull(name);
			return this;
		}
		
		public NewsletterBuilder nationality(Nationality nationality) {
			return this;
		}
		
		public NewsletterBuilder restriction(Predicate<User> restriction) {
			// ajouter conditions
			return this;
		}
	}

	private final EEMailer eemailer = new EEMailer();

	private final String name;
	private final Map<String, User> newsletter;
	private final Predicate<User> conditions;

	public Newsletter(String nom, Predicate<User> predicate) {
		this.name = Objects.requireNonNull(nom);
		this.newsletter = new HashMap<String, User>();
		this.conditions = Objects.requireNonNull(predicate);
	}

	public boolean subscribe(User user) {
		Objects.requireNonNull(user);
		return conditions.test(user) && newsletter.putIfAbsent(user.email(), user) == null;
	}

	public boolean unsubscribe(String email) {
		Objects.requireNonNull(email);
		return newsletter.remove(email) == null;
	}

	public void sendMessage(String recipient, String subject, String content) {
		Mail mail = new EEMailer.Mail(recipient, "[" + name + "]" + " " + subject, content);
		eemailer.send(mail);
	}

	public void sendMessage(String title, String content) {
		Objects.requireNonNull(title);
		Objects.requireNonNull(content);
		for (String email : newsletter.keySet()) {
			sendMessage(email, title, content);
		}
	}

	public static void main(String[] args) {
		Newsletter potter = new Newsletter("Potter4ever",
				user -> user.age() >= 18 && user.nationality() == Nationality.BRITISH);

		Newsletter java = new Newsletter("Java4ever", user -> user.age() >= 21 && (user.nationality() == Nationality.FRENCH || user.nationality() == Nationality.BRITISH));
		
		User arnaud = new User("Arnaud", "arnaud.carayol@u-pem.fr", 16, Nationality.FRENCH);
		User youssef = new User("Youssef", "youssef@u-pem.fr", 25, Nationality.BRITISH);
		
		potter.subscribe(youssef);
		potter.subscribe(arnaud);
		potter.sendMessage("Design Pattern", "C'est pas si simple");
		
		java.subscribe(youssef);
		java.subscribe(arnaud);
		java.sendMessage("Java", "C'est toujours pas si simple");
		
		potter.unsubscribe("youssef@u-pem.fr");
		
		potter.sendMessage("Design Pattern", "On avance");
	}

}
