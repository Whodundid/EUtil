package eutil.swing;

import java.awt.Component;
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
	public void onLeftPress(MouseEvent e) {
		action.run();
	}
	
	public static LeftClick of(Runnable actionIn) {
		return new LeftClick(actionIn);
	}
	
	public static LeftClick applyOn(Component c, Runnable action) {
	    var lc = new LeftClick(action);
	    c.addMouseListener(lc);
	    return lc;
	}
	
}
