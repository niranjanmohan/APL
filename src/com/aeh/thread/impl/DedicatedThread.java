package com.aeh.thread.impl;

import javax.realtime.RealtimeThread;

import com.aeh.commonobjects.AsyncEventWrapper;
import com.aeh.thread.ServerThread;

public class DedicatedThread implements ServerThread {
	int priority;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public boolean checkHandlerPQ() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AsyncEventWrapper getHandler(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkQ() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void executeHandler(AsyncEventWrapper handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHandlerPriority(int priority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHandlerPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RealtimeThread getRealTimeThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealTimeThread(RealtimeThread realTimeThread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	

}
