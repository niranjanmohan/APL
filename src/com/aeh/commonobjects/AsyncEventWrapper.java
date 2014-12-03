package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;

import javax.realtime.AsyncEvent;
import javax.realtime.AsyncEventHandler;

import com.aeh.AEHHolder;


public class AsyncEventWrapper  extends AsyncEvent{
	List<AsyncEventHandler> handlers;
	AEHHolder aehHolder;


	public AsyncEventWrapper() {
		//aehHolder = 
		handlers = new ArrayList<AsyncEventHandler>();
		aehHolder = AEHHolder.getInstance();
		// TODO Auto-generated constructor stub
	}


	public void addHandler(AsyncEventHandler handler){
		synchronized(AsyncEventWrapper.class){
			handlers.add(handler);
		}
		
	}


	public void fire(){
		aehHolder.enQueueHandler(handlers);
		//here we have to enqueue update count 
		
	}

}
