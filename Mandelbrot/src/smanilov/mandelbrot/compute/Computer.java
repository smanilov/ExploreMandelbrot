package smanilov.mandelbrot.compute;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.math3.complex.Complex;

/**
 * Computes the points from the Mandelbrot set.
 * @author szm
 */
public class Computer {

	/**
	 * Amount of iterations of the sequence used to decide if a point belongs 
	 * to the Mandelbrot set. Ideally this would be infinity. The sequence is
	 * [z_0 = 0, z_n = z_(n-1) ^ 2 + c], where c is the point to test. Each
	 * z_k where k < ITERATIONS should be <= 2. 
	 */
	private static final int ITERATIONS = 100;
	
	/**
	 * Returns a crop view of the mandelbrot set.
	 * @param drawing the image to draw on.
	 * @param foregroundColor the color to use when drawing on the image.
	 * @param drawingLock the lock to use for locking the image.
	 * @param center Center of the crop, in units of the complex plane, w.r.t. the origin.
	 * @param scale log2(pixels) per unit in the complex plane. 
	 */
	public static void drawMandelbrotCrop(
			final Image drawing,
			final Color foregroundColor,
			final ReentrantLock drawingLock,
			final int scale, 
			final Point2D center
	) {
		new Thread() {

			@Override
			public void run() {
				super.run();
				int width = drawing.getWidth(null);
				int height = drawing.getHeight(null);
				Point pixelCenter = new Point(width / 2, height / 2);
				ArrayList <Point> result = new ArrayList<Point> ();
				for (int i = 0; i < width; ++i) {
					
					for (int j = 0; j < height; ++j) {
						
						Complex c = toComplex(i, j, pixelCenter, scale, center);
						Complex z = new Complex(0.0, 0.0);
						int k;
						for (k = 0; k < ITERATIONS; ++k) {
							z = z.multiply(z).add(c);
							if (z.abs() > 2)
								break;
						}
						if (k == ITERATIONS) {
							result.add(new Point(i, j));
							drawingLock.lock();
							drawing.getGraphics().fillRect(i, j, 1, 1);
							drawingLock.unlock();
						}
					}
				}
			}
			
		}.start();
	}

	/**
	 * Converts a pixel coordinate into a complex number.
	 * @param pixelCenter The center of the pixel coordinate system.
	 * @param scale log2(pixels) per unit in the complex plane. 
	 * @param center The center of the view on the complex plane.
	 */
	private static Complex toComplex(int x, int y, Point pixelCenter, int scale, Point2D center) {
		int dx = (int) (x - pixelCenter.getX());
		int dy = (int) (- y + pixelCenter.getY());
		double real = (double) dx / (1 << scale) + center.getX();
		double imaginary = (double) dy / (1 << scale) + center.getY();
		return new Complex(real, imaginary);
	}
	
	/**
	 * Turns a complex number to pixel coordinates.
	 * @param c The complex number to convert.
	 * @param scale The scale, see other places.
	 * @param center The center of the view on the complex plane.
	 */
	private static Point toAbsolutePixel(Complex c, int scale, Point2D center) {
		int x = (int) (c.getReal() * (1 << scale));
		int y = (int) (-c.getImaginary() * (1 << scale));
		return new Point(x, y);
	}
}
