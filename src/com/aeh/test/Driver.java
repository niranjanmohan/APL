package com.aeh.test;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.AEHandlerImpl;
import com.aeh.thread.impl.DedicatedWatchDogImpl;
import com.aeh.thread.impl.ServerThreadImpl;
import com.aeh.commonobjects.AsyncEventWrapper;

public class Driver {
	public static void main(String []args){
		AEHHolder h = new AEHHolder(3,4,false);
		AsyncEventWrapper event3 = new AsyncEventWrapper(3, h);
		//AsyncEventWrapper event2 = new AsyncEventWrapper(2, h);
		AsyncEventWrapper event1 = new AsyncEventWrapper(1, h);
		
		for(int i =0 ;i<10; i++){
			AEHandler handler = new AEHandlerImpl("***Executing");
			event3.addHandler(handler);
		}
		
//		for(int i =0 ;i<10; i++){
//			AEHandler handler = new AEHandlerImpl("i will do this");
//			event2.addHandler(handler);
//		}
		
		for(int i =0 ;i<10; i++){
			AEHandler handler = new AEHandlerImpl("EX");
			event1.addHandler(handler);
		}
		
		
		event1.fire();
		//event2.fire();
		event3.fire();
		
		
		
		
//		
//		RThread rr = new RThread();
//		rr.setPriority(0);
//		rr.start();
		
		
		
//		ServerThreadImpl t1 = new ServerThreadImpl();
//		ServerThreadImpl t2 = new ServerThreadImpl();
//		t1.bindHandler(handler1);
//		t2.bindHandler(handler2);
//		t1.executeHandler();
//		t2.executeHandler();
		
	}

}
