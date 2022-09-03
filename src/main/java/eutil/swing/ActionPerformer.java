package eutil.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
}