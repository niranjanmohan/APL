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

	
	private void changePriority(AsyncEventHandler handler){
		PriorityParameters sp = new PriorityParameters();
		sp.setPriority(this.priority);
		handler.setSchedulingParameters(sp);
	}
	

	public void addHandler(AsyncEventHandler handler){
		this.changePriority(handler);
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
