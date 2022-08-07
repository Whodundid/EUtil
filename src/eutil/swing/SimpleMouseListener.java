package eutil.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Simplifies the creation of a MouseListener by overriding each
 * MouseListener action to a default 'do nothing' implementation.
 * <p>
 * This allows for a swing component to not need to declare every
 * single overrides method for the MouseListener on implementation.
 * 
 * @author Hunter Bragg
 */
public class SimpleMouseListener implements MouseListener {
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
}
