package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;

public sealed interface Figure permits Line, Rectangle, Ellipse {

	void draw(Graphics2D graphics);
}
