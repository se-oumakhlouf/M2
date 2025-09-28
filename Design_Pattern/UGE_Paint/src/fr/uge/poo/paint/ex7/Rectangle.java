package fr.uge.poo.paint.ex7;

import fr.uge.poo.paint.ex7.Canvas.CanvasColor;

public final class Rectangle extends Shape implements Figure {

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas graphics, CanvasColor color) {
		graphics.drawRectangle(x, y, width, height, color);
	}
	
}
