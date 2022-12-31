package eutil.timing;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Internal class which manages ETimer runners.
 * 
 * @author Hunter Bragg
 * @since 1.7.1
 */
class ETimerInternalManager {
	
	//---------------
	// Static Fields
	//---------------
	
	/** The primary thread executor. */
	private static ScheduledExecutorService scheduler;
	/** The actively running ETimers. */
	private static Map<ETimer, Integer> activeTimers = new ConcurrentHashMap<>();
	
	//--------------
	// Constructors
	//--------------
	
	/** Hide Constructor */
	private ETimerInternalManager() {}
	
	//------------------
	// Internal Methods
	//------------------
	
	/**
	 * Schedules a timer to be executed once.
	 * 
	 * @param timer The timer to execute
	 * 
	 * @return The ScheduledFuture of the timer (used to control timer)
	 */
	protected static ScheduledFuture<?> schedule(ETimer timer) {
		return schedule(timer, false);
	}
	
	/**
	 * Schedules a timer to be executed potentially more than once.
	 * 
	 * @param timer The timer to execute
	 * 
	 * @return The ScheduledFuture of the timer (used to control timer)
	 */
	protected static ScheduledFuture<?> schedule(ETimer timer, boolean recurring) {
		if (timer == null) return null;
		
		//if there is no scheduler atm, create a new one
		if (scheduler == null) scheduler = Executors.newSingleThreadScheduledExecutor();
		
		Integer num = activeTimers.get(timer);
		if (num == null) activeTimers.put(timer, 1);
		else activeTimers.put(timer, num + 1);
		
		if (recurring) {
			return scheduler.scheduleAtFixedRate(new RunnerClass(timer),
												 timer.getDuration(),
												 timer.getDuration(),
												 TimeUnit.MILLISECONDS);
		}
		return scheduler.schedule(new RunnerClass(timer), timer.getDuration(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Manages currently tracked timers and determines whether or not to keep
	 * timer scheduler alive.
	 * 
	 * @param timer         The timer that finished
	 * @param stopRecurring Flag that indicates whether or not to stop
	 *                      recurring timers or not
	 */
	protected static void onTaskEnd(ETimer timer, boolean stopRecurring) {
		//remove non-recurring timers
		if (stopRecurring || !(timer instanceof ERecurringTimer)) {
			synchronized (activeTimers) {
				Integer num = activeTimers.get(timer);
				if (num == null) {
					/** IDK ??? */
					activeTimers.remove(timer);
				}
				else if (num == 1) activeTimers.remove(timer);
				else activeTimers.put(timer, num - 1);
			}
		}
		
		//check if there are any actively running 
		if (isEmpty()) {
			scheduler.shutdown();
			scheduler = null;
		}
	}
	
	/** Returns true if there are no timers. */
	private static boolean isEmpty() { return activeTimers.isEmpty(); }
	
	/** Stops and removes all actively running timers. */
	public static void shutdown() {
		scheduler.shutdown();
		activeTimers.clear();
	}
	
	//------------------
	// Internal Classes
	//------------------
	
	/**
	 * Wraps an ETimer to internally keep track of when the timer ends.
	 */
	private static class RunnerClass extends Thread {
		private ETimer timer;
		
		public RunnerClass(ETimer timerIn) {
			timer = timerIn;
		}
		
		@Override
		public void run() {
			timer.getTimerRunnable().run();
			//notify global timer
			onTaskEnd(timer, false);
		}
	}
	
}
