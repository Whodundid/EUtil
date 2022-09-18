package eutil.misc;

/**
 * A timer that counts down for a given duration once started.
 * 
 * @author Hunter Bragg
 * @since 1.7.0
 */
public class ETimer {
	
	//--------
	// Fields
	//--------
	
	private long startTime;
	private long duration;
	private boolean isCounting = false;
	private boolean hasRun = false;
	
	//--------------
	// Constructors
	//--------------
	
	public ETimer() {}
	public ETimer(long countdownIn) {
		duration = countdownIn;
	}
	
	//---------
	// Methods
	//---------
	
	/**
	 * Resets and starts counting down this timer.
	 */
	public void start() {
		startTime = System.currentTimeMillis();
		isCounting = true;
		hasRun = true;
	}
	
	/**
	 * Stops this counter (Cannot be resumed: Must be restarted).
	 */
	public void stop() {
		isCounting = false;
	}
	
	/**
	 * Returns true if this timer has reached its duration.
	 */
	public boolean check() {
		if (isCounting && System.currentTimeMillis() - startTime >= duration) {
			isCounting = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the values on this timer.
	 */
	public void reset() {
		startTime = System.currentTimeMillis();
		isCounting = false;
		hasRun = false;
	}
	
	//---------
	// Getters
	//---------
	
	/** Returns the time (in ms) that this timer will count down for. */
	public long getDuration() { return duration; }
	/** Return true if this timer is actively counting down. */
	public boolean isCounting() { return isCounting; }
	/** Returns true if this timer has ever been started. */
	public boolean hasStarted() { return hasRun; }
	
	/**
	 * Returns the current running time of this timer.
	 */
	public long getProgress() {
		if (!isCounting) return 0;
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Returns the time (in ms) that this timer was started.
	 * <p>
	 * NOTE: If this timer was never started, 0 will be returned instead.
	 * 
	 * @return The time (in ms) that this timer was started.
	 */
	public long getStartTime() {
		return startTime;
	}
	
	//---------
	// Setters
	//---------
	
	/**
	 * Sets the running duration of this timer.
	 * <p>
	 * Note: Could behave unexpectedly if modified while actively counting.
	 * 
	 * @param durationIn The amount of time to run for
	 */
	public void setDuration(long durationIn) {
		duration = durationIn;
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/**
	 * Returns true if any of the given timers are actively counting down.
	 * 
	 * @param timers The timers to check
	 * @return True if any of the given timers are counting
	 */
	public static boolean anyCounting(ETimer... timers) {
		if (timers.length == 0) return false;
		for (ETimer t : timers)
			if (t != null && t.isCounting)
				return true;
		return false;
	}
	
}
