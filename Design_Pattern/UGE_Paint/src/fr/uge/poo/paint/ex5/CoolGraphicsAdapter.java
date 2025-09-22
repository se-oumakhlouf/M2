package fr.uge.poo.paint.ex5;

import java.awt.Color;
import java.util.Map;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;

public class CoolGraphicsAdapter implements Canvas {
	
	private final CoolGraphics coolGraphics;
	
	private static final Map<Color, ColorPlus> COLOR_MAPPING = Map.of(
			Color.RED, ColorPlus.RED,
			Color.GREEN, ColorPlus.GREEN,
			Color.ORANGE, ColorPlus.ORANGE,
			Color.BLUE, ColorPlus.BLUE,
      Color.BLACK, ColorPlus.BLACK,
      Color.WHITE, ColorPlus.WHITE
			);
	
	public CoolGraphicsAdapter(String title, int width, int height) {
		this.coolGraphics = new CoolGraphics(title, width, height);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.BLACK);
		coolGraphics.drawLine(x1, y1, x2, y2, colorPlus);
	}

	@Override
	public void drawEllipse(int x, int y, int width, int height, Color color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.BLACK);
		coolGraphics.drawEllipse(x, y, width, height, colorPlus);
	}
	
	@Override
	public void reset(Color color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.WHITE);
		coolGraphics.repaint(colorPlus);
	}
	
	@Override
	public void waitForMouseEvents(MouseCallback callback) {
		coolGraphics.waitForMouseEvents((x, y) -> callback.mouseClicked(x, y));
	}

}
