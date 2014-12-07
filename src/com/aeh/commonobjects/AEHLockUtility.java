package com.aeh.commonobjects;

import java.util.ArrayList;
import java.util.List;


public class AEHLockUtility {
	
	List<Boolean> qLockList = new ArrayList<Boolean>();
	private boolean pqtpLock = false;
	
	private static class AEHHelper{
		private static AEHLockUtility INSTANCE;
	}
	
	public static AEHLockUtility getInstance(){
		return AEHHelper.INSTANCE;
	}
	
	public void initializeQLock(int no){
		for(int i=0; i<no; i++){
			qLockList.add(false);
		}
	}
	
	public synchronized void getQLock(int priority) throws InterruptedException {
		System.out.println("test code from AEHlockutil need to implement ");
		while(qLockList.get(priority)){
			wait();
		}
		qLockList.set(priority,true);
	}

	public synchronized void getPQAndTPLock() throws InterruptedException {
		while(pqtpLock){
			wait();
		}
		pqtpLock = true;
	}

	public synchronized void releasePQAndTPLock() {
		pqtpLock = false;
		notify();
	}

	public synchronized void releaseQLock(int priority) {
		qLockList.set(priority,false);
		notify();
	}


}
