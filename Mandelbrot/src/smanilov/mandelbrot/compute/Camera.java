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
package smanilov.mandelbrot.compute;

import java.awt.geom.Point2D;

/**
 * Describes which part of the complex plane is visible.
 * @author szm
 */
public class Camera {
	
	/**
	 * The center of the camera on the complex plane.
	 */
	private static Point2D center = new Point2D.Double(0.0, 0.0);
	
	/**
	 * The number of pixels in the move step, when moving the camera center.
	 */
	private static final int MOVE_STEP = 100;
	
	/**
	 * log2(pixels) per unit in the complex plane.
	 */
	private static int scale = 8;

	/**
	 * Zooms into the scene.
	 */
	public static void zoomIn() {
		++scale;
	}
	
	/**
	 * Zooms out of the scene.
	 */
	public static void zoomOut() {
		if (scale > 4) --scale;
	}
	
	
	/**
	 * Calculate the move step according to the current zoom.
	 */
	private static double getMoveStepInUnits() {
		double delta = (double) MOVE_STEP / (1 << scale);
		return delta;
	}
	
	
	/**
	 * Moves the camera up.
	 */
	public static void moveUp() {
		center.setLocation(center.getX(), center.getY() + getMoveStepInUnits());
	}
	
	/**
	 * Moves the camera down.
	 */
	public static void moveDown() {
		center.setLocation(center.getX(), center.getY() - getMoveStepInUnits());
	}
	
	/**
	 * Moves the camera left.
	 */
	public static void moveLeft() {
		center.setLocation(center.getX() - getMoveStepInUnits(), center.getY());
	}
	
	/**
	 * Moves the camera right.
	 */
	public static void moveRight() {
		center.setLocation(center.getX() + getMoveStepInUnits(), center.getY());
	}


	/**
	 * Set the center of the camera on the complex plane.
	 */
	public static void setCenter(double x, double y) {
		Camera.center.setLocation(x, y);
	}
	

	/**
	 * Get the center of the camera on the complex plane.
	 */
	public static Point2D getCenter() {
		return center;
	}
	
	/**
	 * Set the scale of the camera view. 
	 * @param scale log2(pixels) per unit in the complex plane. 
	 */
	public static void setScale(int scale) {
		Camera.scale = scale;
	}
	
	/**
	 * Get the scale of the camera view.
	 * @return log2(pixels) per unit in the complex plane. 
	 */
	public static int getScale() {
		return scale;
	}
}
