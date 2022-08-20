package eutil.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Provides a mechanism for performing an action on a swing component property change.
 * 
 * @author Hunter Bragg
 */
public class ChangeListener implements PropertyChangeListener {

	protected Runnable action;
	
	public ChangeListener(Runnable actionIn) {
		action = actionIn;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		action.run();
	}
	
	public static ChangeListener of(Runnable actionIn) {
		return new ChangeListener(actionIn);
	}
	
}
