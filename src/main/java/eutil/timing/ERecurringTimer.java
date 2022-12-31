package eutil.timing;

/**
 * A type of ETimer that runs continuously.
 * <p>
 * Like a normal ETimer, an ERecurringTimer can be stopped at any time
 * during its execution by simply calling the {@code stop} method. This
 * will stop the timer from executing or until restarted.
 * 
 * @author Hunter Bragg
 * @since 1.7.1
 */
public class ERecurringTimer extends ETimer {
	
	//--------------
	// Constructors
	//--------------
	
	public ERecurringTimer() { this(null, 0l); }
	public ERecurringTimer(long countdownIn) { this(null, countdownIn); }
	public ERecurringTimer(String nameIn) { this(nameIn, 0l); }
	public ERecurringTimer(String nameIn, long countdownIn) {
		timerName = nameIn;
		duration = countdownIn;
	}
	
	public ERecurringTimer(ETimerListener... listeners) { this(null, 0l); }
	public ERecurringTimer(long countdownIn, ETimerListener... listeners) { this(null, countdownIn, listeners); }
	public ERecurringTimer(String nameIn, ETimerListener... listeners) {}
	public ERecurringTimer(String nameIn, long countdownIn, ETimerListener... listeners) {
		timerName = nameIn;
		duration = countdownIn;
		addListeners(listeners);
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	protected void start_internal() {
		managed = ETimerInternalManager.schedule(this, true);
	}
	
	@Override
	protected void onFinished() {
		finishTime = System.currentTimeMillis();
		startTime = finishTime;
		
		//notify listeners
		for (var l : listeners)
			l.onTimerEnd(this, finishTime, !interrupted);
	}
	
	@Override
	protected void cancelTask() {
		ETimerInternalManager.onTaskEnd(this, true);
	}
	
}
