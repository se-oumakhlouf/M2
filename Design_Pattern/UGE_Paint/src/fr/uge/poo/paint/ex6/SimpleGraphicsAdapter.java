package fr.uge.poo.paint.ex6;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class SimpleGraphicsAdapter implements Canvas {

	private final SimpleGraphics simpleGraphics;
	private final ArrayList<Consumer<Graphics2D>> drawingActions = new ArrayList<>();
	
	private static final Map<CanvasColor, Color> COLOR_MAPPING = Map.of(
			CanvasColor.RED, Color.RED,
			CanvasColor.GREEN, Color.GREEN,
			CanvasColor.ORANGE, Color.ORANGE,
			CanvasColor.BLUE, Color.BLUE,
			CanvasColor.BLACK, Color.BLACK,
			CanvasColor.WHITE, Color.WHITE
			); 
	

	public SimpleGraphicsAdapter(String title, int width, int height) {
		this.simpleGraphics = new SimpleGraphics(title, width, height);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, CanvasColor color) {
		Color sgColor = COLOR_MAPPING.getOrDefault(color, Color.BLACK);
		drawingActions.add(graphics -> {
			graphics.setColor(sgColor);
			graphics.drawLine(x1, y1, x2, y2);
		});
	}

	@Override
	public void drawEllipse(int x, int y, int width, int height, CanvasColor color) {
		Color sgColor = COLOR_MAPPING.getOrDefault(color, Color.BLACK);
		drawingActions.add(graphics -> {
			graphics.setColor(sgColor);
			graphics.drawOval(x, y, width, height);
		});
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height, CanvasColor color) {
		Color sgColor = COLOR_MAPPING.getOrDefault(color, Color.BLACK);
		drawingActions.add(graphics -> {
			graphics.setColor(sgColor);
			graphics.drawRect(x, y, width, height);
		});
	}
	
	@Override
	public void reset(CanvasColor color) {
		Color sgColor = COLOR_MAPPING.getOrDefault(color, Color.WHITE);
		simpleGraphics.clear(sgColor);
	}
	
	@Override
	public void waitForMouseEvents(MouseClickCallback callback) {
		simpleGraphics.waitForMouseEvents((x, y) -> callback.mouseClicked(x, y));
	}

	@Override
	public void render() {
		var toDraw = List.copyOf(drawingActions);
		drawingActions.clear();
		simpleGraphics.render(graphics -> {
			toDraw.forEach(consumer -> consumer.accept(graphics));
		});
	}

}
