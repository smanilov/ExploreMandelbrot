package smanilov.mandelbrot.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import smanilov.mandelbrot.Mandelbrot;
import smanilov.mandelbrot.compute.Camera;
import smanilov.mandelbrot.compute.Computer;

/**
 * Includes the controlling components - arrows, +, -
 * 
 * TODO:
 *  1. Basic mode
 *  	1. Color mixer for the color scheme
 *  2. Advanced
 *		1. Add iterations entry
 *		2. Shader threads entry
 *		3. Anti-aliasing entry
 *  
 * @author szm
 */
public class ControlPanel extends JPanel {
	
	private JButton zoomOutButton;
	private JButton moveUpButton;
	private JButton zoomInButton;
	private JButton moveLeftButton;
	private JButton moveDownButton;
	private JButton moveRightButton;
	
	private JTextField iterationsTextField;
	private JButton redrawButton;
	
	public ControlPanel() {
		super(true);

		setLayout(new BorderLayout());
		
		JPanel navigationPane = createNavigationPane();
		
		add(navigationPane, BorderLayout.NORTH);
		
		JPanel iterationsPane = createIterationsPane();
		add(iterationsPane, BorderLayout.CENTER);
		
		JButton resolutionary = new JButton("1366 x 768");
		resolutionary.addActionListener(new ResolutionaryButtonListener());
		add(resolutionary, BorderLayout.SOUTH);
//		iterationsTextField
	}

	private JPanel createNavigationPane() {
		JPanel navigationPane = new JPanel(new BorderLayout());
		
		JPanel top = createTopRow();
		navigationPane.add(top, BorderLayout.NORTH);
		
		JPanel bottom = createBottomRow();
		navigationPane.add(bottom, BorderLayout.SOUTH);
		
		return navigationPane;
	}
	
	private JPanel createIterationsPane() {
		JPanel iterationsPane = new JPanel(new BorderLayout());
		
		JLabel iterationsLabel = new JLabel("Iterations:");
		iterationsPane.add(iterationsLabel, BorderLayout.NORTH);
		
		iterationsTextField = new JTextField();
		iterationsPane.add(iterationsTextField, BorderLayout.CENTER);
		
		redrawButton = new JButton("Redraw");
		redrawButton.addActionListener(new RedrawButtonListener());
		iterationsPane.add(redrawButton, BorderLayout.SOUTH);
		
		return iterationsPane;
	}

	/**
	 * Creates the top row of buttons and returns them.
	 */
	private JPanel createTopRow() {
		JPanel top = new JPanel(new BorderLayout());
		
		zoomOutButton = new JButton("-");
		zoomOutButton.addActionListener(new ZoomOutButtonListener());
		top.add(zoomOutButton, BorderLayout.WEST);
		
		moveUpButton = new JButton("^");
		moveUpButton.addActionListener(new MoveUpButtonListener());
		top.add(moveUpButton, BorderLayout.CENTER);

		zoomInButton = new JButton("+");
		zoomInButton.addActionListener(new ZoomInButtonListener());
		top.add(zoomInButton, BorderLayout.EAST);
		
		return top;
	}
	
	/**
	 * Creates the bottom row of buttons and returns them.
	 */
	private JPanel createBottomRow() {
		JPanel bottom = new JPanel(new BorderLayout());
		
		moveLeftButton = new JButton();
		moveLeftButton.setText("<");
		moveLeftButton.addActionListener(new MoveLeftButtonListener());
		bottom.add(moveLeftButton, BorderLayout.WEST);

		moveDownButton = new JButton();
		moveDownButton.setText("v");
		moveDownButton.addActionListener(new MoveDownButtonListener());
		bottom.add(moveDownButton, BorderLayout.CENTER);
		
		moveRightButton = new JButton();
		moveRightButton.setText(">");
		moveRightButton.addActionListener(new MoveRightButtonListener());
		bottom.add(moveRightButton, BorderLayout.EAST);
		
		return bottom;
	}
	
	/**
	 * Called when a button is used. Sets the iterations of the Computer and 
	 * signals the parent.
	 */
	private void used() {
		String text = iterationsTextField.getText();
		
		int iterations = -1;
		try {
			iterations = Integer.parseInt(text);
		} catch (NumberFormatException exc) {
			iterationsTextField.setText("0");
		}
		
		if (iterations > 0)
			Computer.setIterations(iterations);
		firePropertyChange("used", false, true);
	}
	
	/**
	 * Tells the camera to zoom out, when the zoom out button is pressed.
	 * @author szm
	 */
	private class ZoomOutButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.zoomOut();
			used();
		}
	}
	
	/**
	 * Tells the camera to zoom in, when the zoom in button is pressed.
	 * @author szm
	 */
	private class ZoomInButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.zoomIn();
			used();
		}
	}
	
	/**
	 * Tells the camera to move up, when the move up button is pressed.
	 * @author szm
	 */
	private class MoveUpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.moveUp();
			used();
		}
	}
	
	/**
	 * Tells the camera to move down, when the move down button is pressed.
	 * @author szm
	 */
	private class MoveDownButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.moveDown();
			used();
		}
	}
	
	/**
	 * Tells the camera to move left, when the move left button is pressed.
	 * @author szm
	 */
	private class MoveLeftButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.moveLeft();
			used();
		}
	}
	
	/**
	 * Tells the camera to move right, when the move right button is pressed.
	 * @author szm
	 */
	private class MoveRightButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Camera.moveRight();
			used();
		}
	}
	
	/**
	 * Tells the parent to redraw the canvas.
	 * @author szm
	 */
	private class RedrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			used();
		}
	}
	
	/**
	 * Tells the parent to redraw the canvas.
	 * @author szm
	 */
	private class ResolutionaryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mandelbrot.canvasFrame.setBounds(
					-Mandelbrot.canvasFrame.insets().left, 
					-Mandelbrot.canvasFrame.insets().top, 
					1366 + Mandelbrot.canvasFrame.insets().left + Mandelbrot.canvasFrame.insets().right, 
					768 + Mandelbrot.canvasFrame.insets().top + Mandelbrot.canvasFrame.insets().bottom
			);
		}
	}
}
