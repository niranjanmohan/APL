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
		System.out.println("Executing {{"+aeHandler.getPriority()+"}}");
		//aeHandler.handlerLogic();
		next();
	}

	public void setThreadPriority(int priority) {
		this.setPriority(priority+1);
	}

	public int getHandlerPriority() {
		return aeHandler.getPriority();
	}
	
	public void next(){
		
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
//		}
			
//			if(hp>aeHandler.getPriority()){
//				System.out.println("There is a higher priority available ["+aeHandler.getPriority()+"]");
//				addNewThreadToPool();
//				aehHolder.getLockUtil().releasePQAndTPLock();
//				//aehHolder.getPriorityObjects().get(hp).getDedicatedWatchDog().notify();
//				pObject.getDedicatedWatchDog().notify();
//				return;
//			}
//			else{
//				addNewThreadToPool();
//				aehHolder.getLockUtil().releasePQAndTPLock();
//				//aehHolder.getPriorityObjects().get(hp).getDedicatedWatchDog().notify();
//				pObject.getDedicatedWatchDog().notify();
//				return;
//			}
//			else{
//				System.out.println(hp+" -- There is no higher priority available ["+aeHandler.getPriority()+"]");	
//				aehHolder.getpQueue().poll();
//				Queue<AEHandler> q;
//				if(!( q = aehHolder.getQueue(hp)).isEmpty()){
//					this.bindHandler(q.poll(),false);
//					aehHolder.getLockUtil().releasePQAndTPLock();
//				}
//			}
	
			//this.executeHandler();	
	}
	
	public void addNewThreadToPool(){
		aehHolder.getThreadPoolQ().add(new ServerThreadImpl(aehHolder));
	}

}
