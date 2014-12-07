package com.aeh.thread.impl;

import javax.realtime.NoHeapRealtimeThread;

import com.aeh.commonobjects.AsyncEventWrapper;
import com.aeh.thread.ServerThread;

public class ServerThreadImpl implements ServerThread{
	NoHeapRealtimeThread noHeapRealTimeThread;


	public ServerThreadImpl() {
		noHeapRealTimeThread = new NoHeapRealtimeThread(new Runnable() {
			
			@Override
			public void run() {
				//execute the code for the handler inside this
				
			}
		});
		// TODO Auto-generated constructor stub
	}




	public void executeHandler(AsyncEventWrapper handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandlerPriority(int priority) {
		noHeapRealTimeThread.setPriority(priority);

	}

	@Override
	public int getHandlerPriority() {
		// TODO Auto-generated method stub
		return noHeapRealTimeThread.getPriority();


	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

}
