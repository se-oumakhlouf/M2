package fr.uge.poo.newsletter.question5;

import java.util.List;
import java.util.Objects;

public class EEMailerObserver implements NewsletterObserver {

	private static final Mailer MAILER = new EEmailerAdapter();

	@Override
	public void onSubscribeSuccess(String newsletterName, String userEmail) {
		MAILER.sendMail(userEmail, "[ Bienvenue ]", "Bienvenue dans la newsletter " + newsletterName);

	}

	@Override
	public void onSubscribeFail(String newsletterName) {
		MAILER.sendMail("support@good.corp", "[ Erreur ]",
				"Votre inscription à la newsletter " + newsletterName + " a échoué");

	}

	@Override
	public void onMailContaining(String newsletterName, String endwith) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFirstPassXUsers(String newsletterName, int usersCountStep, List<String> usersNames) {
		if (Objects.requireNonNull(usersNames).size() < usersCountStep) {
			return;
		}
		MAILER.sendMail("support@good.corp", "[ Nouveau Pallier pour " + newsletterName + " ]",
				"La newsletter " + newsletterName + " a dépassé le pallier des " + usersCountStep
						+ " utilisateurs pour la première fois");

	}

}
