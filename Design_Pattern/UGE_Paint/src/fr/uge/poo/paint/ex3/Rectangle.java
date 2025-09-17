package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public final class Rectangle implements Figures {

	private final int x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawRect(x, y, width, height);
	}
}
