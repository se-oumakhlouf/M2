package fr.uge.poo.paint.ex6;

import fr.uge.poo.paint.ex6.Canvas.CanvasColor;

public final class Line implements Figure {
	
	private final int x1, y1, x2, y2;

	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public void draw(Canvas graphics, CanvasColor color) {
		graphics.drawLine(x1, y1, x2, y2, color);
	}
	
	@Override 
	public double distance(int x, int y) {
		int x_center = (x1 + x2) / 2;
		int y_center =  (y1 + y2) / 2;
		return Figure.distanceSq(x_center, y_center, x, y);
	}
	
}
