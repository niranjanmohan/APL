package com.aeh.test;

import com.aeh.AEHHolder;
import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.AEHandlerImpl;
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
		
//		for(int i =0 ;i<20; i++){
//			AEHandler handler = new AEHandlerImpl("**Executing");
//			event2.addHandler(handler);
//		}
		
		for(int i =0 ;i<100000; i++){
			AEHandler handler = new AEHandlerImpl("*Executing");
			event1.addHandler(handler);
		}
		
		
		event1.fire();
		//event2.fire();
		event3.fire();
		
	}

}
