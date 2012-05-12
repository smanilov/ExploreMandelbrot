package smanilov.mandelbrot;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import smanilov.mandelbrot.gui.CanvasPanel;
import smanilov.mandelbrot.gui.ControlPanel;

/**
 * The main class. Constructs the GUI.
 * 
 * TODO:
 * 	 1. Add a key listener for keyboard control.
 * @author szm
 */
public class Mandelbrot {
	
	public static JFrame canvasFrame; // TODO: back to private
	private static JFrame controlFrame;
	private static CanvasPanel canvas;
	private static ControlPanel control;
	
	/**
	 * Creates the GUI elements and links them together.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		canvasFrame = new JFrame("Explore Mandelbrot") {

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
		
		canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new CanvasPanel();
		
		canvasFrame.setContentPane(canvas);
		
		// Setting the size
		canvasFrame.pack();
		canvasFrame.setMinimumSize(canvasFrame.getSize());
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		canvasFrame.setSize((int)(d.getWidth() * 0.681), (int)(d.getHeight() * 0.681));
		canvasFrame.setLocationRelativeTo(null);

		canvasFrame.setVisible(true);
		
		controlFrame = new JFrame("Settings Mandelbrot");
		
		control = new ControlPanel();
		control.addPropertyChangeListener("used", new ControlPanelUsedListener());
		
		controlFrame.add(control);
		controlFrame.pack();
		controlFrame.setResizable(false);
		
		stickControlFrameToCanvasFrame();
		
		controlFrame.setVisible(true);
	}

	private static void stickControlFrameToCanvasFrame() {
		if (controlFrame != null)
			controlFrame.setLocation(canvasFrame.getX() + canvasFrame.getWidth(), canvasFrame.getY());
	}	
	
	private static class ControlPanelUsedListener implements PropertyChangeListener {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			canvas.setRedraw(true);
			canvas.repaint();
		}
	}
}
