package fr.uge.poo.paint.ex5;

import java.awt.Color;

public interface Canvas {
		
	void drawLine(int x1, int y1, int x2, int y2, Color color);
	
	default void drawLine(int x1, int y1, int x2, int y2) {
		this.drawLine(x1, y1, x2, y2, Color.BLACK);
	}
	
	void drawEllipse(int x, int y, int width, int height, Color color);
	
	default void drawEllipse(int x, int y, int width, int height) {
		this.drawEllipse(x, y, width, height, Color.BLACK);
	}
	
	default void drawRectangle(int x, int y, int width, int height, Color color) {
		int x2 = x + width;
		int y2 = y + height;
		drawLine(x, y, x2, y, color);
		drawLine(x2, y, x2, y2, color);
		drawLine(x2, y2, x, y2, color);
		drawLine(x, y2, x, y, color);
	}
	
	default public void drawRectangle(int x, int y, int widht, int height) {
		this.drawRectangle(x, y, widht, height, Color.BLACK);
	}
	
	void reset(Color color);
	
	void waitForMouseEvents(MouseCallback callback);
}
