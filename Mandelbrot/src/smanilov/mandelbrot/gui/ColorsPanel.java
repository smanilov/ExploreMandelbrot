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
