/*
 * ###
 * Archetype - phresco-nodejs-archetype
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
