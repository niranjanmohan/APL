package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;

import javax.realtime.AsyncEvent;
import javax.realtime.AsyncEventHandler;

import com.aeh.AEHHolder;


public class AsyncEventWrapper extends AsyncEvent {
	List<AsyncEventHandler> listHandlers;
	AEHHolder aehHolder;
	public AsyncEventWrapper(AEHHolder aehHolder) {
		this.aehHolder = aehHolder;
		listHandlers = new ArrayList<AsyncEventHandler>();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void addHandler(AsyncEventHandler handler){
		listHandlers.add(handler);
	}
	
	@Override
	public void fire(){
		//here we have to enqueue update count 
		for(AsyncEventHandler async: listHandlers)
			aehHolder.enQueueHandler(async);
	}

}
