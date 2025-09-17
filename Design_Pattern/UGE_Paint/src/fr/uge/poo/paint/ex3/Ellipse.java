package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public final class Ellipse implements Figures{
	
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

}
