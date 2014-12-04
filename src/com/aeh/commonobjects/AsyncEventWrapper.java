package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;

import javax.realtime.AsyncEvent;
import javax.realtime.AsyncEventHandler;
import javax.realtime.PriorityParameters;

import com.aeh.AEHHolder;


public class AsyncEventWrapper  extends AsyncEvent{
	List<AsyncEventHandler> handlers;
	AEHHolder aehHolder;
	int priority;


	public AsyncEventWrapper(int priority) {
		//aehHolder = 
		handlers = new ArrayList<AsyncEventHandler>();
		this.priority = priority;
		aehHolder = AEHHolder.getInstance();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getPriority(){
		return this.priority;
	}

	
	private void setPriority(AsyncEventHandler handler){
		PriorityParameters sp = new PriorityParameters();
		int priority = this.getPriority()>aehHolder.getPriorityCount()?aehHolder.getPriorityCount():this.getPriority();
		sp.setPriority(priority);
		handler.setSchedulingParameters(sp);
	}
	

	public void addHandler(AsyncEventHandler handler){
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
