package eutil.swing;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * Provides a mechanism for performing an action on a swing right click.
 * 
 * @author Hunter Bragg
 */
public class RightClick extends SimpleMouseListener {
    
    Runnable action;
    
    public RightClick(Runnable actionIn) {
        action = actionIn;
    }
    
    @Override
    public void onRightPress(MouseEvent e) {
        action.run();
    }
    
    public static RightClick of(Runnable actionIn) {
        return new RightClick(actionIn);
    }
    
    public static RightClick applyOn(Component c, Runnable action) {
        var lc = new RightClick(action);
        c.addMouseListener(lc);
        return lc;
    }
    
}
