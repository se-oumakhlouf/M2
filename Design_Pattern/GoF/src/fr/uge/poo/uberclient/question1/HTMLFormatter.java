package fr.uge.poo.uberclient.question1;

import java.util.Objects;

public class HTMLFormatter {

	private final UberClient client;
	private boolean showAverage = false;
	private boolean showEmails = false;
	private int limitGrades = Integer.MAX_VALUE;

	public HTMLFormatter(UberClient client) {
		this.client = Objects.requireNonNull(client);
	}

	public HTMLFormatter withAverage() {
		this.showAverage = true;
		return this;
	}

	public HTMLFormatter withAverageOverLast(int n) {
		this.showAverage = true;
		this.limitGrades = n;
		return this;
	}

	public HTMLFormatter withEmails() {
		this.showEmails = true;
		return this;
	}

	public String build() {
		var result = new StringBuilder();
		result.append("<h2>").append(client.getFirstName()).append(" ").append(client.getLastName());

		if (showAverage) {
			var avg = computeAverage();
			result.append(String.format(" (%1.2f*)", avg));
		}

		if (showEmails && !client.getEmails().isEmpty()) {
			result.append(" : ").append(hideEmails());
		}

		result.append("</h2>");
		return result.toString();
	}

	private double computeAverage() {
		return client.getGrades().stream().limit(limitGrades).mapToLong(l -> l).average()
				.orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
	}

	private String hideEmails() {
		return client.getEmails().stream().map(this::hideEmail).toList().toString();
	}

	private String hideEmail(String email) {
		var parts = email.split("@");
		if (parts.length != 2) {
			return email;
		}
		var localPart = parts[0];
		var domainPart = parts[1];

		var obfuscatedLocal = localPart.isEmpty() ? "" : localPart.charAt(0) + "*";
		var obfuscatedDomain = domainPart.isEmpty() ? "" : domainPart.charAt(0) + "*";

		return obfuscatedLocal + "@" + obfuscatedDomain;
	}

}
