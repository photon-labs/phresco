package com.photon.phresco.framework.model;

public class TestResult {

	int time;
	int latencyTime;
    String timeStamp;
    boolean success;
    String label;
    String threadName;
    
    public int getTime() {
        return time;
    }
    
    public void setTime(int time) {
        this.time = time;
    }
    
    public int getLatencyTime() {
        return latencyTime;
    }

    public void setLatencyTime(int latencyTime) {
        this.latencyTime = latencyTime;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }
    
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getThreadName() {
        return threadName;
    }
    
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
    
}
