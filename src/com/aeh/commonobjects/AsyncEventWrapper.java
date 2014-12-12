package com.aeh.commonobjects;

import com.aeh.thread.AEHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.realtime.AsyncEvent;
import com.aeh.AEHHolder;


public class AsyncEventWrapper  extends AsyncEvent{
	List<AEHandler> handlers;
	AEHHolder aehHolder;
	AEHLockUtility lockUtil;
	int priority;
	

	public AsyncEventWrapper(int priority, AEHHolder h) {
		//aehHolder = 
		handlers = new ArrayList<AEHandler>();
		this.priority = priority;
		aehHolder = h;
		lockUtil = aehHolder.getLockUtil();
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
		//System.out.println("called file enque handlers");
		enqueueHandler();
		//here we have to enqueue update count 
		//System.out.println("finifhed enqueue handler");
		
	}
	
	public void enqueueHandler(){
		int handlerCount = handlers.size();
		PObject pObject = aehHolder.getPriorityObjects().get(priority);
		synchronized(pObject.getDedicatedWatchDog()){
			//lockUtil.getQLock(priority);
			Queue <AEHandler> queue = aehHolder.getHandlerQueues().get(priority);
			queue.addAll(handlers);
			//lockUtil.releaseQLock(priority);
			//System.out.println("Entering synchronized block");
			pObject.count= pObject.count + handlerCount;//.incrementAndGet();
			pObject.getDedicatedWatchDog().notify();
		}
	}

}
