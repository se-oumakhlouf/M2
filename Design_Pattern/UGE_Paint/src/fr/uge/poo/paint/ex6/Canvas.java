package fr.uge.poo.paint.ex6;

public interface Canvas {
	
	enum CanvasColor {
		RED, GREEN, ORANGE, BLUE, BLACK, WHITE
	}
		
	@FunctionalInterface
	public interface MouseClickCallback {
		void mouseClicked(int x, int y);
	}

	void drawLine(int x1, int y1, int x2, int y2, CanvasColor color);
	
	void drawEllipse(int x, int y, int width, int height, CanvasColor color);
	
	default void drawRectangle(int x, int y, int width, int height, CanvasColor color) {
		int x2 = x + width;
		int y2 = y + height;
		drawLine(x, y, x2, y, color);
		drawLine(x2, y, x2, y2, color);
		drawLine(x2, y2, x, y2, color);
		drawLine(x, y2, x, y, color);
	}
	
	void reset(CanvasColor color);
	
	void waitForMouseEvents(MouseClickCallback callback);
	
	default void render() {
		return;
	}
}
