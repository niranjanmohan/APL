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
	
	public void bindHandler(AEHandler handler, boolean setPriority){
		aeHandler = handler;
		if(setPriority)
			setThreadPriority(aeHandler.getPriority());
	}
	
	@Override
	public void run(){
		executeHandler();
	}
	
	public void executeHandler() {
		//System.out.println("Executing {{"+aeHandler.getPriority()+"}}");
		aeHandler.handlerLogic();
		next1();
	}

	public void setThreadPriority(int priority) {
		this.setPriority(priority+1);
	}

	public int getHandlerPriority() {
		return aeHandler.getPriority();
	}
	
	
	// Thread always goes to pool
	public void next1(){
		
		aehHolder.getLockUtil().getPQAndTPLock();
//		System.out.println("PQ = "+aehHolder.getpQueue().toString());
		int hp;
		if(aehHolder.getpQueue().isEmpty()){
			addNewThreadToPool();
			aehHolder.getLockUtil().releasePQAndTPLock();
			return;
		}
		hp = aehHolder.getpQueue().peek();
		addNewThreadToPool();
		aehHolder.getLockUtil().releasePQAndTPLock();
		PObject pObject = aehHolder.getPriorityObjects().get(hp);
		synchronized (pObject.getDedicatedWatchDog()) {
			pObject.getDedicatedWatchDog().notify();
		}	
	}
	
	
	// Thread goes to pool if higher priority  handler available
	public void next(){
		aehHolder.getLockUtil().getPQAndTPLock();
		int hp;
		if(aehHolder.getpQueue().isEmpty()){
			addNewThreadToPool();
			aehHolder.getLockUtil().releasePQAndTPLock();
			return;
		}
		hp = aehHolder.getpQueue().peek();
		if(hp>this.getPriority()){
			// go back to pool
			addNewThreadToPool();
			aehHolder.getLockUtil().releasePQAndTPLock();
			PObject pObject = aehHolder.getPriorityObjects().get(hp);
			synchronized (pObject.getDedicatedWatchDog()) {
				pObject.getDedicatedWatchDog().notify();
			}
		}
		else{
			AEHandler h;
			PObject pObject = aehHolder.getPriorityObjects().get(hp);
			aehHolder.getLockUtil().releasePQAndTPLock();
			synchronized (pObject.getDedicatedWatchDog()) {
				aehHolder.getLockUtil().getPQAndTPLock();
				h = findHandler(hp);
				aehHolder.getLockUtil().releasePQAndTPLock();
				pObject.getDedicatedWatchDog().notify();
			}
			if(h!=null){
				bindHandler(h,false);
				executeHandler();
			}
			else{
				synchronized (pObject.getDedicatedWatchDog()) {
					aehHolder.getLockUtil().getPQAndTPLock();
					addNewThreadToPool();
					aehHolder.getLockUtil().releasePQAndTPLock();
					pObject.getDedicatedWatchDog().notify();
				}
			}
		}
		
		
	}
	
	public AEHandler findHandler(int p){
		Queue<AEHandler> q;
		if(!( q = aehHolder.getQueue(p)).isEmpty()){
			aehHolder.getpQueue().poll();
			return q.poll();
		}
		return null;
	}
	
	public void addNewThreadToPool(){
		aehHolder.getThreadPoolQ().add(new ServerThreadImpl(aehHolder));
	}

}
