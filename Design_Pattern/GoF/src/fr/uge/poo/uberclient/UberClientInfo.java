package fr.uge.poo.uberclient;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;

public record UberClientInfo(String firstName, String lastName, List<String> emails, DoubleSupplier averageSupplier) {

	public UberClientInfo {
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(emails);
		Objects.requireNonNull(averageSupplier);
		emails = emails.stream().map(UberClient::obfuscateEmail).toList();
	}
	
	public double average() {
		return averageSupplier.getAsDouble();
	}
}
