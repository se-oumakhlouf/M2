package fr.uge.poo.uberclient;

public interface UberClientFormatter {

	UberClientFormatter HTML = clientInfo -> String.format("<h2>%s %s (%1.2f*)</h2>", clientInfo.firstName(),
			clientInfo.lastName(), clientInfo.average());
	UberClientFormatter HTMLSimple = clientInfo -> String.format("<h2>%s %s <h2>", clientInfo.firstName(),
			clientInfo.lastName());
	UberClientFormatter HTMLWithEmails = clientInfo -> String.format("<h2>%s %s (%1.2f*) : %s <h2>",
			clientInfo.firstName(), clientInfo.lastName(), clientInfo.average(),
			UberClient.obfuscateEmail(clientInfo.emails().getFirst()));

	String format(UberClientInfo clientInfo);
}
