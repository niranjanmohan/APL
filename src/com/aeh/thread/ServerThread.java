package com.aeh.thread;

import com.aeh.commonobjects.Handler;

public interface ServerThread {
	public void executeHandler(Handler handler);
	//checks if it has to go back to TP or not
	public boolean checkHandlerPQ();
	public Handler getHandler(Object o);
	public boolean checkQ();
}
