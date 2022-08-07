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

	protected ISwingAction action;
	
	public ActionPerformer(ISwingAction actionIn) {
		action = actionIn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		action.runAction();
	}
	
	public static ActionPerformer of(ISwingAction actionIn) {
		return new ActionPerformer(actionIn);
	}
	
}
