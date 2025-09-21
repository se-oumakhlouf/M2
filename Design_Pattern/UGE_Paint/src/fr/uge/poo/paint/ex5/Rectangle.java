package fr.uge.poo.paint.ex5;

import java.awt.Graphics2D;

public final class Rectangle extends Shape implements Figure {

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawRectangle(x, y, width, height);
	}
}
