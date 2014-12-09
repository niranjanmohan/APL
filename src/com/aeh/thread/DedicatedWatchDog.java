package com.aeh.thread;


public interface DedicatedWatchDog{
	public void initiateProcess();
	public void setPriority(int priority);
	public int getPriority();
	public void start();
	public void wake();
	public void waitOn();
}
