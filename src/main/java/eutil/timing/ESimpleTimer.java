package eutil.timing;

public class ESimpleTimer {
	
	/** The time (in ms) this timer was started. */
	protected long startTime;
	/** The length of time (in ms) this timer will run for. */
	protected long duration;
	/** True if the timer is actively running. */
	protected boolean isCounting = false;
	/** True if the timer has ever been started. */
	protected boolean hasRun = false;
	/** True if the timer was stopped. */
	protected boolean interrupted = false;
	
	//--------------
	// Constructors
	//--------------
	
	public ESimpleTimer() { this(0l); }
	public ESimpleTimer(long countdownIn) {
		duration = countdownIn;
	}
	
	//---------
	// Methods
	//---------
	
	/**
	 * Resets and starts counting down this timer.
	 */
	public ESimpleTimer start() {
		startTime = System.currentTimeMillis();
		isCounting = true;
		hasRun = true;
		interrupted = false;
		return this;
	}
	
	public ESimpleTimer stop() {
		isCounting = false;
		interrupted = true;
		return this;
	}
	
	/** Returns true if this timer has finished counting down. */
	public boolean isFinished() {
		if (isCounting && !interrupted && (System.currentTimeMillis() - startTime >= duration)) {
			isCounting = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the values on this timer.
	 */
	public void reset() {
		stop();
		hasRun = false;
		isCounting = false;
		interrupted = false;
		startTime = -1;
	}
	
	/**
	 * Returns the current amount of time (in ms) that this timer has been
	 * running for.
	 * <p>
	 * Note: If the timer isn't counting (or was never started), then 0 is
	 * returned instead.
	 */
	public long getRunTime() {
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
	
	/** Returns the time (in ms) that this timer will count down for. */
	public long getDuration() { return duration; }
	/** Return true if this timer is actively counting down. */
	public boolean isStarted() { return isCounting; }
	/** Returns true if this timer has <u>ever</u> been started. */
	public boolean hasEverRun() { return hasRun; }
	
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
	public static boolean anyCounting(ESimpleTimer... timers) {
		if (timers.length == 0) return false;
		for (ESimpleTimer t : timers)
			if (t != null && t.isCounting)
				return true;
		return false;
	}
	
}
