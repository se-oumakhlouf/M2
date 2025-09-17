package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public final class Ellipse implements Figure{
	
	private final int x, y, width, height;
	
	public Ellipse(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.drawOval(x, y, width, height);
	}
	
	@Override
	public double distance(int x, int y) {
		int x_center = this.x + width / 2;
		int y_center = this.y + height / 2;
		return Figure.distanceSq(x_center, y_center, x, y);
	}

}
