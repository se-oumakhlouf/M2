package fr.uge.poo.paint.ex2;

import java.awt.Graphics2D;

public class Line {
	
	private final int x1, y1, x2, y2;

	Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void drawLine(Graphics2D graphics) {
		graphics.drawLine(x1, y1, x2, y2);
	}
}
