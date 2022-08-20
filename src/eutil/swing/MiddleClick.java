package eutil.swing;

import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing middle mouse click.
 * 
 * @author Hunter Bragg
 */
public class MiddleClick extends SimpleMouseListener {
	
	Runnable action;
	
	public MiddleClick(Runnable actionIn) {
		action = actionIn;
	}
	
	@Override
	public void onMiddleClick(MouseEvent e) {
		action.run();
	}
	
	public static MiddleClick of(Runnable actionIn) {
		return new MiddleClick(actionIn);
	}
	
}
