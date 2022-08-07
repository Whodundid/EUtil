package eutil.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Provides a mechanism for performing an action on a swing component property change.
 * 
 * @author Hunter Bragg
 */
public class ChangeListener implements PropertyChangeListener {

	protected ISwingAction action;
	
	public ChangeListener(ISwingAction actionIn) {
		action = actionIn;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		action.runAction();
	}
	
	public static ChangeListener of(ISwingAction actionIn) {
		return new ChangeListener(actionIn);
	}
	
}
