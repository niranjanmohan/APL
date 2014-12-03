package com.aeh.commonobjects;

import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.impl.DedicatedThread;
import com.aeh.thread.impl.DedicatedWatchDogImpl;



public class PObject {
	boolean isDedicatedFree;
	DedicatedThread dedicatedThread;
	DedicatedWatchDog dedicatedWatchDog;
	volatile int count;

	public PObject(int priority){
		isDedicatedFree = true;
		dedicatedThread = new DedicatedThread();
		dedicatedThread.setPriority(priority);
		dedicatedWatchDog = new DedicatedWatchDogImpl();
		dedicatedWatchDog.setPriority(priority);
		count =0;
	}
	
	
	public boolean isDedicatedFree() {
		return isDedicatedFree;
	}
	public void setDedicatedFree(boolean isDedicatedFree) {
		this.isDedicatedFree = isDedicatedFree;
	}
	public DedicatedThread getDedicatedThread() {
		return dedicatedThread;
	}
	public void setDedicatedThread(DedicatedThread dedicatedThread) {
		this.dedicatedThread = dedicatedThread;
	}
	public DedicatedWatchDog getDedicatedWatchDog() {
		return dedicatedWatchDog;
	}
	public void setDedicatedWatchDog(DedicatedWatchDog dedicatedWatchDog) {
		this.dedicatedWatchDog = dedicatedWatchDog;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}


}
