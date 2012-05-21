package smanilov.mandelbrot.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import smanilov.mandelbrot.compute.Camera;
import smanilov.mandelbrot.compute.Computer;

/**
 * Includes the controlling components (arrows, +, -) and generation settings.
 * 
 * TODO:
 *  1. Basic mode
 *  	1. Color mixer for the color scheme
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
	
	private JTextField xCoordinateTextField;
	private JTextField yCoordinateTextField;
	private JTextField scaleTextField;
	private JButton currentPositionButton;
	
	private JTextField iterationsTextField;
	private JTextField shadersTextField;
	private JTextField antiAliasingTextField;
	
	private JButton redrawButton;
	private JButton colorsButton;
	
	public ControlPanel() {
		super(true);

		setLayout(new BorderLayout());
		
		JPanel navigationPane = createNavigationPane();
		add(navigationPane, BorderLayout.NORTH);
		
		JPanel entriesPane = createEntriesPane();
		add(entriesPane, BorderLayout.CENTER);
		
		JPanel buttonsPane = createButtonsPane();
		add(buttonsPane, BorderLayout.SOUTH);
	}

	private JPanel createNavigationPane() {
		JPanel navigationPane = new JPanel(new BorderLayout());
		
		JPanel top = createTopRow();
		navigationPane.add(top, BorderLayout.NORTH);
		
		JPanel bottom = createBottomRow();
		navigationPane.add(bottom, BorderLayout.SOUTH);
		
		return navigationPane;
	}
	
	private JPanel createEntriesPane() {
		JPanel entriesPane = new JPanel(new BorderLayout());
		
		JPanel navigationPane = createNavigationEntriesPane();
		entriesPane.add(navigationPane, BorderLayout.NORTH);		
		
		JPanel advancedPane = createAdvancedEntriesPane();
		entriesPane.add(advancedPane, BorderLayout.SOUTH);
		
		return entriesPane;
	}
	
	private JPanel createButtonsPane() {
		JPanel buttonsPane = new JPanel(new BorderLayout());
		
		redrawButton = new JButton("Redraw");
		redrawButton.addActionListener(new RedrawButtonListener());
		buttonsPane.add(redrawButton, BorderLayout.NORTH);
		
		colorsButton = new JButton("Colors");
		colorsButton.addActionListener(new ColorsButtonListener());
		buttonsPane.add(colorsButton, BorderLayout.SOUTH);

		return buttonsPane;
	}
	
	
	private JPanel createNavigationEntriesPane() {
		JPanel navigationPane = new JPanel(new BorderLayout());

		JPanel xCoordinatePane = createXCoordinatePane();
		navigationPane.add(xCoordinatePane, BorderLayout.NORTH);
		
		JPanel yCoordinatePane = createYCoordinatePane();
		navigationPane.add(yCoordinatePane, BorderLayout.CENTER);
		
		JPanel scalePane = createScalePane();
		navigationPane.add(scalePane, BorderLayout.SOUTH);
		return navigationPane;
	}
	
	private JPanel createAdvancedEntriesPane() {
		JPanel advancedPane = new JPanel(new BorderLayout());

		JPanel iterationsPane = createIterationsPane();
		advancedPane.add(iterationsPane, BorderLayout.NORTH);
		
		JPanel threadsPane = createShadersPane();
		advancedPane.add(threadsPane, BorderLayout.CENTER);
		
		JPanel antiAliasingPane = createAntiAliasingPane();
		advancedPane.add(antiAliasingPane, BorderLayout.SOUTH);
		return advancedPane;
	}

	
	private JPanel createXCoordinatePane() {
		JPanel xCoordinatePane = new JPanel(new BorderLayout());
		
		JLabel xLabel = new JLabel("X Coordinate:");
		xCoordinatePane.add(xLabel, BorderLayout.NORTH);
		
		xCoordinateTextField = new JTextField();
		xCoordinateTextField.setText("" + Camera.getCenter().getX());
		xCoordinatePane.add(xCoordinateTextField, BorderLayout.SOUTH);
				
		return xCoordinatePane;
	}
	
	private JPanel createYCoordinatePane() {
		JPanel yCoordinatePane = new JPanel(new BorderLayout());
		
		JLabel yLabel = new JLabel("Y Coordinate:");
		yCoordinatePane.add(yLabel, BorderLayout.NORTH);
		
		yCoordinateTextField = new JTextField();
		yCoordinateTextField.setText("" + Camera.getCenter().getY());
		yCoordinatePane.add(yCoordinateTextField, BorderLayout.SOUTH);
				
		return yCoordinatePane;
	}
	
	private JPanel createScalePane() {
		JPanel scalePane = new JPanel(new BorderLayout());
		
		JLabel yLabel = new JLabel("Scale:");
		scalePane.add(yLabel, BorderLayout.NORTH);
		
		scaleTextField = new JTextField();
		scaleTextField.setText("" + Camera.getScale());
		scalePane.add(scaleTextField, BorderLayout.CENTER);

		currentPositionButton = new JButton("Current Position");
		currentPositionButton.addActionListener(new CurrentPositionListener());
		scalePane.add(currentPositionButton, BorderLayout.SOUTH);		
		
		return scalePane;
	}
	
	
	private JPanel createIterationsPane() {
		JPanel iterationsPane = new JPanel(new BorderLayout());
		
		JLabel iterationsLabel = new JLabel("Iterations:");
		iterationsPane.add(iterationsLabel, BorderLayout.NORTH);
		
		iterationsTextField = new JTextField();
		iterationsTextField.setText("" + Computer.getIterations());
		iterationsPane.add(iterationsTextField, BorderLayout.SOUTH);
				
		return iterationsPane;
	}
	
	private JPanel createShadersPane() {
		JPanel shadersPane = new JPanel(new BorderLayout());
		
		JLabel shadersLabel = new JLabel("N.o. Shaders:");
		shadersPane.add(shadersLabel, BorderLayout.NORTH);
		
		shadersTextField = new JTextField();
		shadersTextField.setText("" + Computer.getShaders());
		shadersPane.add(shadersTextField, BorderLayout.SOUTH);
				
		return shadersPane;
	}
	
	private JPanel createAntiAliasingPane() {
		JPanel antiAliasingPane = new JPanel(new BorderLayout());
		
		JLabel antiAliasingLabel = new JLabel("Anti-aliasing:");
		antiAliasingPane.add(antiAliasingLabel, BorderLayout.NORTH);
		
		antiAliasingTextField = new JTextField();
		int t = Computer.getAntiAliasing();
		antiAliasingTextField.setText("" + t * t);
		antiAliasingPane.add(antiAliasingTextField, BorderLayout.SOUTH);
		
		return antiAliasingPane;
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
	
	
	private void resetNavigationEntries() {
		xCoordinateTextField.setText("" + Camera.getCenter().getX());
		yCoordinateTextField.setText("" + Camera.getCenter().getY());
		scaleTextField.setText("" + Camera.getScale());
	}
	
	
	/**
	 * Called when a button is used. Updates the settings of the Computer and 
	 * signals the parent.
	 */
	private void used() {
		setIterationsInComputer();
		setShadersInComputer();
		setAntiAliasingInComputer();
		
		firePropertyChange("used", false, true);
		
		resetNavigationEntries();
	}

	
	private void setCoordinatesInCamera() {
		double x = Double.NaN;
		try {
			String text = xCoordinateTextField.getText();
			x = Double.parseDouble(text);
		} catch (NumberFormatException exc) {
			xCoordinateTextField.setText("" + Camera.getCenter().getX());
		}
		
		double y = Double.NaN;
		try {
			String text = yCoordinateTextField.getText();
			y = Double.parseDouble(text);
		} catch (NumberFormatException exc) {
			yCoordinateTextField.setText("" + Camera.getCenter().getY());
		}
		
		if (!Double.isNaN(x) && !Double.isNaN(y)) {
			Camera.setCenter(x, y);
		}
	}

	private void setScaleInCamera() {
		int scale = 0;
		try {
			String text = scaleTextField.getText();
			scale = Integer.parseInt(text);
		} catch (NumberFormatException exc) {
			scaleTextField.setText("" + Camera.getScale());
		}
		if (scale > 0)
			Camera.setScale(scale);	
	}
	

	private void setIterationsInComputer() {
		int iterations = 0;
		try {
			String text = iterationsTextField.getText();
			iterations = Integer.parseInt(text);
		} catch (NumberFormatException exc) {
			iterationsTextField.setText("" + Computer.getIterations());
		}
		if (iterations > 0)
			Computer.setIterations(iterations);
	}
	
	private void setShadersInComputer() {
		int shaders = 0;
		try {
			String text = shadersTextField.getText();
			shaders = Integer.parseInt(text);
		} catch (NumberFormatException exc) {
			shadersTextField.setText("" + Computer.getShaders());
		}
		if (shaders > 0)
			Computer.setShaders(shaders);
	}
	
	private void setAntiAliasingInComputer() {
		int antiAliasing = 0;
		try {
			String text = antiAliasingTextField.getText();
			antiAliasing = Integer.parseInt(text);
			antiAliasing = (int)Math.sqrt(antiAliasing);
			antiAliasingTextField.setText("" + antiAliasing * antiAliasing);
		} catch (NumberFormatException exc) {
			int t = Computer.getAntiAliasing();
			antiAliasingTextField.setText("" + t * t);
		}
		
		if (antiAliasing > 0)
			Computer.setAntiAliasing(antiAliasing);
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
	private class CurrentPositionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			resetNavigationEntries();
//			Mandelbrot.canvas.setSize(3311, 4681);
		}
	}
	
	/**
	 * Tells the parent to redraw the canvas.
	 * @author szm
	 */
	private class RedrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setCoordinatesInCamera();
			setScaleInCamera();
			used();
		}
	}
	
	/**
	 * Tells the parent to open the color settings window.
	 * @author szm
	 */
	private class ColorsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			firePropertyChange("colors", false, true);
		}
	}
}
