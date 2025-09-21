package fr.uge.poo.paint.ex5;

public sealed interface Figure permits Line, Rectangle, Ellipse {

	void draw(Graphics graphics);
	
	static double distanceSq(double x1, double y1, double x2, double y2) {
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1); 
	}
	
	double distance(int x, int y);
}
