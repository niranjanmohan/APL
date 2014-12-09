package com.aeh.thread.impl;

import java.util.Queue;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.AEHHolder;
import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.AEHandler;
import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.ServerThread;

public class DedicatedWatchDogImpl implements DedicatedWatchDog {
	int priority;
	AEHHolder aehHolder;
	AEHLockUtility aehLockUtility;
	NoHeapRealtimeThread noHeapRealTimeThread;
	
	public DedicatedWatchDogImpl() {
		aehHolder = AEHHolder.getInstance();
		aehLockUtility = aehHolder.getLockUtil();
		noHeapRealTimeThread = new NoHeapRealtimeThread(new Runnable() {
			
			@Override
			public void run() {
				start();
			}
		});
	}
	
	@Override
	public int getPriority() {
		return priority;
	}
	
	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	@Override
	public void initiateProcess() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void wake(){
		noHeapRealTimeThread.notify();
	}
	
	
	@Override
	public void waitOn(){
		try {
			noHeapRealTimeThread.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void start() {
		System.out.println("Enter the Dedicated thread for priority ----->"+priority);
		boolean decrementFlag;
		//get the count 
		while(true){
			decrementFlag = false;
			System.out.println("the priority is   :"+ aehHolder.getPriorityObjects().get(priority));
			PObject pObject = aehHolder.getPriorityObjects().get(priority);

			if(pObject.count.get() >0 ){
				pObject.count.decrementAndGet();
				decrementFlag = true;
				
				// get PQTP lock
				aehLockUtility.getPQAndTPLock();
				
				// check for thread availability
				if(!aehHolder.isThreadPoolEmpty()){
					ServerThread t = aehHolder.getThreadFromThreadPool();
					aehLockUtility.getQLock(priority);
					Queue<AEHandler> q;
					if(!( q = aehHolder.getQueue(priority)).isEmpty()){
						t.bindHandler(q.poll());
						t.executeHandler();	
					}
					aehLockUtility.releaseQLock(priority);
				}
				else{
					if(decrementFlag){
						aehHolder.getpQueue().add(priority);
					}
					else{
						try {
							this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				// release PQTP lock
				aehLockUtility.releasePQAndTPLock();
			}
			else{
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
