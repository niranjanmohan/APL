package com.aeh.thread;

public interface AEHandler {
	public void handlerLogic();
	public int getPriority();
	public void setPriority(int priority);
	public void setFiretime(long firetime);
}
