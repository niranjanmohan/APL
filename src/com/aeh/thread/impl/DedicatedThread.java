package com.aeh.thread.impl;

import java.util.Queue;

import javax.realtime.RealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.AEHandler;

public class DedicatedThread extends RealtimeThread{
	AEHandler aeHandler;
	AEHHolder aehHolder;
	int priority;

	public DedicatedThread(int p, AEHHolder holder) {
		this.priority = p;
		this.aehHolder = holder;
		this.setPriority(priority+1);
	}
	
	public void bindHandler(AEHandler handler){
		aeHandler = handler;
	}
	
	@Override
	public void run(){
		executeHandler();
	}
	
	public void executeHandler() {
		System.out.println("Executing with D {{"+aeHandler.getPriority()+"}}");
		//aeHandler.handlerLogic();
		next();
	}
	
	public void next(){
//		System.out.println("PQ = "+aehHolder.getpQueue().toString());
		PObject pObject = aehHolder.getPriorityObjects().get(priority);
		AEHandler h;
		synchronized (pObject.getDedicatedWatchDog()) {
			h = findHandler();
			pObject.getDedicatedWatchDog().notify();
		}
		if(h!=null){
			bindHandler(h);
			executeHandler();
		}
		else{
			synchronized (pObject.getDedicatedWatchDog()) {
				pObject.setDedicatedThread(new DedicatedThread(priority, aehHolder));
				pObject.setDedicatedFree(true);
				pObject.getDedicatedWatchDog().notify();
			}
		}
	}
	
	public AEHandler findHandler(){
		Queue<AEHandler> q;
		if(!( q = aehHolder.getQueue(priority)).isEmpty()){
			return q.poll();
		}
		return null;
	}

}
