package com.aeh.commonobjects;

public interface LockUtility {
	public boolean getQLock(int priority);
	public boolean getPQAndTPLock();
	public boolean releasePQAndTPLock();
	public boolean releaseQLock(int priority);
	public boolean enqueueHandler(int priority);
//	public void notifyWatchDog(int priority);
	public boolean	updateCount(int priority);
}
