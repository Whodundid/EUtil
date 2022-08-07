package eutil.swing;

import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing left mouse click.
 * 
 * @author Hunter Bragg
 */
public class LeftClick extends SimpleMouseListener {
	
	ISwingAction action;
	
	public LeftClick(ISwingAction actionIn) {
		action = actionIn;
	}
	
	@Override
	public void onLeftClick(MouseEvent e) {
		action.runAction();
	}
	
	public static LeftClick of(ISwingAction actionIn) {
		return new LeftClick(actionIn);
	}
	
}
