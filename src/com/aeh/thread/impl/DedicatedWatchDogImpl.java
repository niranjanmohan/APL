package com.aeh.thread.impl;

import java.util.PriorityQueue;
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
//				System.out.println("it did come here "+ priority);
//				wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		go();
	}
	
	public void go() {
		boolean decrementFlag;
		System.out.println("in thread with priority "+priority);
		while(true){
			decrementFlag = false;
			PObject pObject = aehHolder.getPriorityObjects().get(priority);
			synchronized (this) {
				if(pObject.count >0 ){
					pObject.count--;
					decrementFlag = true;
					aehLockUtility.getPQAndTPLock();
					if(!aehHolder.isThreadPoolEmpty()){
						bindAndStart();
					}
					else{
						aehHolder.getpQueue().add(priority);
						System.out.println("added to PQ "+aehHolder.getpQueue().toString());
						aehLockUtility.releasePQAndTPLock();
						//makeItWait();	
					}
				}
				else{
					if(!aehHolder.getpQueue().isEmpty()){
						aehLockUtility.getPQAndTPLock();
						if(!aehHolder.isThreadPoolEmpty() && (aehHolder.getpQueue().peek() == priority) ){
							aehHolder.getpQueue().poll();
							System.out.println("polled from PQ "+aehHolder.getpQueue().toString());
							bindAndStart();
						}
						else{
							aehLockUtility.releasePQAndTPLock();
							makeItWait();					
						}
					}
					else{
						aehLockUtility.releasePQAndTPLock();
						makeItWait();					
					}
				}
			}
		}
	}
	
	public void bindAndStart(){
		Queue<AEHandler> q;
		if(!( q = aehHolder.getQueue(priority)).isEmpty()){
			ServerThreadImpl t = aehHolder.getThreadFromThreadPool();
			t.bindHandler(q.poll(),true);
			aehLockUtility.releasePQAndTPLock();
			t.start();	
		}
		else{
			System.out.println("no handler for priority ________________"+priority);
			for(int i =0;i<aehHolder.getHandlerQueues().size();i++){
				System.out.println(i+" - "+aehHolder.getQueue(i).size());
			}
			aehLockUtility.releasePQAndTPLock();
		}
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
