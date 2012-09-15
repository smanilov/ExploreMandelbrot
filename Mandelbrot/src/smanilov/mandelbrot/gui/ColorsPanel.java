package smanilov.mandelbrot.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import smanilov.mandelbrot.Colors;

public class ColorsPanel extends JPanel{

	JButton getButton;
	
	JColorChooser chooser;
	
	JButton setButton;
	
	public ColorsPanel() {
		super(new BorderLayout());
		
		JPanel topButtons = new JPanel(new BorderLayout());
		
		getButton = new JButton("Get");
		getButton.addActionListener(new GetButtonListener());
		topButtons.add(getButton);
		add(topButtons, BorderLayout.NORTH);
		
		chooser = new JColorChooser();
		add(chooser, BorderLayout.CENTER);
		
		JPanel bottomButtons = new JPanel(new BorderLayout());
		
		setButton = new JButton("Use as Foreground ");
		setButton.addActionListener(new SetButtonListener());
		bottomButtons.add(setButton);
		add(bottomButtons, BorderLayout.SOUTH);
	}
	
	
	private class GetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			chooser.setColor(Colors.getColor());
		}
	}
	
	private class SetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Colors.setColor(chooser.getColor());
			firePropertyChange("colorSet", false, true);
		}
	}
}
