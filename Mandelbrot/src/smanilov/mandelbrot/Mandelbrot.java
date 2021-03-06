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

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import smanilov.mandelbrot.compute.Computer;
import smanilov.mandelbrot.gui.CanvasPanel;
import smanilov.mandelbrot.gui.ColorsPanel;
import smanilov.mandelbrot.gui.ControlPanel;

/**
 * The main class. Constructs the GUI.
 * 
 * TODO:
 * 	 1. Add a key listener for keyboard control.
 * @author szm
 */
public class Mandelbrot {

	private static JFrame mainFrame;
	private static JFrame controlFrame;
	private static JFrame colorsFrame;
	
	private static CanvasPanel canvas;
	private static ControlPanel control;
	
	private static JProgressBar progressBar;
	
	/**
	 * Creates the GUI elements and links them together.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		// Add the sticky behavior of the settings window.
		mainFrame = new JFrame("Explore Mandelbrot") {

			@Override
			protected void processEvent(AWTEvent e) {
				if (
						e.getID() == ComponentEvent.COMPONENT_MOVED || 
						e.getID() == ComponentEvent.COMPONENT_RESIZED
				) {
					stickControlFrameToCanvasFrame();
				}
				super.processEvent(e);
			}
		};
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Setup the content pane.
		JPanel mainContentPane = new JPanel(new BorderLayout());
		progressBar = new JProgressBar(0, 1000000000);
		progressBar.setForeground(Colors.getColor());
		
		canvas = new CanvasPanel();
		canvas.addPropertyChangeListener("redrawn", new CanvasPanelRedrawnListener());
//		canvas.addKeyListener(new GlobalKeyListener()); TODO
		
		mainContentPane.add(canvas, BorderLayout.CENTER);
		mainContentPane.add(progressBar, BorderLayout.SOUTH);
		mainFrame.setContentPane(mainContentPane);
		
		// Setting the size.
		mainFrame.pack();
		mainFrame.setMinimumSize(mainFrame.getSize());
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize((int)(d.getWidth() * 0.681), (int)(d.getHeight() * 0.681));
		mainFrame.setLocationRelativeTo(null);

		mainFrame.setVisible(true);
		
		// Setting the control frame.
		controlFrame = new JFrame("Settings Mandelbrot");
		
		control = new ControlPanel();
		control.addPropertyChangeListener("used", new ControlPanelUsedListener());
		control.addPropertyChangeListener("colors", new ControlPanelColorListener());
//		control.addKeyListener(new GlobalKeyListener());
		
		controlFrame.add(control);
		controlFrame.pack();
		controlFrame.setResizable(false);
		
		stickControlFrameToCanvasFrame();
		
		controlFrame.setVisible(true);
	}
	
	/**
	 * Displays the frame that allows color selection.
	 */
	private static void openColorsFrame() {
		colorsFrame = new JFrame();
		ColorsPanel cp = new ColorsPanel();
		cp.addPropertyChangeListener("colorSet", new ColorPanelColorSetListener());
		colorsFrame.add(cp);
		colorsFrame.pack();
		colorsFrame.setVisible(true);
	}

	private static void stickControlFrameToCanvasFrame() {
		if (controlFrame != null)
			controlFrame.setLocation(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());
	}	
	
	private static void redrawCanvas() {
		canvas.setRedraw(true);
		canvas.repaint();
	}

	/**
	 * Invoked when the visible image is changed.
	 */
	private static class CanvasPanelRedrawnListener implements PropertyChangeListener {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			progressBar.setValue((int) (progressBar.getMaximum() * Computer.getProgress()));
		}
	}
	
	/**
	 * Invoked when a button of the control panel, which effect requires a redrawing of the 
	 * visible image, is pressed. This pattern is used so the ControlPanel does not depend
	 * on the main screen. 
	 */
	private static class ControlPanelUsedListener implements PropertyChangeListener {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			redrawCanvas();
		}
	}

	/**
	 * Invoked when the Colors button of the ControlPanel is pressed.
	 */
	private static class ControlPanelColorListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			openColorsFrame();
		}

	}

	/**
	 * Invoked when a new color is selected as foreground.
	 */
	private static class ColorPanelColorSetListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			progressBar.setForeground(Colors.getColor());
			redrawCanvas();
		}
		
	}
}
