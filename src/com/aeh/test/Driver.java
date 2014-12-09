package com.aeh.test;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.AEHandlerImpl;
import com.aeh.thread.impl.ServerThreadImpl;
import com.aeh.commonobjects.AsyncEventWrapper;

public class Driver {
	public static void main(String []args){
		AsyncEventWrapper event = new AsyncEventWrapper(5);
		ServerThreadImpl t1 = new ServerThreadImpl();
		ServerThreadImpl t2 = new ServerThreadImpl();
		AEHandler handler1 = new AEHandlerImpl(1);
		AEHandler handler2 = new AEHandlerImpl(5);
		t1.bindHandler(handler1);
		t2.bindHandler(handler2);
		t1.executeHandler();
		t2.executeHandler();

		//creating handlers and adding it
//		event.addHandler(handler1);
//		event.addHandler(handler2);
//		event.fire();
		
	}

}
