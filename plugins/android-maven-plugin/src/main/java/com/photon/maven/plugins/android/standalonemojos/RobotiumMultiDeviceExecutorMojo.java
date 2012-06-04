/*
 * ###
 * Android Maven Plugin - android-maven-plugin
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
/*
 * Copyright (C) 2009 Jayway AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photon.maven.plugins.android.standalonemojos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.testrunner.IRemoteAndroidTestRunner;
import com.android.ddmlib.testrunner.RemoteAndroidTestRunner;
import com.photon.maven.plugins.android.AbstractInstrumentationMojo;
import com.photon.maven.plugins.android.DeviceCallback;
import com.photon.maven.plugins.android.common.DeviceHelper;

/**
 * MultiDeviceExecutorMojo helps to connect to all selected android devices
 * within separate threads, install the apk file on selected devices and start
 * the application.
 * 
 * @goal perftest
 * @author viral_b
 */
public class RobotiumMultiDeviceExecutorMojo extends
		AbstractInstrumentationMojo {
	/**
	 * @parameter expression="${deviceList}"
	 */
	private String deviceList = "";
	private String[] devicesListArr = null;
	private ArrayList<Thread> threadObj = new ArrayList<Thread>();
	
	private AndroidTestRunListener testRunListener;
	private ArrayList<String> xmlReportNameForConnectedDevices;

	public void execute() throws MojoExecutionException, MojoFailureException {
		checkDeviceList();
		// deployDependencies();
		instrument();
		joinThreads();
		
		testRunListener.writeJunitReportToAllTestsFile(xmlReportNameForConnectedDevices);
	};

	/**
	 * Check the deviceList parameter
	 */
	private void checkDeviceList() {
		try {
			if (StringUtils.isNotBlank(deviceList)) {
				if (StringUtils.indexOf(deviceList, ",") > -1) {
					devicesListArr = deviceList.split(",");
				} else {
					devicesListArr = new String[1];
					devicesListArr[0] = deviceList.trim();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getLog().info("checkDeviceList - Exception: " + e.getMessage());
		}
	}

	protected void instrument() throws MojoExecutionException,
			MojoFailureException {
		parseConfiguration();

		if (parsedInstrumentationPackage == null) {
			parsedInstrumentationPackage = extractPackageNameFromAndroidManifest(androidManifestFile);
		}

		if (parsedInstrumentationRunner == null) {
			parsedInstrumentationRunner = extractInstrumentationRunnerFromAndroidManifest(androidManifestFile);
		}

		// only run Tests in specific package
		packagesList = buildCommaSeparatedString(parsedPackages);
		packagesExists = StringUtils.isNotBlank(packagesList);

		if (parsedClasses != null) {
			classesExists = parsedClasses.size() > 0;
		} else {
			classesExists = false;
		}

		if (classesExists && packagesExists) {
			// if both packages and classes are specified --> ERROR
			throw new MojoFailureException(
					"packages and classes are mutually exclusive. They cannot be specified at "
							+ "the same time. Please specify either packages or classes. For details, "
							+ "see http://developer.android.com/guide/developing/testing/testing_otheride.html");
		}

		doWithDevices(new DeviceCallback() {
			public void doWithDevice(final IDevice device)
					throws MojoExecutionException, MojoFailureException {
				RemoteAndroidTestRunner remoteAndroidTestRunner = new RemoteAndroidTestRunner(
						parsedInstrumentationPackage,
						parsedInstrumentationRunner, device);

				if (packagesExists) {
					remoteAndroidTestRunner.setTestPackageName(packagesList);
					getLog().info(
							"Running tests for specified test packages: "
									+ packagesList);
				}

				if (classesExists) {
					remoteAndroidTestRunner.setClassNames(parsedClasses
							.toArray(new String[parsedClasses.size()]));
					getLog().info(
							"Running tests for specified test classes/methods: "
									+ parsedClasses);
				}

				remoteAndroidTestRunner.setDebug(parsedDebug);
				remoteAndroidTestRunner.setCoverage(parsedCoverage);
				remoteAndroidTestRunner.setLogOnly(parsedLogOnly);

				if (StringUtils.isNotBlank(parsedTestSize)) {
					IRemoteAndroidTestRunner.TestSize validSize = IRemoteAndroidTestRunner.TestSize
							.getTestSize(parsedTestSize);
					remoteAndroidTestRunner.setTestSize(validSize);
				}

				getLog().info(
						"Running instrumentation tests in "
								+ parsedInstrumentationPackage + " on "
								+ device.getSerialNumber() + " (avdName="
								+ device.getAvdName() + ")");
				try {
					testRunListener = new AndroidTestRunListener(
							project, device);
					remoteAndroidTestRunner.run(testRunListener);
					if (testRunListener.hasFailuresOrErrors()) {
						throw new MojoFailureException(
								"Tests failed on device.");
					}
					if (testRunListener.testRunFailed()) {
						throw new MojoFailureException(
								"Test run failed to complete: "
										+ testRunListener
												.getTestRunFailureCause());
					}
					if (testRunListener.threwException()) {
						throw new MojoFailureException(
								testRunListener.getExceptionMessages());
					}
				} catch (TimeoutException e) {
					throw new MojoExecutionException("timeout", e);
				} catch (AdbCommandRejectedException e) {
					throw new MojoExecutionException("adb command rejected", e);
				} catch (ShellCommandUnresponsiveException e) {
					throw new MojoExecutionException("shell command "
							+ "unresponsive", e);
				} catch (IOException e) {
					throw new MojoExecutionException("IO problem", e);
				}
			}
		});
	}

	protected void doWithDevices(final DeviceCallback deviceCallback)
			throws MojoExecutionException, MojoFailureException {

		final AndroidDebugBridge androidDebugBridge = initAndroidDebugBridge();

		if (androidDebugBridge.isConnected()) {
			waitForInitialDeviceList(androidDebugBridge);
			List<IDevice> devices = Arrays.asList(androidDebugBridge
					.getDevices());
			int numberOfDevices = devices.size();
			getLog().info(
					"Found "
							+ numberOfDevices
							+ " devices connected with the Android Debug Bridge");

			if (devices.size() > 0) {
			 	getLog().info(
						"android.device parameter not set, using all attached devices");
				for (final IDevice idevice : devices) {
					for (int i = 0; i < devicesListArr.length; i++) {
						if (devicesListArr[i].equalsIgnoreCase(idevice
								.getSerialNumber())) {
							if (xmlReportNameForConnectedDevices == null) {
								xmlReportNameForConnectedDevices = new ArrayList<String>();
							}
							xmlReportNameForConnectedDevices.add("TEST-"+DeviceHelper.getDescriptiveName(idevice)+".xml");
							startThread(deviceCallback, idevice);
							break;
						}
					}

				}
			} else {
				throw new MojoExecutionException("No online devices attached.");
			}
		} else {
			throw new MojoExecutionException(
					"Android Debug Bridge is not connected.");
		}
	}

	private void startThread(final DeviceCallback deviceCallback,
			final IDevice idevice) {
		try {
			Thread t = new RobitiumPerformanceTestRunner(deviceCallback,
					idevice);
			t.setName(idevice.getSerialNumber());
			t.start();
			threadObj.add(t);
//			getLog().info(t.getName() + ": thread started");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void joinThreads() {
		for (int i = 0; i < threadObj.size(); i++) {
			try {
				threadObj.get(i).join();
//				getLog().info(threadObj.get(i).getName() + ": thread joined");
			} catch (InterruptedException e) {
				getLog().info(
						threadObj.get(i).getName()
								+ ": joinThreads - Exception = "
								+ e.getLocalizedMessage());
			}
		}
	}

	private class RobitiumPerformanceTestRunner extends Thread {

		private DeviceCallback _deviceCallback;
		private IDevice _iDevice;

		public RobitiumPerformanceTestRunner(DeviceCallback deviceCallback,
				IDevice idevice) {
			this._deviceCallback = deviceCallback;
			this._iDevice = idevice;
		}

		public void run() {
			try {
				_deviceCallback.doWithDevice(_iDevice);
			} catch (MojoExecutionException e) {
				e.printStackTrace();
			} catch (MojoFailureException e) {
				e.printStackTrace();
			}
		}
	}
}
