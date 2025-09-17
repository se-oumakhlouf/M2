package fr.uge.poo.paint.ex2;

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

	static void splitLines(String line, List<Line> array) {
		String[] tokens = line.split(" ");
		int x1 = Integer.parseInt(tokens[1]);
		int y1 = Integer.parseInt(tokens[2]);
		int x2 = Integer.parseInt(tokens[3]);
		int y2 = Integer.parseInt(tokens[4]);
		array.add(new Line(x1, y1, x2, y2));
	}
	
	static void drawAll(Graphics2D graphics, List<Line> lines) {
		graphics.setColor(Color.black);
		lines.forEach(line -> line.drawLine(graphics));
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			return;
		}
		
		ArrayList<Line> array = new ArrayList<>();
		
		Path path = Paths.get(args[0]);
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> splitLines(line, array));
		}
		SimpleGraphics area = new SimpleGraphics("area", 800, 600);
		area.clear(Color.WHITE);
        area.render(graphics -> drawAll(graphics, array));	
	}

}
