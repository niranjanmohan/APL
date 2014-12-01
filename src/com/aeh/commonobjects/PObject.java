package com.aeh.commonobjects;

import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.impl.DedicatedThread;



public class PObject {
	boolean isDedicatedFree;
	DedicatedThread dedicatedThread;
	DedicatedWatchDog dedicatedWatchDog;
	volatile int count;
	

}
