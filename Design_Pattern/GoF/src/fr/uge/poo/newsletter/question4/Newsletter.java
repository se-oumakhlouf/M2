package fr.uge.poo.newsletter.question4;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.evilcorp.eemailer.EEMailer;
import com.evilcorp.eemailer.EEMailer.Mail;

import fr.uge.poo.newsletter.question4.User.Nationality;

public class Newsletter {
	
	static class NewsletterBuilder {
		private String name;
		private Predicate<User> conditions = _ -> true;
		private Supplier<Mailer> mailerSupplier = EEMAILER;
		
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
		
		public NewsletterBuilder mailer(Supplier<Mailer> mailerSupplier) {
			Objects.requireNonNull(mailerSupplier);
			this.mailerSupplier = mailerSupplier;
			return this;
		}
		
		public Newsletter build() {
			if (name == null) {
				throw new IllegalStateException("the newsletter must have name");
			}
			return new Newsletter(this);
		}
	}

	private final Mailer mailer;

	private final String name;
	private final Map<String, User> users;
	private final Predicate<User> conditions;
	
	private static final Supplier<Mailer> GMAILER = () -> new GMailerAdapter();
	private static final Supplier<Mailer> EEMAILER = () -> new EEmailerAdapter();
		
	private Newsletter(String nom, Predicate<User> predicate, Supplier<Mailer> mailerSupplier) {
		this.name = Objects.requireNonNull(nom);
		this.users = new HashMap<String, User>();
		this.conditions = Objects.requireNonNull(predicate);
		this.mailer = mailerSupplier.get();
	}
	
	public Newsletter(NewsletterBuilder builder) {
		this(builder.name, builder.conditions, builder.mailerSupplier);
	}

	public boolean subscribe(User user) {
		Objects.requireNonNull(user);
		return conditions.test(user) && users.putIfAbsent(user.email(), user) == null;
	}

	public boolean unsubscribe(String email) {
		Objects.requireNonNull(email);
		return users.remove(email) == null;
	}

	public void sendMessage(String title, String content) {
		Objects.requireNonNull(title);
		Objects.requireNonNull(content);
		mailer.sendMailAll(users.keySet().stream().toList(), "[" + name + "]" + title, content);
	}

	public static void main(String[] args) {
		User arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 18, Nationality.FRENCH);
		User youssef = new User("Youssef", "youssef@u-pem.fr", 25, Nationality.BRITISH);
		User someone = new User("Some", "one@univ-eiffel.fr", 24, Nationality.SPANISH);
		
		Newsletter whyMe = new NewsletterBuilder()
				.name("Why me!")
				.restriction(user -> user.age() % 2 == 0)
				.restriction(user -> user.email().endsWith("univ-eiffel.fr"))
				.mailer(Newsletter.GMAILER)
				.build();
		
		whyMe.subscribe(youssef);
		whyMe.subscribe(arnaud);
		whyMe.subscribe(someone);
		whyMe.sendMessage("Something something", "Something is indeed something");
		
	}

}
