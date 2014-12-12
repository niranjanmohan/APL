package com.aeh.thread.impl;

import java.util.Queue;

import javax.realtime.RealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.AEHandler;

public class ServerThreadImpl extends RealtimeThread{
	AEHandler aeHandler;
	AEHHolder aehHolder;

	public ServerThreadImpl(AEHHolder holder) {
		this.aehHolder = holder;
	}
	
	public void bindHandler(AEHandler handler){
		aeHandler = handler;
		setThreadPriority(aeHandler.getPriority());
	}
	
	@Override
	public void run(){
		aeHandler.handlerLogic();
		next();
	}
	
	public void executeHandler() {
		if(this.getState()==State.NEW)
			this.start();
	}

	public void setThreadPriority(int priority) {
		this.setPriority(priority+1);
	}

	public int getHandlerPriority() {
		return aeHandler.getPriority();
	}
	
	public void next(){
		PObject pObject = aehHolder.getPriorityObjects().get(aeHandler.getPriority());
			synchronized (pObject.lock) {
			aehHolder.getLockUtil().getPQAndTPLock();
			int hp;
			try{
				 hp = aehHolder.getpQueue().peek();
			}
			catch(Exception e){
				System.out.println("null exception in PQ");
				aehHolder.getLockUtil().releasePQAndTPLock();
				return;
			}
			
			if(hp>aeHandler.getPriority()){
				System.out.println("There is a higher priority available");
				aehHolder.getLockUtil().releasePQAndTPLock();
				aehHolder.getThreadPoolQ().add(this);
				pObject.getDedicatedWatchDog().notify();
			}
			else{
				System.out.println("There is no higher priority available "+ hp);
				aehHolder.getpQueue().poll();
				Queue<AEHandler> q;
				if(!( q = aehHolder.getQueue(hp)).isEmpty()){
					this.bindHandler(q.poll());
					aehHolder.getLockUtil().releasePQAndTPLock();
					this.executeHandler();	
				}
			}
		}	
	}

}
