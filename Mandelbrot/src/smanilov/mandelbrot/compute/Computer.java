package smanilov.mandelbrot.compute;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	 * The identifier of the current producer thread. Used to stop old ones.
	 */
	private static int currentDrawingId = -1;
	
	private static Thread[] consumerThread;
	
	/**
	 * Returns a crop view of the Mandelbrot set.
	 * @param drawing the image to draw on.
	 * @param foregroundColor the color to use when drawing Mandelbrot pixels.
	 * @param backgroundColor the color to use when drawing non-Mandelbrot pixels.
	 * @param drawingLock the lock to use for locking the image.
	 * @param center Center of the crop, in units of the complex plane, w.r.t. the origin.
	 * @param scale log2(pixels) per unit in the complex plane. 
	 */
	public static void drawMandelbrotCrop(
			final Image drawing,
			final Color foregroundColor,
			final Color backgroundColor,
			final ReentrantLock drawingLock,
			final int scale, 
			final Point2D center
	) {
		++currentDrawingId;
		
		Thread pt = createProducerThread(drawing.getWidth(null), drawing.getHeight(null));
		pt.start();
		
		Thread sh = createShaderThread(drawing, foregroundColor, backgroundColor, drawingLock, scale, center);
		sh.start();
	}

	/**
	 * Creates a thread that fills the toDoList with points.
	 * @param width The maximum x coordinate.
	 * @param height The maximum y coordinate.
	 * @return
	 */
	private static Thread createProducerThread(final int width, final int height) {
		Thread producerThread = new Thread() {

			@Override
			public void run() {
				super.run();
				int id = currentDrawingId;
				
				boolean [][]added = new boolean[height][width];
				int nAdded = 0; // How many points were added in the toDoList
				int max = width * height;
				
				int x, y;
				while (nAdded < max) {
					if (id != currentDrawingId)
						return;
					x = (int) (width * Math.random());
					y = (int) (height * Math.random());
					if (!added[y][x]) {
						/* TODO: Actually add */
						++nAdded;
						added[y][x] = true;
					}
				}
			}
		};
		return producerThread;
	}

	/**
	 * Creates a thread that draws points from the toDoList.
	 * @param width The maximum x coordinate.
	 * @param height The maximum y coordinate.
	 * @return
	 */
	private static Thread createShaderThread(final Image drawing,
			final Color foregroundColor,
			final Color backgroundColor,
			final ReentrantLock drawingLock,
			final int scale, 
			final Point2D center) {
		Thread shaderThread = new Thread() {

			@Override
			public void run() {
				int id = currentDrawingId;
				super.run();
				
//				for (int r = 0; r < width * height; ++r) {
//				int i = xs.get(r / height % width);
//				int j = ys.get(r % height);
//				
//				Complex c = toComplex(i, j, pixelCenter, scale, center);
//				Complex z = new Complex(0.0, 0.0);
//				int k;
//				for (k = 0; k < ITERATIONS; ++k) {
//					z = z.multiply(z).add(c);
//					if (z.abs() > 2)
//						break;
//				}
//				if (k == ITERATIONS) {
//					drawingLock.lock();
//					Graphics g = drawing.getGraphics();
//					g.setColor(foregroundColor);
//					g.fillRect(i, j, 1, 1);
//					drawingLock.unlock();
//				} else {
//					drawingLock.lock();
//					Graphics g = drawing.getGraphics();
//					int v = k * 255 / ITERATIONS;
//					g.setColor(new Color(v, v, 0));
//					g.fillRect(i, j, 1, 1);
//					drawingLock.unlock();
//				}
			}
		};
		return shaderThread;
	}
	
	/**
	 * Converts a pixel coordinate into a complex number.
	 * TODO: use for memoization / re-use of old computations.
	 * @param pixelCenter The center of the pixel coordinate system.
	 * @param scale log2(pixels) per unit in the complex plane. 
	 * @param center The center of the view on the complex plane.
	 */
	@SuppressWarnings("unused")
	private static Complex toComplex(int x, int y, Point pixelCenter, int scale, Point2D center) {
		int dx = (int) (x - pixelCenter.getX());
		int dy = (int) (- y + pixelCenter.getY());
		double real = (double) dx / (1 << scale) + center.getX();
		double imaginary = (double) dy / (1 << scale) + center.getY();
		return new Complex(real, imaginary);
	}
}
