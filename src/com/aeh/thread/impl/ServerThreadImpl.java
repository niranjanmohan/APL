package com.aeh.thread.impl;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.thread.AEHandler;
import com.aeh.thread.ServerThread;

public class ServerThreadImpl implements ServerThread{
	NoHeapRealtimeThread noHeapRealTimeThread;
	AEHandler aeHandler;

	public ServerThreadImpl() {
		noHeapRealTimeThread = new NoHeapRealtimeThread(new Runnable() {
			
			@Override
			public void run() {
				aeHandler.handlerLogic();
			}
		});
	}
	
	
	@Override
	public void bindHandler(AEHandler handler){
		aeHandler = handler;
		setThreadPriority(aeHandler.getPriority());
	}
	
	
	@Override
	public void executeHandler() {
		start();
		// TODO execute handler and check what to do 
	}

	@Override
	public void setThreadPriority(int priority) {
		noHeapRealTimeThread.setPriority(priority);
	}

	@Override
	public int getHandlerPriority() {
		return noHeapRealTimeThread.getPriority();
	}

	@Override
	public void start() {
		noHeapRealTimeThread.start();
	}

}
