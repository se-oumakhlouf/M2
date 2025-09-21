package fr.uge.poo.paint.ex5;

import java.awt.Color;

public interface Graphics {
		
	public void drawLine(int x1, int y1, int x2, int y2, Color color);
	
	public void drawEllipse(int x, int y, int width, int height, Color color);
	
	public void drawRectangle(int x, int y, int width, int height, Color color);
	
	public void reset(Color color);
	
	public void waitForMouseEvents(MouseCallback callback);
}
