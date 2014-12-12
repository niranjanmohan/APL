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
		AEHHolder h = new AEHHolder(2,4);
		AsyncEventWrapper event4 = new AsyncEventWrapper(3, h);
		AsyncEventWrapper event3 = new AsyncEventWrapper(2, h);
		AsyncEventWrapper event2 = new AsyncEventWrapper(1, h);
		AEHandler handler1 = new AEHandlerImpl("i will do this");
		AEHandler handler2 = new AEHandlerImpl("i will do this");
		AEHandler handler3 = new AEHandlerImpl("i will do this");
		AEHandler handler4 = new AEHandlerImpl("i will do this");
		AEHandler handler5 = new AEHandlerImpl("i will do this");
		event4.addHandler(handler3);
		event3.addHandler(handler1);
		event3.addHandler(handler2);
		event2.addHandler(handler4);
		event2.addHandler(handler5);
		event3.fire();
		event2.fire();
		event4.fire();
		
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
