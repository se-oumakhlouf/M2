package fr.uge.poo.paint.ex7;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.uge.poo.paint.ex7.Canvas.CanvasColor;

public class Paint {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Expected Arguments : {file.txt} {option}.\n" + "option :\n\t"
					+ "default -> CoolGraphics,\n\t" + "-legacy -> SimpleGraphics");
		}

		Path path = Paths.get(args[0]);
		Drawing figures = Drawing.fromFile(path);

		Canvas graphics = Library.build(args, "area", figures.windowX(), figures.windowY());
		graphics.reset(CanvasColor.WHITE);
		figures.drawAll(graphics, CanvasColor.BLACK);
		graphics.waitForMouseEvents((x, y) -> figures.onClick(graphics, x, y));
	}

}
