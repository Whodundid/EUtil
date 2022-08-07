package eutil.swing;

/**
 * An arbitrary action to be performed by a swing component.
 * 
 * @author Hunter Bragg
 */
@FunctionalInterface
public interface ISwingAction {
	
	/**
	 * Called whenever the action is to be performed.
	 */
	void runAction();
	
}
