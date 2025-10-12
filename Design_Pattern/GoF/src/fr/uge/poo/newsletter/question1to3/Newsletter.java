package fr.uge.poo.newsletter.question1to3;import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.evilcorp.eemailer.EEMailer;
import com.evilcorp.eemailer.EEMailer.Mail;

import fr.uge.poo.newsletter.question1to3.User.Nationality;

public class Newsletter {
	
	static class NewsletterBuilder {
		private String name;
		private Predicate<User> conditions = _ -> true;
		
		public NewsletterBuilder name(String name) {
			this.name = Objects.requireNonNull(name);
			return this;
		}
		
		public NewsletterBuilder nationality(List<Nationality> nationalities) {
			Objects.requireNonNull(nationalities);
			var copy = List.copyOf(nationalities);
			conditions = conditions.and(user -> copy.contains(user.nationality()));
			return this;
		}
		
		public NewsletterBuilder ageAbove(int age) {
			if (age < 0) {
				throw new IllegalArgumentException("age must be > 0");
			}
			conditions = conditions.and(user -> user.age() >= age);
			return this;
		}
		
		public NewsletterBuilder restriction(Predicate<User> restriction) {
			conditions = conditions.and(restriction);
			return this;
		}
		
		public Newsletter build() {
			if (name == null) {
				throw new IllegalStateException("the newsletter must have name");
			}
			return new Newsletter(this);
		}
	}

	private final EEMailer eemailer = new EEMailer();

	private final String name;
	private final Map<String, User> newsletter;
	private final Predicate<User> conditions;

	private Newsletter(String nom, Predicate<User> predicate) {
		this.name = Objects.requireNonNull(nom);
		this.newsletter = new HashMap<String, User>();
		this.conditions = Objects.requireNonNull(predicate);
	}
	
	public Newsletter(NewsletterBuilder builder) {
		this(builder.name, builder.conditions);
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
		
		User arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 18, Nationality.FRENCH);
		User youssef = new User("Youssef", "youssef@u-pem.fr", 25, Nationality.BRITISH);
		
		potter.subscribe(youssef);
		potter.subscribe(arnaud);
		potter.sendMessage("Design Pattern", "C'est pas si simple");
		
		java.subscribe(youssef);
		java.subscribe(arnaud);
		java.sendMessage("Java", "C'est toujours pas si simple");
		
		potter.unsubscribe("youssef@u-pem.fr");
		
		potter.sendMessage("Design Pattern", "On avance");
		
		
		// builder
		System.out.println();
		
		Newsletter potterFromBuild = new NewsletterBuilder()
				.name("Potter4EverFromBuilder")
				.ageAbove(18)
				.nationality(List.of(Nationality.BRITISH))
				.build();
		
		Newsletter javaFromBuild = new NewsletterBuilder()
				.name("Java4EverFromBuilder")
				.ageAbove(21)
				.nationality(List.of(Nationality.FRENCH, Nationality.BRITISH))
				.build();
		
		Newsletter whyMe = new NewsletterBuilder()
				.name("Why me!")
				.restriction(user -> user.age() % 2 == 0)
				.restriction(user -> user.email().endsWith("univ-eiffel.fr"))
				.build();
		
		potterFromBuild.subscribe(youssef);
		potterFromBuild.subscribe(arnaud);
		potterFromBuild.sendMessage("Design Pattern Builder", "It's not that simple");
		
		javaFromBuild.subscribe(youssef);
		javaFromBuild.subscribe(arnaud);
		javaFromBuild.sendMessage("Java Builder", "It is still not that simple");
		
		whyMe.subscribe(youssef);
		whyMe.subscribe(arnaud);
		whyMe.sendMessage("Something something", "Something is indeed something");
		
	}

}
