package com.aeh;

import javax.realtime.RealtimeThread;


public class PObject {
	boolean isDedicatedFree;
	RealtimeThread dedicatedThread;
	volatile int count;
	

}
