package com.aeh.thread;

import javax.realtime.RealtimeThread;

import com.aeh.commonobjects.AsyncEventWrapper;

public interface ServerThread {
	public void executeHandler(AsyncEventWrapper handler);
	//checks if it has to go back to TP or not
	public boolean checkHandlerPQ();
	public AsyncEventWrapper getHandler(Object o);
	public boolean checkQ();
	public void setHandlerPriority(int priority);
	public int getHandlerPriority();
	public RealtimeThread getRealTimeThread();
	public void setRealTimeThread(RealtimeThread realTimeThread);
	public void start();
}
