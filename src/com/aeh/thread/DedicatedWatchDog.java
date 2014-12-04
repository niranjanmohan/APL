package com.aeh.thread;


public interface DedicatedWatchDog extends Runnable{
	public void initiateProcess();
	public void setPriority(int priority);
	public int getPriority();
}
