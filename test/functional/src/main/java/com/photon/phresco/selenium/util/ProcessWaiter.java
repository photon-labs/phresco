package com.photon.phresco.selenium.util;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class ProcessWaiter {

	public static void waitFor(final Process proc, final long timeout)
			throws TimeoutException, InterruptedException {

		final Thread callingThread = Thread.currentThread();

		final long start = System.currentTimeMillis();
		
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				callingThread.interrupt();
			}
		}, timeout);

		try {
			proc.waitFor();
		} catch (final InterruptedException e) {
			proc.destroy();
			if (System.currentTimeMillis() >= start + timeout) {
				throw new TimeoutException("Process failed to finish within "
						+ timeout + " milliseconds");
			} else {
				throw e;
			}
		} finally {
			timer.cancel();
		}
	}

}
