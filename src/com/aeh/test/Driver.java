package com.aeh.test;

import javax.realtime.AsyncEventHandler;

import com.aeh.commonobjects.AsyncEventWrapper;

public class Driver {
	public static void main(String []args){
		AsyncEventWrapper event = new AsyncEventWrapper(5);
		
		//creating handlers and adding it
		AsyncEventHandler handler1 = new AsyncEventHandler();
		AsyncEventHandler handler2 = new AsyncEventHandler();
		event.addHandler(handler1);
		event.addHandler(handler2);
		event.fire();
	}

}
