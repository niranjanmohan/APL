package com.aeh.thread.impl;

import com.aeh.AEHHolder;
import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.LockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.ServerThread;

public class DedicatedWatchDogImpl implements DedicatedWatchDog{
	int priority;
	AEHHolder aehHolder;
	LockUtility lockUtility;
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Override
	public void initiateProcess() {
		// TODO Auto-generated method stub
		aehHolder = AEHHolder.getInstance();
		lockUtility = AEHLockUtility.getInstance();
	}
	@Override
	public void run() {
		System.out.println("Enter the Dedicated thread for priority ----->"+priority);
		//get the count 
		while(true){

			System.out.println("the priority is   :"+ aehHolder.getPriorityObjects().get(priority));
			PObject pObject = aehHolder.getPriorityObjects().get(priority);

			if(pObject.count.get() >0 ){
				//decrement count  
				pObject.count.decrementAndGet();
				//search for Threads if no threads found insert priority into priority Q
				//need to get lock for the operation
				lockUtility.getPQAndTPLock();
				if(aehHolder.isThreadPoolEmpty()){
					//enque the value in the PQ
					aehHolder.getpQueue().add(priority);
				}
				else{
					//if thread pool is not empty get thread from thread pool and give a priority
					ServerThread serverThread = aehHolder.getThreadFromThreadPool();
					serverThread.setHandlerPriority(priority);
					serverThread.start();
				}
				lockUtility.releasePQAndTPLock();
			}
		}
	}
}
