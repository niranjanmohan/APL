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
		//AsyncEventWrapper event4 = new AsyncEventWrapper(3, h);
		AsyncEventWrapper event3 = new AsyncEventWrapper(2, h);
		AEHandler handler1 = new AEHandlerImpl("i will do this");
		AEHandler handler2 = new AEHandlerImpl("i will do that");
		//event4.addHandler(handler1);
		event3.addHandler(handler1);
		event3.addHandler(handler2);
		event3.fire();
		//event4.fire();
		
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
