package com.aeh.commonobjects;

//import java.util.concurrent.atomic.AtomicInteger;

import com.aeh.AEHHolder;
import com.aeh.thread.impl.DedicatedThread;
import com.aeh.thread.impl.DedicatedWatchDogImpl;



public class PObject {
	boolean isDedicatedFree;
	DedicatedThread dedicatedThread;
	DedicatedWatchDogImpl watchDog;
	volatile public int count ;
	public Object lock = new Object();

	public PObject(int priority, AEHHolder h){
		count = 0;
		isDedicatedFree = true;
//		count = new AtomicInteger(0);
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
	public DedicatedWatchDogImpl getDedicatedWatchDog() {
		return watchDog;
	}
	public void setDedicatedWatchDog(DedicatedWatchDogImpl dedicatedWatchDog) {
		this.watchDog = dedicatedWatchDog;
	}
}
