/* Copyright 2012 Stanislav Manilov
 *
 * This file is part of ExploreMandelbrot.
 *
 * ExploreMandelbrot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ExploreMandelbrot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ExploreMandelbrot.  If not, see <http://www.gnu.org/licenses/>.
 */
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
