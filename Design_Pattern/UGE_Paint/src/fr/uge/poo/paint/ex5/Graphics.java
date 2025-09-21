package fr.uge.poo.paint.ex5;

import java.awt.Color;

public interface Graphics {
		
	public void drawLine(int x1, int y1, int x2, int y2, Color color);
	
	default public void drawLine(int x1, int y1, int x2, int y2) {
		this.drawLine(x1, y1, x2, y2, Color.BLACK);
	}
	
	public void drawEllipse(int x, int y, int width, int height, Color color);
	
	default public void drawEllipse(int x, int y, int width, int height) {
		this.drawEllipse(x, y, width, height, Color.BLACK);
	}
	
	public void drawRectangle(int x, int y, int width, int height, Color color);
	
	default public void drawRectangle(int x, int y, int widht, int height) {
		this.drawRectangle(x, y, widht, height, Color.BLACK);
	}
	
	public void reset(Color color);
	
	public void waitForMouseEvents(MouseCallback callback);
}
