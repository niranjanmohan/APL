package com.aeh.commonobjects;

//import java.util.concurrent.atomic.AtomicInteger;

import com.aeh.AEHHolder;
import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.impl.DedicatedThread;
import com.aeh.thread.impl.DedicatedWatchDogImpl;



public class PObject {
	boolean isDedicatedFree;
	DedicatedThread dedicatedThread;
	DedicatedWatchDog dedicatedWatchDog;
	public int count ;

	public PObject(int priority, AEHHolder h){
		isDedicatedFree = true;
		//dedicatedThread = new DedicatedThread();
		//dedicatedThread.setPriority(priority);
		dedicatedWatchDog = new DedicatedWatchDogImpl(h);
		dedicatedWatchDog.setPriority(priority);
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
	public DedicatedWatchDog getDedicatedWatchDog() {
		return dedicatedWatchDog;
	}
	public void setDedicatedWatchDog(DedicatedWatchDog dedicatedWatchDog) {
		this.dedicatedWatchDog = dedicatedWatchDog;
	}
}
