package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Figures {
	
	private final ArrayList<Figure> figures = new ArrayList<>();
	
	public void add(Figure figure) {
		figures.add(figure);
	}

	public void drawAll(Graphics2D graphics) {
		figures.forEach(figure -> figure.draw(graphics));
	}
	
	public Figure closestFigure(int x, int y) {
		Figure closest = figures.getFirst();
		double minDistance = Double.MAX_VALUE;
		for (var figure : figures) {
			double distance = figure.distance(x, y);
			if (distance < minDistance) {
				closest = figure;
				minDistance = distance;
			}
		}
		return closest;
	}
	
}
