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
}
