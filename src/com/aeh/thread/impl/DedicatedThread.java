package com.aeh.thread.impl;

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
	

}
