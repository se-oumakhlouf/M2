package fr.uge.poo.paint.ex6;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import fr.uge.poo.paint.ex6.Canvas.CanvasColor;

public class Drawing {
	
	private final ArrayList<Figure> figures = new ArrayList<>();
	
	public void add(Figure figure) {
		figures.add(figure);
	}

	public void drawAll(Canvas graphics, CanvasColor color) {
		figures.forEach(figure -> figure.draw(graphics, color));
		graphics.render();
	}
	
	public Figure closestFigure(int x, int y) {
		if (figures.isEmpty()) {
			return null;
		}
		var closest =  figures.stream().min(Comparator.comparingDouble(figure -> figure.distance(x, y)));
		return closest.get();
	}
	
	void splitLines(String line) {
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
	
	static Drawing fromFile(Path path) throws IOException {
		Drawing drawing = new Drawing();
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(line -> drawing.splitLines(line));
		}
		return drawing;
	}
	
	void onClick(Canvas graphics, int x, int y) {
		System.out.println("x: " + x + ", y: " + y);
		Figure closest = closestFigure(x, y);
		if (closest == null) {
			return;
		}
		closest.draw(graphics, CanvasColor.ORANGE);
		graphics.render();
	}
	
	
	
}
