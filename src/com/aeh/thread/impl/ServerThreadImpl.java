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
		executeHandler(false);
	}
	
	public void executeHandler(boolean what) {
		System.out.println("Executing ["+aeHandler.getPriority()+"]");
		aeHandler.handlerLogic();
		next();
	}

	public void setThreadPriority(int priority) {
		this.setPriority(priority+1);
	}

	public int getHandlerPriority() {
		return aeHandler.getPriority();
	}
	
	public void next(){
		PObject pObject = aehHolder.getPriorityObjects().get(aeHandler.getPriority());
			synchronized (pObject.getDedicatedWatchDog()) {
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
				aehHolder.getPriorityObjects().get(hp).getDedicatedWatchDog().notify();
			}
			else{
				System.out.println("There is no higher priority available "+ hp);
				aehHolder.getpQueue().poll();
				Queue<AEHandler> q;
				if(!( q = aehHolder.getQueue(hp)).isEmpty()){
					this.bindHandler(q.poll());
					aehHolder.getLockUtil().releasePQAndTPLock();
					this.executeHandler(false);	
				}
			}
		}	
	}

}
