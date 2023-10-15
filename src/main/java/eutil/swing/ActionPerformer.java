package eutil.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

/**
 * Provides a mechanism for inserting ActionPerformers to perform a
 * lambda action in swing.
 * 
 * @author Hunter Bragg
 */
public class ActionPerformer implements ActionListener {

	protected Runnable action;
	
	public ActionPerformer(Runnable actionIn) {
		action = actionIn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		action.run();
	}
	
	public static ActionPerformer of(Runnable actionIn) {
		return new ActionPerformer(actionIn);
	}
	
	/**
	 * A one line lambda that 'applies' the given action to the given component.
	 * In this case, an AbstractButton.
	 * 
	 * @param component
	 * @param action
	 * 
	 * @return The created ActionPerformer instance
	 * 
	 * @since 2.6.1
	 */
	public static ActionPerformer applyOn(AbstractButton component, Runnable action) {
	    var ap = new ActionPerformer(action);
	    component.addActionListener(ap);
	    return ap;
	}
	
}
