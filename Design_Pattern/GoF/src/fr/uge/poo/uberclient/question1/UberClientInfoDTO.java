package fr.uge.poo.uberclient.question1;

import java.util.List;

public record UberClientInfoDTO(String nom, String prenom, String email, List<Integer> grades) {

	public int moyenne() {
		return 0;
	}
}
