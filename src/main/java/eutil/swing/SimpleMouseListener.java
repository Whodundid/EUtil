package eutil.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Simplifies the creation of a MouseListener and MosueAdapter by
 * overriding each MouseListener action to a default 'do nothing'
 * implementation.
 * <p>
 * This allows for a swing component to not need to declare every
 * single overrides method for the MouseListener on implementation.
 * 
 * @author Hunter Bragg
 */
public class SimpleMouseListener implements MouseListener {
	
	public void onLeftClick(MouseEvent e) {}
	public void onRightClick(MouseEvent e) {}
	public void onMiddleClick(MouseEvent e) {}
	
	public void onLeftPress(MouseEvent e) {}
	public void onRightPress(MouseEvent e) {}
	public void onMiddlePress(MouseEvent e) {}
	
	public void onLeftRelease(MouseEvent e) {}
	public void onRightRelease(MouseEvent e) {}
	public void onMiddleRelease(MouseEvent e) {}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) onLeftClick(e);
		if (e.getButton() == MouseEvent.BUTTON2) onRightClick(e);
		if (e.getButton() == MouseEvent.BUTTON3) onMiddleClick(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) onLeftPress(e);
		if (e.getButton() == MouseEvent.BUTTON2) onRightPress(e);
		if (e.getButton() == MouseEvent.BUTTON3) onMiddlePress(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) onLeftRelease(e);
		if (e.getButton() == MouseEvent.BUTTON2) onRightRelease(e);
		if (e.getButton() == MouseEvent.BUTTON3) onMiddleRelease(e);
	}
	
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
}
