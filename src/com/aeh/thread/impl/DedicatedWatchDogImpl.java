package com.aeh.thread.impl;

import java.util.Queue;

import javax.realtime.RealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.AEHandler;

public class DedicatedWatchDogImpl extends RealtimeThread {
	int priority;
	AEHHolder aehHolder;
	AEHLockUtility aehLockUtility;
	RealtimeThread rtThread;
	
	public DedicatedWatchDogImpl(int p, AEHHolder h) {
		aehHolder = h;
		priority = p;
		this.setPriority(priority+1);
		aehLockUtility = aehHolder.getLockUtil();
	}
	
	@Override
	public void run(){
//		synchronized (this) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		go();
	}
	
	public void go() {
		//System.out.println("Enter the Dedicated thread for priority ----->"+priority);
		boolean decrementFlag;
		while(true){
			decrementFlag = false;
			PObject pObject = aehHolder.getPriorityObjects().get(priority);
			aehLockUtility.getQLock(priority);
			if(pObject.count>0){
				pObject.count--;
				decrementFlag = true;
			}
			else{
				decrementFlag = false;
			}
			aehLockUtility.releaseQLock(priority);
			aehLockUtility.getPQAndTPLock();
			ServerThreadImpl t = checkForThread();
			if(decrementFlag && (t==null)){
				aehHolder.getpQueue().add(priority);
			}
			if(!decrementFlag && (t!=null)){
				aehHolder.getpQueue().poll();
			}
			aehLockUtility.releasePQAndTPLock();
			synchronized (this) {
				if(decrementFlag && (t==null)){
					makeItWait();
				}
				if(decrementFlag && (t!=null)){
					bindAndStart(t);
				}
				if(!decrementFlag && (t!=null)){
					bindAndStart(t);
				}
				if(!decrementFlag && (t==null)){
					makeItWait();
				}
			}
//			synchronized (this) {
//				if(pObject.count >0 ){
//					System.out.println("count is greater than 0  ["+priority+"]");
//					pObject.count--;
//					decrementFlag = true;
//					aehLockUtility.getPQAndTPLock();
//					if(!aehHolder.isThreadPoolEmpty()){
//						System.out.println("thread pool is not empty ["+priority+"]");
//						bindAndStart();
//					}
//					else{
//						System.out.println("thread pool is empty ["+priority+"]");
//						System.out.println("adding to PQ ["+priority+"]");
//						aehHolder.getpQueue().add(priority);
//						aehLockUtility.releasePQAndTPLock();
//						makeItWait();	
//					}
//				}
//				else{
//					System.out.println("count is not greater than 0  ["+priority+"]");			
//					if(!aehHolder.getpQueue().isEmpty()){
//						aehLockUtility.getPQAndTPLock();
//						if(!aehHolder.isThreadPoolEmpty()){
//							aehHolder.getpQueue().poll();
//							System.out.println("count!> 0 & thread pool is not empty ["+priority+"]");
//							bindAndStart();
//						}
//						else{
//							System.out.println("count!> 0 & thread pool is empty ["+priority+"]");
//							aehLockUtility.releasePQAndTPLock();
//							makeItWait();					
//						}
//					}
//					else{
//						makeItWait();
//					}
//				}
//			}
		}
	}
	
	public void bindAndStart(ServerThreadImpl t){
		Queue<AEHandler> q;
		if(!( q = aehHolder.getQueue(priority)).isEmpty()){
			t.bindHandler(q.poll(),true);
			t.start();	
		}
	}
	
	public ServerThreadImpl checkForThread(){
		if(aehHolder.getThreadPoolQ().isEmpty())
			return null;
		else
			return aehHolder.getThreadPoolQ().poll();
	}
	
	public void makeItWait(){
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
