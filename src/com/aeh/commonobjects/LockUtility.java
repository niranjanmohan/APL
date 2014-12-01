package com.aeh.commonobjects;

public interface LockUtility {
	public void getQLock(int priority);
	public void getPQAndTPLock();
	public void releasePQAndTPLock();
	public void releaseQLock(int priority);
	public void enqueueHandler(int priority);
	public void notifyWatchDog(int priority);
	public void	updateCount(int priority);
}
