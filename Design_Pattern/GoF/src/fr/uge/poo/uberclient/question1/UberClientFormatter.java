package fr.uge.poo.uberclient.question1;

public interface UberClientFormatter {
	
	UberClientFormatter HTML = clientInfo -> String.format("<h2>%s %s  (%1.2f*)</h2>", clientInfo.firstName(), clientInfo.lastName(), 0);
	
	String format(UberClientInfo clientInfo);
}
