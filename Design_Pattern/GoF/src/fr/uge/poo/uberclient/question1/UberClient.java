package fr.uge.poo.uberclient.question1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

	private UberClient(String firstName, String lastName, long uid, List<Integer> grades, List<String> emails,
			List<String> phoneNumbers) {
		this.firstName = Objects.requireNonNull(firstName);
		this.lastName = Objects.requireNonNull(lastName);
		if (uid < 0) {
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
	
    public String toHTML() {
        var averageGrade= grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s  (%1.2f*)</h2>",firstName,lastName,averageGrade);
    }

    public String toHTMWithAverageOverLast7Grades() {
        var averageGrade= grades.stream().limit(7).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s  (%1.2f*)</h2>",firstName,lastName,averageGrade);
    }

    public String toHTMLSimple() {
        return String.format("<h2>%s %s </h2>",firstName,lastName);
    }

    public String toHtmlWithEmails() {
            var averageGrade = grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
            return String.format("<h2>%s %s (%1.2f*) : %s </h2>",firstName,lastName,averageGrade,emails);
    }

    public String toHtmlWithEmailsAndAverageOverLast5Grades() {
        var averageGrade= grades.stream().limit(5).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s (%1.2f*) : %s </h2>",firstName,lastName,averageGrade,emails);
    }

	public static void main(String[] args) {
//		var arnaud = new UberClient("Arnaud", "Carayol", 1, List.of(1, 2, 5, 2, 5, 1, 1, 1),
//				List.of("arnaud.carayol@univ-eiffel.fr", "arnaud.carayol@u-pem.fr"), List.of("07070707070707"));
//		var youssef = new UberClient("Youssef", "Bergeron", List.of(5), List.of("youssefbergeron@outlook.fr"),
//				List.of());
		
		UberClient arnaudBuild = UberClient.with()
				.firstName("Arnaud")
				.lastName("Carayol")
				.uid(1)
				.grades(List.of(1, 2, 5, 2, 5, 1, 1, 1))
				.email("arnaud.carayol@univ-eiffel.fr").email("arnaud.carayol@u-pem.fr")
				.phoneNumbers("07070707070707")
				.build();
		
		UberClient youssefBuild = UberClient.with()
				.firstName("Youssef")
				.lastName("Bergeron")
				.grades(5)
				.email("youssefbergeron@outlook.fr")
				.phoneNumbers("")
				.build();
		
		System.out.println(arnaudBuild);
		System.out.println(youssefBuild);
	}

}