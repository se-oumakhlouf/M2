package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Paint {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Expected Arguments : {file.txt} {option}.\n" + "option :\n\t"
					+ "default -> CoolGraphics,\n\t" + "-legacy -> SimpleGraphics");
		}

		Path path = Paths.get(args[0]);
		Drawing figures = Drawing.fromFile(path);

		Canvas graphics = Library.build(args, "area", 800, 600);
		graphics.reset(Color.WHITE);
		figures.drawAll(graphics);
		graphics.waitForMouseEvents((x, y) -> figures.onClick(graphics, x, y));
	}

}
