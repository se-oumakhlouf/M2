package fr.uge.poo.paint.ex7;

public class Library {
	
	public static Canvas build(String[] args, String title, int width, int height) {
		if (args.length > 1 && args[1].equals("-legacy")) {
			System.out.println("SimpleGraphics");
			return new SimpleGraphicsAdapter(title, width, height);
		}
		System.out.println("CoolGraphics");
		return new CoolGraphicsAdapter(title, width, height);
	}

}
