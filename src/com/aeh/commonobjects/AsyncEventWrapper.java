package com.aeh.commonobjects;

import com.aeh.thread.AEHandler;
import java.util.ArrayList;
import java.util.List;

import javax.realtime.AsyncEvent;
import com.aeh.AEHHolder;


public class AsyncEventWrapper  extends AsyncEvent{
	List<AEHandler> handlers;
	AEHHolder aehHolder;
	int priority;


	public AsyncEventWrapper(int priority) {
		//aehHolder = 
		handlers = new ArrayList<AEHandler>();
		this.priority = priority;
		aehHolder = AEHHolder.getInstance();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getPriority(){
		return this.priority;
	}

	
	private void setPriority(AEHandler handler){
		handler.setPriority(priority);
	}
	

	public void addHandler(AEHandler handler){
		this.setPriority(handler);
		synchronized(AsyncEventWrapper.class){
			handlers.add(handler);
		}
		
	}


	public void fire(){
		System.out.println("called file enque handlers");
		aehHolder.enQueueHandler(handlers);
		//here we have to enqueue update count 
		System.out.println("finifhed enqueue handler");
		
	}

}
