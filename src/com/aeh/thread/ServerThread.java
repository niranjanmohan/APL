package com.aeh.thread;

public interface ServerThread {
	public void setThreadPriority(int priority);
	public int getHandlerPriority();
	public void start();
}
