package eutil.swing;

import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing middle mouse click.
 * 
 * @author Hunter Bragg
 */
public class MiddleClick extends SimpleMouseListener {
	
	ISwingAction action;
	
	public MiddleClick(ISwingAction actionIn) {
		action = actionIn;
	}
	
	@Override
	public void onMiddleClick(MouseEvent e) {
		action.runAction();
	}
	
	public static MiddleClick of(ISwingAction actionIn) {
		return new MiddleClick(actionIn);
	}
	
}
