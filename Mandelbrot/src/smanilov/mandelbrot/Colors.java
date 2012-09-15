package smanilov.mandelbrot;

import java.awt.Color;

/**
 * Used to manage the colors that the fractal is drawn in. 
 * @author szm
 */
public class Colors {

	private static Color color = new Color(192, 244, 11);
	
	/**
	 * Get the bright end of the gradient.
	 * @return
	 */
	public static Color getColor() {
		return color;
	}
	
	/**
	 * Set the bright end of the gradient.
	 */
	public static void setColor(Color color) {
		Colors.color = color; 
	}

}
