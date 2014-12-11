package com.aeh.test;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.AEHandlerImpl;
import com.aeh.thread.impl.ServerThreadImpl;
import com.aeh.commonobjects.AsyncEventWrapper;

public class Driver {
	public static void main(String []args){
		AEHHolder h = new AEHHolder(5,10);
		AsyncEventWrapper event = new AsyncEventWrapper(3, h);
		AEHandler handler1 = new AEHandlerImpl();
		AEHandler handler2 = new AEHandlerImpl();
		event.addHandler(handler1);
		event.addHandler(handler2);
		event.fire();
		
		
		
//		ServerThreadImpl t1 = new ServerThreadImpl();
//		ServerThreadImpl t2 = new ServerThreadImpl();
//		t1.bindHandler(handler1);
//		t2.bindHandler(handler2);
//		t1.executeHandler();
//		t2.executeHandler();
		
	}

}
