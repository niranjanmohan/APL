package com.aeh.commonobjects;

import com.aeh.AEHHolder;


public class AEHLockUtility implements LockUtility{
	private static 
	
	
	class AEHHelper{
		private static AEHLockUtility INSTANCE;
	}
	
	public AEHLockUtility getInstance(){
		return AEHHelper.INSTANCE;
	}
	
	
	
	@Override
	public boolean getQLock(int priority) {
		System.out.println("test code from AEHlockutil need to implement ");
	
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getPQAndTPLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releasePQAndTPLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseQLock(int priority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enqueueHandler(int priority) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public void notifyWatchDog(int priority) {
//		
//		return ;
//	}

	@Override
	public boolean updateCount(int priority) {
		// TODO Auto-generated method stub
		return false;
		
	}

}
