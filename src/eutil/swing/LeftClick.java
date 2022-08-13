package eutil.swing;

import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing left mouse click.
 * 
 * @author Hunter Bragg
 */
public class LeftClick extends SimpleMouseListener {
	
	Runnable action;
	
	public LeftClick(Runnable actionIn) {
		action = actionIn;
	}
	
	@Override
	public void onLeftClick(MouseEvent e) {
		action.run();
	}
	
	public static LeftClick of(Runnable actionIn) {
		return new LeftClick(actionIn);
	}
	
}
