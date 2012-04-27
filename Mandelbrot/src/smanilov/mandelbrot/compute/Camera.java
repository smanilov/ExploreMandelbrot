package smanilov.mandelbrot.compute;

import java.awt.Image;
import java.awt.geom.Point2D;

/**
 * Describes which part of the complex plane is visible.
 * @author szm
 */
/**
 * @author stas
 *
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
	 * Get the center of the camera on the complex plane.
	 */
	public static Point2D getCenter() {
		return center;
	}

	
	/**
	 * Gets the scale of the camera view. The value corresponds to
	 * log2(pixels) per unit in the complex plane. 
	 */
	public static int getScale() {
		return scale;
	}
}
