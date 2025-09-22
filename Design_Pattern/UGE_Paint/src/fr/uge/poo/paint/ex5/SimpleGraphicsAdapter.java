package fr.uge.poo.paint.ex5;

import java.awt.Color;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class SimpleGraphicsAdapter implements Canvas {

	private final SimpleGraphics simpleGraphics;

	public SimpleGraphicsAdapter(String title, int width, int height) {
		this.simpleGraphics = new SimpleGraphics(title, width, height);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		simpleGraphics.render(graphics -> {
			graphics.setColor(color);
			graphics.drawLine(x1, y1, x2, y2);
		});
	}

	@Override
	public void drawEllipse(int x, int y, int width, int height, Color color) {
		simpleGraphics.render(graphics -> {
			graphics.setColor(color);
			graphics.drawOval(x, y, width, height);
		});
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height, Color color) {
		simpleGraphics.render(graphics -> {
			graphics.setColor(color);
			graphics.drawRect(x, y, width, height);
		});
	}
	
	@Override
	public void reset(Color color) {
		simpleGraphics.clear(color);
	}
	
	@Override
	public void waitForMouseEvents(MouseCallback callback) {
		simpleGraphics.waitForMouseEvents((x, y) -> callback.mouseClicked(x, y));
	}

}
