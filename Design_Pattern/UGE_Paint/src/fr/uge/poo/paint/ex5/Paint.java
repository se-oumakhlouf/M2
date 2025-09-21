package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Paint {

	static void splitLines(String line, Figures figures) {
		String[] tokens = line.split(" ");
		int x1 = Integer.parseInt(tokens[1]);
		int y1 = Integer.parseInt(tokens[2]);
		int x2 = Integer.parseInt(tokens[3]);
		int y2 = Integer.parseInt(tokens[4]);

		switch (tokens[0]) {
			case "line":
				figures.add(new Line(x1, y1, x2, y2));
				break;
			case "rectangle":
				figures.add(new Rectangle(x1, y1, x2, y2));
				break;
			case "ellipse":
				figures.add(new Ellipse(x1, y1, x2, y2));
				break;
			default:
				throw new UnsupportedOperationException("Expected line/rectangle/ellipse, found " + tokens[0]);
		}
	}

	private static void drawAll(Graphics graphics, Figures figures) {
		figures.drawAll(graphics);
	}

	private static void onClick(Graphics graphics, int x, int y, Figures figures) {
		graphics.reset(Color.WHITE);
		System.out.println(x + ", " + y);
		Figure closest = figures.closestFigure(x, y);
		if (closest == null) {
			return;
		}
		closest.draw(graphics);
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Expected Arguments : {file.txt} {option}.\n" + "option :\n\t"
					+ "default -> CoolGraphics,\n\t" + "-legacy -> SimpleGraphics");
		}

		Figures figures = new Figures();

		Path path = Paths.get(args[0]);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> splitLines(line, figures));
		}

		Graphics graphics = Library.build(args, "area", 800, 600);

		graphics.reset(Color.WHITE);

		graphics.drawRectangle(100, 100, 100, 100, Color.red);

//		graphics.render(graphics2D -> drawAll(graphics2D, figures));

		graphics.waitForMouseEvents((x, y) -> onClick(graphics, x, y, figures));
	}

}
