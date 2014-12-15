package com.aeh.test;

import com.aeh.AEHHolder;
import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.AEHandlerImpl;
import com.aeh.commonobjects.AsyncEventWrapper;

public class Driver {
	public static void main(String []args){
		
		
		AEHHolder h = new AEHHolder(2,4,false);
		AsyncEventWrapper event3 = new AsyncEventWrapper(3, h);
//		AsyncEventWrapper event2 = new AsyncEventWrapper(2, h);
		AsyncEventWrapper event1 = new AsyncEventWrapper(1, h);
		
		for(int i =0 ;i<10; i++){
			AEHandler handler = new AEHandlerImpl("Executing", h);
			event3.addHandler(handler);
		}
		
//		for(int i =0 ;i<100; i++){
//			AEHandler handler = new AEHandlerImpl("Executing");
//			event2.addHandler(handler);
//		}
//		
		for(int i =0 ;i<200; i++){
			AEHandler handler = new AEHandlerImpl("Executing", h);
			event1.addHandler(handler);
		}
		
		
		event1.fire();
//		event2.fire();
		event3.fire();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<h.countHandlers.size();i++){
			if(h.countHandlers.get(i)!=0){
				System.out.println(h.countHandlers.get(i)+" - "+h.responseTime.get(i));
				System.out.println(i+" -- "+h.responseTime.get(i)/h.countHandlers.get(i));
			}
				
		}
		
	}

}
