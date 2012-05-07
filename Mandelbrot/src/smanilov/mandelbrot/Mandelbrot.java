package smanilov.mandelbrot;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Component.BaselineResizeBehavior;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuBar;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.im.InputContext;
import java.awt.im.InputMethodRequests;
import java.awt.image.BufferStrategy;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.awt.peer.ComponentPeer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.accessibility.AccessibleContext;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.TransferHandler;

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
