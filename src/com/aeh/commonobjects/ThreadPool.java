package com.aeh.commonobjects;

import java.util.Queue;

import com.aeh.thread.ServerThread;

public class ThreadPool {
	Queue<ServerThread> threadPoolQ;
	
	public void enQueueHandler(ServerThread serverThread){
		threadPoolQ.add(serverThread);
	}
	
}
