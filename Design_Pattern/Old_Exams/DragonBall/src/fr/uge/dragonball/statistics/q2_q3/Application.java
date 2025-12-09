package fr.uge.dragonball.statistics.q2_q3;

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
		
		Fighter adults = goku.fusionWith(vegeta);
		Fighter kids = gohan.fusionWith(trunk);
		
		Fighter mega = adults.fusionWith(kids);
		
		// --------------
		
		Transformation ss = new SuperSayan();
		Transformation ss2 = new SuperSayanTwo();
		Transformation blue = new SuperBlue();
	
		
		Fighter gokuSS = goku.tranform(ss);
		Fighter adultsBlue = adults.tranform(blue);
		Fighter vegetaBlue = vegeta.tranform(blue);
		Fighter gohanSS2 = gohan.tranform(ss2);
		Fighter vegetaGohan = vegetaBlue.fusionWith(gohanSS2);
		
		
		System.out.println("With Transformations:");
		System.out.println(gokuSS);
		System.out.println(adultsBlue);
		System.out.println(vegetaGohan);
	}

}
