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
		go();
	}
	
//	public int getPriority() {
//		return priority;
//	}
//	
//	
//	public void setPriority(int priority) {
//		this.priority = priority;
//	}
//	
	
	
	public void initiateProcess() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void go() {
		//System.out.println("Enter the Dedicated thread for priority ----->"+priority);
		boolean decrementFlag;
		//get the count 
		while(true){
			decrementFlag = false;
			//System.out.println("the priority is   :"+ aehHolder.getPriorityObjects().get(priority));
			PObject pObject = aehHolder.getPriorityObjects().get(priority);
			synchronized (this) {
			//System.out.println(pObject.count);
			if(pObject.count >0 ){
				System.out.println("count is greater than 0  ["+priority+"]");
				pObject.count--;
				decrementFlag = true;
				
				// get PQTP lock
				aehLockUtility.getPQAndTPLock();
				
				// check for thread availability
				if(!aehHolder.isThreadPoolEmpty()){
					System.out.println("thread pool is not empty ["+priority+"]");
					ServerThreadImpl t = aehHolder.getThreadFromThreadPool();
					//aehLockUtility.getQLock(priority);
					Queue<AEHandler> q;
					if(!( q = aehHolder.getQueue(priority)).isEmpty()){
						t.bindHandler(q.poll());
						t.start();	
					}
					//aehLockUtility.releaseQLock(priority);
				}
				else{
					System.out.println("thread pool is empty ["+priority+"]");
					if(decrementFlag){
						aehHolder.getpQueue().add(priority);
					}
					else{
							try {
								wait();
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
				System.out.println("count is not greater than 0  ["+priority+"]");
				
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			}
		}
	}
}
