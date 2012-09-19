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
package smanilov.mandelbrot.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import smanilov.mandelbrot.Colors;
import smanilov.mandelbrot.compute.Camera;
import smanilov.mandelbrot.compute.Computer;

/**
 * A custom panel used for drawing the Mandelbrot set on.
 * @author szm
 */
public class CanvasPanel extends JPanel {
		
	/**
	 * The current render of the Mandelbrot set.
	 */
	private Image drawing;
	
	/**
	 * A lock for the image.
	 */
	private ReentrantLock drawingLock;
	
	/**
	 * Indicates if the drawing should be redrawn.
	 */
	private boolean redraw;
	

	public CanvasPanel() {
		super(true);
		drawing = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		redraw = true;
		
		drawingLock = new ReentrantLock(true);
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				repaint();
			}
		}, 0, 50);
	}
	
		
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		if (redraw) {
			redraw = false;
			Computer.drawMandelbrotCrop(
					drawing,
					Colors.getColor(),
					Color.BLACK,
					drawingLock,
					Camera.getScale(), 
					Camera.getCenter()
			);
		}
		
		drawingLock.lock();
		graphics.drawImage(drawing, 0, 0, null);
		drawingLock.unlock();
		
		firePropertyChange("redrawn", false, true);
		
		for (Component child : getComponents()) {
			child.repaint();
		}
	}

	private void resizeDrawing(int width, int height) {
		if ((drawing.getWidth(null) != width || drawing.getHeight(null) != height) &&
			width > 0 && 
			height > 0
		) {
			drawing = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			redraw = true;
		}
	}
	
	/**
	 * Sets the redraw flag that indicates if the drawing should be regenerated.
	 */
	public void setRedraw(boolean redraw) {
		this.redraw = redraw;
	}
	
	@Override
	public void setSize(int width, int height) {
		resizeDrawing(width, height);
		super.setSize(width, height);
	}

	@Override
	public void setSize(Dimension d) {
		resizeDrawing(d.width, d.height);
		super.setSize(d);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		resizeDrawing(width, height);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setBounds(Rectangle r) {
		resizeDrawing(r.width, r.height);
		super.setBounds(r);
	}
}
