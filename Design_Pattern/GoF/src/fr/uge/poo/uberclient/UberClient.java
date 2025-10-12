package fr.uge.poo.uberclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// Quel principe SOLID n'est plus respecté par la classe UberClient ?
// 		SRP (Single Responsibility Principle), deux responsabilités :
//				- gérer les données de UberClient
// 				- Formater l'affichage HTML

// Votre boss vient vous voir en catastrophe pour vous dire que les adresses mails ne doivent surtout plus être affichées en clair. On veut a*@u* au lieu de arnaud.carayol@univ-eiffel.fr.
// Effectuez les changements demandés. Est-ce que vous voyez un gain avec votre nouvelle architecture par rapport au code initial ?
//		Besoin de changer une seule implémentation qui se propage sur toutes les méthodes

public class UberClient {

	public static class UberClientBuilder {

		private String firstName;
		private String lastName;
		private long uid;
		private List<Integer> grades = new ArrayList<>();
		private List<String> emails = new ArrayList<>();
		private List<String> phoneNumbers = new ArrayList<>();

		public UberClientBuilder firstName(String firstName) {
			this.firstName = Objects.requireNonNull(firstName);
			return this;
		}

		public UberClientBuilder lastName(String lastName) {
			this.lastName = Objects.requireNonNull(lastName);
			return this;
		}

		public UberClientBuilder uid(long uuid) {
			this.uid = uuid;
			return this;
		}
		
		public UberClientBuilder randomUID() {
			this.uid = ThreadLocalRandom.current().nextLong();
			return this;
		}

		public UberClientBuilder grades(int grade) {
			if (grade < 1 || grade > 5) {
				throw new IllegalArgumentException("All grades must be between 1 and 5");
			}
			grades.add(grade);
			return this;
		}

		public UberClientBuilder grades(List<Integer> grades) {
			for (var grade : grades) {
				grades(grade);
			}
			return this;
		}

		public UberClientBuilder email(String email) {
			emails.add(Objects.requireNonNull(email));
			return this;
		}

		public UberClientBuilder phoneNumbers(String phoneNumber) {
			phoneNumbers.add(Objects.requireNonNull(phoneNumber));
			return this;
		}

		public UberClient build() {
			if (firstName == null || lastName == null || uid == 0) {
				throw new IllegalStateException("firstName, lastName and uid are mandatory");
			}
			if (grades.isEmpty()) {
				throw new IllegalStateException("the client must have at least one grade");
			}
			if (phoneNumbers.isEmpty() && emails.isEmpty()) {
				throw new IllegalStateException("the client must have either of a phoneNumber or an email");
			}
			return new UberClient(this);
		}
	}

	public static UberClientBuilder with() {
		return new UberClientBuilder();
	}

	private final String firstName;
	private final String lastName;
	private final long uid;
	private final List<Integer> grades;
	private final List<String> emails;
	private final List<String> phoneNumbers;

	static String obfuscateEmail(String email) {
		return Arrays.stream(email.split("@")).map(s -> s.length() != 0 ? s.charAt(0) + "*" : "*")
				.collect(Collectors.joining("@"));
	}

	private UberClientInfo uberClientInfo(AverageStrategy strategy) {
		return new UberClientInfo(firstName, lastName, emails, () -> strategy.average(grades).orElseThrow());
	}

	private UberClientInfo uberClientInfo() {
		return uberClientInfo(AverageStrategy.STANDARD);
	}

	public String format(UberClientFormatter formatter) {
		return formatter.format(uberClientInfo());
	}

	public String format(UberClientFormatter formatter, AverageStrategy strategy) {
		return formatter.format(uberClientInfo(strategy));
	}

	private UberClient(String firstName, String lastName, long uid, List<Integer> grades, List<String> emails,
			List<String> phoneNumbers) {
		this.firstName = Objects.requireNonNull(firstName);
		this.lastName = Objects.requireNonNull(lastName);
		if (uid <= 0) {
			throw new IllegalArgumentException("UID must be positive");
		}
		this.uid = uid;
		this.grades = List.copyOf(grades);
		for (var grade : grades) {
			if (grade < 1 || grade > 5) {
				throw new IllegalArgumentException("All grades must be between 1 and 5");
			}
		}
		this.emails = List.copyOf(emails);
		this.phoneNumbers = List.copyOf(phoneNumbers);
		if (grades.size() == 0) {
			throw new IllegalArgumentException("A client must have at least one grade");
		}
		if (emails.size() == 0 && phoneNumbers.size() == 0) {
			throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
		}
	}

	private UberClient(String firstName, String lastName, List<Integer> grades, List<String> emails,
			List<String> phoneNumbers) {
		this(firstName, lastName, ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE), grades, emails, phoneNumbers);
	}
	
	private UberClient(UberClientBuilder builder) {
		this(builder.firstName, builder.lastName, builder.grades, builder.emails, builder.phoneNumbers);
	}

	public static void main(String[] args) {
		var arnaud = UberClient.with()
				.firstName("Arnaud")
				.lastName("Carayol")
				.randomUID()
				.grades(List.of(1, 2, 5, 2, 5, 1, 1, 1))
				.email("arnaud.carayol@univ-eiffel.fr")
				.email("arnaud.carayol@u-pem.fr")
				.phoneNumbers("07070707070707")
				.build();
		
		var youssef = UberClient.with()
				.firstName("Youssef")
				.lastName("Bergeron")
				.uid(10)
				.grades(3)
				.email("youssef.bergeron@u-pem.fr")
				.build();
		
		System.out.println(arnaud.format(UberClientFormatter.HTML));
		System.out.println(arnaud.format(UberClientFormatter.HTMLSimple));
		System.out.println(arnaud.format(UberClientFormatter.HTMLWithEmails));
		System.out.println(arnaud.format(UberClientFormatter.HTML, AverageStrategy.STANDARD));
		System.out.println(arnaud.format(UberClientFormatter.HTML, AverageStrategy.overLast(7)));
		
		System.out.println(youssef.format(UberClientFormatter.HTMLWithEmails));
	}

}