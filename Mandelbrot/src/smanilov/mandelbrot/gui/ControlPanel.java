package smanilov.mandelbrot.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import smanilov.mandelbrot.compute.Camera;

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
	
	public ControlPanel() {
		super(true);
		
		setLayout(new BorderLayout());
		JPanel top = createTopRow();
		add(top, BorderLayout.NORTH);
		
		JPanel bottom = createBottomRow();
		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Creates the top row of buttons and returns them.
	 */
	private JPanel createTopRow() {
		JPanel top = new JPanel(new BorderLayout());
		
		zoomOutButton = new JButton();
		zoomOutButton.setText("-");
		zoomOutButton.addActionListener(new ZoomOutButtonListener());
		top.add(zoomOutButton, BorderLayout.WEST);
		
		moveUpButton = new JButton();
		moveUpButton.setText("^");
		moveUpButton.addActionListener(new MoveUpButtonListener());
		top.add(moveUpButton, BorderLayout.CENTER);

		zoomInButton = new JButton();
		zoomInButton.setText("+");
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
	 * Called when a button is used. Signals the parent.
	 */
	private void used() {
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
}
