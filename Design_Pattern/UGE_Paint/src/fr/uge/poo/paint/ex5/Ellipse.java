package fr.uge.poo.paint.ex5;

public final class Ellipse extends Shape implements Figure {
		
	public Ellipse(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas graphics) {
		graphics.drawEllipse(x, y, width, height);
	}

}
