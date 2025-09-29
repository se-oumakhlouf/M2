package fr.uge.poo.paint.ex7;import java.util.Map;

import com.evilcorp.coolgraphics.CoolGraphics;
import com.evilcorp.coolgraphics.CoolGraphics.ColorPlus;

public class CoolGraphicsAdapter implements Canvas {
	
	private final CoolGraphics coolGraphics;
	
	private static final Map<CanvasColor, ColorPlus> COLOR_MAPPING = Map.of(
			CanvasColor.RED, ColorPlus.RED,
			CanvasColor.GREEN, ColorPlus.GREEN,
			CanvasColor.ORANGE, ColorPlus.ORANGE,
			CanvasColor.BLUE, ColorPlus.BLUE,
			CanvasColor.BLACK, ColorPlus.BLACK,
			CanvasColor.WHITE, ColorPlus.WHITE
			); 
	
	public CoolGraphicsAdapter(String title, int width, int height) {
		this.coolGraphics = new CoolGraphics(title, width, height);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2, CanvasColor color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.BLACK);
		coolGraphics.drawLine(x1, y1, x2, y2, colorPlus);
	}

	@Override
	public void drawEllipse(int x, int y, int width, int height, CanvasColor color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.BLACK);
		coolGraphics.drawEllipse(x, y, width, height, colorPlus);
	}
	
	@Override
	public void reset(CanvasColor color) {
		ColorPlus colorPlus = COLOR_MAPPING.getOrDefault(color, ColorPlus.WHITE);
		coolGraphics.repaint(colorPlus);
	}
	
	@Override
	public void waitForMouseEvents(MouseClickCallback callback) {
		coolGraphics.waitForMouseEvents((x, y) -> callback.mouseClicked(x, y));
	}

}
