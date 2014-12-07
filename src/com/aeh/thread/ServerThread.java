package com.aeh.thread;

import com.aeh.commonobjects.AsyncEventWrapper;

public interface ServerThread {
	public void setHandlerPriority(int priority);
	public int getHandlerPriority();
	public void start();
}
