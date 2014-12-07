package com.aeh.thread.impl;

import javax.realtime.RealtimeThread;

import com.aeh.commonobjects.AsyncEventWrapper;
import com.aeh.thread.ServerThread;

public class ServerThreadImpl implements ServerThread{
	RealtimeThread realTimeThread;


	public ServerThreadImpl() {
		realTimeThread = new RealtimeThread();
		// TODO Auto-generated constructor stub
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
		realTimeThread.setPriority(priority);

	}

	@Override
	public int getHandlerPriority() {
		// TODO Auto-generated method stub
		return realTimeThread.getPriority();
	}


	public RealtimeThread getRealTimeThread() {
		return realTimeThread;
	}


	public void setRealTimeThread(RealtimeThread realTimeThread) {
		this.realTimeThread = realTimeThread;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		//get the PQ lock 
		
		
		realTimeThread.start();
		
	}


	
	
}
