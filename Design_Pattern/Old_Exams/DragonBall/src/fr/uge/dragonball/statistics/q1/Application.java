package fr.uge.dragonball.statistics.q1;

// Design Pattern :
// - Builder

public class Application {

	public static void main(String[] args) {
		Fighter goku = Fighter.with()
				.name("Goku")
				.power(9000)
				.maxHealth(10_000)
				.build();
		
		Fighter vegeta = Fighter.with()
				.name("Vegeta")
				.power(8500)
				.maxHealth(9500)
				.build();
		
		Fighter gohan = Fighter.with()
				.name("Gohan")
				.power(7000)
				.maxHealth(3500)
				.build();
		
		Fighter trunk = Fighter.with()
				.name("Trunk")
				.power(6500)
				.maxHealth(4000)
				.build();
		
		Fighter adults = goku.fusion(vegeta);
		Fighter kids = gohan.fusion(trunk);
		
		Fighter mega = adults.fusion(kids);
		
		System.out.println("Individual Fighters:");
		System.out.println(goku);
		System.out.println(vegeta);
		
		System.out.println("\nFusion Fighters:");
		System.out.println(adults);
		System.out.println(kids);
		
		System.out.println("\nMega Fusion:");
		System.out.println(mega);
	}

}
