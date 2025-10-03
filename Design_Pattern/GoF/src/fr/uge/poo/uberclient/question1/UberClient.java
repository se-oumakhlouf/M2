package fr.uge.poo.uberclient.question1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

// Quel principe SOLID n'est plus respecté par la classe UberClient ?
// 		SRP (Single Responsibility Principle), deux responsabilités :
//				- gérer les données de UberClient
// 				- Formater l'affichage HTML

// Votre boss vient vous voir en catastrophe pour vous dire que les adresses mails ne doivent surtout plus être affichées en clair. On veut a*@u* au lieu de arnaud.carayol@univ-eiffel.fr.
// Effectuez les changements demandés. Est-ce que vous voyez un gain avec votre nouvelle architecture par rapport au code initial ?
//		Besoin de changer une seule implémentation qui se propage sur toutes les méthodes

public class UberClient {

	private final String firstName;
	private final String lastName;
	private final long uid;
	private final List<Integer> grades;
	private final List<String> emails;
	private final List<String> phoneNumbers;

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
			if (uid < 0) {
				throw new IllegalArgumentException("UID must be positive");
			}
			this.uid = uuid;
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
			emails.add(email);
			return this;
		}

		public UberClientBuilder phoneNumbers(String phoneNumber) {
			phoneNumbers.add(phoneNumber);
			return this;
		}

		public UberClient build() {
			if (firstName == null || lastName == null || grades.isEmpty() || phoneNumbers.isEmpty()) {
				throw new IllegalArgumentException();
			}
			if (uid == 0) {
				uid = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
			}
			return new UberClient(firstName, lastName, uid, grades, emails, phoneNumbers);
		}
	}

	public static UberClientBuilder with() {
		return new UberClientBuilder();
	}
	
	private UberClientInfo infos() {
		return new UberClientInfo(firstName, lastName, emails, grades);
	}
	
	public String export(UberClientFormatter formater) {
		return formater.format(infos());
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
		this(firstName, lastName, ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE), grades, emails,
				phoneNumbers);
	}

	public static void main(String[] args) {
		var arnaud = UberClient.with().firstName("Arnaud").lastName("Carayol").uid(1)
				.grades(List.of(1, 2, 5, 2, 5, 1, 1, 1)).email("arnaud.carayol@univ-eiffel.fr")
				.email("arnaud.carayol@u-pem.fr").phoneNumbers("07070707070707").build();

//		System.out.println(arnaud.toHTML());
//		System.out.println(arnaud.toHTMLSimple());
//		System.out.println(arnaud.toHTMWithAverageOverLast7Grades());
//		System.out.println(arnaud.toHtmlWithEmails());
//		System.out.println(arnaud.toHtmlWithEmailsAndAverageOverLast5Grades());
	}

}