package fr.uge.poo.paint.ex6;

import fr.uge.poo.paint.ex6.Canvas.CanvasColor;

abstract class Shape {

	final int x, y, width, height;

	public Shape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double distance(int x, int y) {
		int x_center = this.x + width / 2;
		int y_center = this.y + height / 2;
		return Figure.distanceSq(x_center, y_center, x, y);
	}
	
	public abstract void draw(Canvas graphics, CanvasColor color);
}
