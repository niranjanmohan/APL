package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;

import javax.realtime.AsyncEvent;
import javax.realtime.AsyncEventHandler;

import com.aeh.AEHHolder;


public class AsyncEventWrapper  extends AsyncEvent{
	volatile static List<AsyncEventHandler> listHandlers;
	AEHHolder aehHolder;


	public AsyncEventWrapper() {
		//aehHolder = 
		listHandlers = new ArrayList<AsyncEventHandler>();
		// TODO Auto-generated constructor stub
	}


	public void addHandler(AsyncEventHandler handler){
		synchronized(AsyncEventWrapper.class){
			listHandlers.add(handler);
		}
		
	}


	public void fire(){
		
		
		//here we have to enqueue update count 
		for(AsyncEventHandler async: listHandlers)
			aehHolder.enQueueHandler(async);
	}

}
