package eutil.timing;

/**
 * An interface that allows an object to receive updates from ETimers when
 * they finish counting.
 * 
 * @author Hunter Bragg
 */
public interface ETimerListener {
	
	/**
	 * Called by the timer once it has finished it has either finished its
	 * count down or if it was interrupted early.
	 * 
	 * @param t                The timer that ended
	 * 
	 * @param endTime          The exact time that the timer finished
	 * 
	 * @param finishedNormally Indicates whether or not the timer fully
	 *                         completed ticking or if it was interrupted early
	 */
	void onTimerEnd(ETimer t, long endTime, boolean finishedNormally);
	
}
