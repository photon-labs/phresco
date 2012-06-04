package com.photon.phresco.framework.model;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTestResult {

	int min;
	int max;
	int err;
	int lastTime;
	double avgBytes;
	int noOfSamples;
	long totalTime;
	long totalBytes;
	double maxTs;
	double minTs;
	double avg;
	double stdDev;
	double throughtPut;
	double kbPerSec;
	String label;
	List<Integer> times = new ArrayList<Integer>(100);
	double totalStdDev;
	double totalThroughput;
	
	public double getTotalThroughput() {
		return totalThroughput;
	}

	public void setTotalThroughput(double totalThroughput) {
		this.totalThroughput = totalThroughput;
	}

	public double getTotalStdDev() {
		return totalStdDev;
	}

	public void setTotalStdDev(double totalStdDev) {
		this.totalStdDev = totalStdDev;
	}

	public List<Integer> getTimes() {
		return times;
	}

	public void setTimes(List<Integer> times) {
		this.times = times;
	}

	public String getLabel() {
		return label;
	}
	
	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int time) {
		this.lastTime = time;
	}
	
	public int getNoOfSamples() {
		return noOfSamples;
	}
	
	public void setNoOfSamples(int noOfSamples) {
		this.noOfSamples = noOfSamples;
	}
	
	public double getAvg() {
		return avg;
	}
	
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	public int getMin() {
		return min;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public double getStdDev() {
		return stdDev;
	}
	
	public void setStdDev(double stdDev) {
		this.stdDev = stdDev;
	}
	
	public int getErr() {
		return err;
	}
	
	public void setErr(int err) {
		this.err = err;
	}
	
	public double getThroughtPut() {
		return throughtPut;
	}
	
	public void setThroughtPut(double throughPut) {
		this.throughtPut = throughPut;
	}
	
	public double getKbPerSec() {
		return kbPerSec;
	}
	
	public void setKbPerSec(Double calKbPerSec) {
		this.kbPerSec = calKbPerSec;
	}
	
	public double getAvgBytes() {
		return avgBytes;
	}
	
	public void setAvgBytes(double avgBytes2) {
		this.avgBytes = avgBytes2;
	}

	public double getMaxTs() {
		return maxTs;
	}

	public void setMaxTs(double timeStamp) {
		this.maxTs = timeStamp;
	}

	public double getMinTs() {
		return minTs;
	}

	public void setMinTs(double timeStamp) {
		this.minTs = timeStamp;
	}
	
	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
}
