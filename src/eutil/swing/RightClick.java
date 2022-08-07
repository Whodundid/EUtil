package eutil.swing;

import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing right click.
 * 
 * @author Hunter Bragg
 */
public class RightClick extends SimpleMouseListener {
	
	ISwingAction action;
	
	public RightClick(ISwingAction actionIn) {
		action = actionIn;
	}
	
	@Override
	public void onRightClick(MouseEvent e) {
		action.runAction();
	}
	
	public static RightClick of(ISwingAction actionIn) {
		return new RightClick(actionIn);
	}
	
}
