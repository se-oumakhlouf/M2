package fr.uge.poo.paint.ex3;

import java.awt.Graphics2D;

public sealed interface Figures permits Line, Rectangle, Ellipse {

	void draw(Graphics2D graphics);
}
