package fr.uge.poo.paint.ex5;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

public class Figures {
	
	private final ArrayList<Figure> figures = new ArrayList<>();
	
	public void add(Figure figure) {
		figures.add(figure);
	}

	public void drawAll(Graphics2D graphics) {
		figures.forEach(figure -> figure.draw(graphics));
	}
	
	public Figure closestFigure(int x, int y) {
		if (figures.isEmpty()) {
			return null;
		}
		var closest =  figures.stream().min(Comparator.comparingDouble(figure -> figure.distance(x, y)));
		return closest.get();
	}
	
}
