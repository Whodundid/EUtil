package eutil.timing;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import eutil.datatypes.EArrayList;

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
	
	/** The user defined name of this timer. */
	protected String timerName;
	/** The time (in ms) this timer was started. */
	protected long startTime;
	/** The length of time (in ms) this timer will run for. */
	protected long duration;
	/** The time (in ms) that this timer ended. */
	protected long finishTime;
	/** True if the timer is actively running. */
	protected boolean isCounting = false;
	/** True if the timer has ever been started. */
	protected boolean hasRun = false;
	/** True if the timer completed counting down from its current start time. */
	protected boolean finished = false;
	/** True if the timer was stopped. */
	protected boolean interrupted = false;
	
	/** Internal timer reference that is used to stop this scheduled timer's runnable. */
	protected ScheduledFuture<?> managed = null;
	
	/** The set of listeners that will receive notifications when this timer is either stopped or finishes. */
	protected EArrayList<ETimerListener> listeners = new EArrayList<>();
	
	//--------------
	// Constructors
	//--------------
	
	public ETimer() { this(null, 0l); }
	public ETimer(long countdownIn) { this(null, countdownIn); }
	public ETimer(String nameIn) { this(nameIn, 0l); }
	public ETimer(String nameIn, long countdownIn) {
		timerName = nameIn;
		duration = countdownIn;
	}
	
	public ETimer(ETimerListener... listeners) { this(null, 0l); }
	public ETimer(long countdownIn, ETimerListener... listeners) { this(null, countdownIn, listeners); }
	public ETimer(String nameIn, ETimerListener... listeners) {}
	public ETimer(String nameIn, long countdownIn, ETimerListener... listeners) {
		timerName = nameIn;
		duration = countdownIn;
		addListeners(listeners);
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public String toString() {
		if (timerName != null) return timerName;
		return super.toString();
	}

	//------------------
	// Internal Methods
	//------------------
	
	/**
	 * The runnable task that will actually notify the ETimer once completed.
	 * 
	 * @return This timer's internal runnable task
	 * @since 1.7.1
	 */
	protected Runnable getTimerRunnable() {
		return () -> {
			onFinished();
		};
	}
	
	/**
	 * Called when the timer should be scheduled by the internal manager.
	 * @since 1.7.1
	 */
	protected void start_internal() {
		managed = ETimerInternalManager.schedule(this);
	}
	
	/**
	 * Called by this timer's internal runnable once it has finished.
	 * @since 1.7.1
	 */
	protected void onFinished() {
		finishTime = System.currentTimeMillis();
		isCounting = false;
		finished = true;
		
		//notify listeners
		for (var l : listeners)
			l.onTimerEnd(this, finishTime, !interrupted);
	}
	
	/**
	 * Used to cancel this actively scheduled timer.
	 * @since 1.7.1
	 */
	protected void cancelTask() {
		ETimerInternalManager.onTaskEnd(this, false);
	}
	
	//---------
	// Methods
	//---------
	
	/**
	 * Adds an object(s) that will receive an update once this ETimer has
	 * either finished counting down or was interrupted early.
	 * 
	 * @param listenersIn The object(s) to receive timer notifications
	 * @since 1.7.1
	 */
	public void addListeners(ETimerListener... listenersIn) {
		listeners.ensureCapacity(listeners.size() + listenersIn.length);
		for (var l : listenersIn) listeners.add(l);
	}
	
	/**
	 * Resets and starts counting down this timer.
	 */
	public ETimer start() {
		startTime = System.currentTimeMillis();
		isCounting = true;
		hasRun = true;
		finished = false;
		finishTime = -1;
		interrupted = false;
		start_internal();
		return this;
	}
	
	/**
	 * Stops this counter (Cannot be resumed: Must be restarted).
	 */
	public void stop() {
		isCounting = false;
		interrupted = true;
		
		//stop background thread if not already cancelled
		if (managed != null && !managed.isCancelled()) {
			finishTime = System.currentTimeMillis();
			isCounting = false;
			finished = true;
			
			managed.cancel(true);
			managed = null;
			cancelTask();
			
			//notify listeners
			for (var l : listeners)
				l.onTimerEnd(this, finishTime, !interrupted);
		}
	}
	
	/**
	 * Returns true if this timer has reached its duration.
	 * <p>
	 * Note: If this timer isn't actually counting (or was never started),
	 * false is returned instead.
	 */
	public boolean check() {
		if (isCounting && System.currentTimeMillis() - startTime >= duration) {
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
		interrupted = false;
		finished = false;
		startTime = -1;
		finishTime = -1;
	}
	
	//---------
	// Getters
	//---------
	
	/**
	 * Returns the list of objects that will receive a notification when this
	 * timer either finishes counting or is interrupted.
	 */
	public List<ETimerListener> getListeners() { return listeners; }
	
	/** Returns the time (in ms) that this timer will count down for. */
	public long getDuration() { return duration; }
	/** Return true if this timer is actively counting down. */
	public boolean isStarted() { return isCounting; }
	/** Returns true if this timer has finished counting down. */
	public boolean isFinished() { return finished; }
	/** Returns true if this timer has <u>ever</u> been started. */
	public boolean hasEverRun() { return hasRun; }
	/** Returns the user-assigned name of this timer -- could be null. */
	public String getTimerName() { return timerName; }
	
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
	
	/**
	 * Sets the name of this timer.
	 */
	public void setTimerName(String nameIn) {
		timerName = nameIn;
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
