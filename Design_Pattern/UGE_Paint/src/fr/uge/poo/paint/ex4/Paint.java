package fr.uge.poo.paint.ex4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import fr.uge.poo.simplegraphics.SimpleGraphics;

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
			throw new UnsupportedOperationException();
		}
	}

	static void drawAll(Graphics2D graphics, Figures figures) {
		graphics.setColor(Color.black);
		figures.drawAll(graphics);
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Need a file in args");
		}

		Figures figures = new Figures();

		Path path = Paths.get(args[0]);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> splitLines(line, figures));
		}
		SimpleGraphics area = new SimpleGraphics("area", 800, 600);
		area.clear(Color.WHITE);
		area.render(graphics -> drawAll(graphics, figures));
	}

}
