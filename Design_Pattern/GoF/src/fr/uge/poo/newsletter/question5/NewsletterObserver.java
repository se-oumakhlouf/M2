package fr.uge.poo.newsletter.question5;

import java.util.List;

// mettre directement l'object Newsletter dans les méthode au lieu du nom
public interface NewsletterObserver {
		
	void onSubscribeSuccess (String newsletterName, String userEmail);
	
	void onSubscribeFail (String newsletterName);
	
	void onMailContaining (String newsletterName, String endwith);
	
	void onFirstPassXUsers (String newsletterName, int usersCountStep, List<String> usersNames);
	
	default void onSubscribe(Newsletter newsletter, User user, boolean successfull) {};
}
