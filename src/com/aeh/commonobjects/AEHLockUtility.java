package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;


public class AEHLockUtility {
	
	volatile List<Boolean> qLockList = new ArrayList<Boolean>();
	private volatile boolean  pqtpLock = false;
	
//	private static class AEHHelper{
//		private static AEHLockUtility INSTANCE;
//	}
//	
//	public static AEHLockUtility getInstance(){
//		return AEHHelper.INSTANCE;
//	}
	
	public AEHLockUtility(int priorityCount){
		initializeQLock(priorityCount);
	}
	
	public void initializeQLock(int no){
		for(int i=0; i<no; i++){
			qLockList.add(false);
		}
	}
	
	public synchronized void getQLock(int priority){
		//System.out.println("test code from AEHlockutil need to implement ");
		while(qLockList.get(priority)){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		qLockList.set(priority,true);
	}

	public synchronized void getPQAndTPLock(){
		System.out.println("in lock pqtp");
		while(pqtpLock){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pqtpLock = true;
	}

	public synchronized void releasePQAndTPLock() {
		System.out.println("release pqtp lock");
		pqtpLock = false;
		notify();
	}

	public synchronized void releaseQLock(int priority) {
		qLockList.set(priority,false);
		notify();
	}


}
