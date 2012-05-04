package smanilov.mandelbrot;

import java.awt.Dimension;
import java.awt.Toolkit;
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
	
	private static JFrame frame;
	private static CanvasPanel canvas;
	private static ControlPanel control;
	
	/**
	 * Creates the GUI elements and links them together.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		frame = new JFrame("Explore Mandelbrot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new CanvasPanel();
		control = new ControlPanel();
		control.addPropertyChangeListener("used", new ControlPanelUsedListener());
		
		frame.setContentPane(canvas);
		frame.add(control);
		
		// Setting the size
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int)(d.getWidth() * 0.681), (int)(d.getHeight() * 0.681));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static class ControlPanelUsedListener implements PropertyChangeListener {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			canvas.setRedraw(true);
			canvas.repaint();
		}
	}
}
