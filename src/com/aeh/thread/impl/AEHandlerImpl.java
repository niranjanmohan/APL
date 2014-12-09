package com.aeh.thread.impl;

import com.aeh.thread.AEHandler;

public class AEHandlerImpl implements AEHandler {
	private int priority;
	
	public AEHandlerImpl(int p){
		priority = p;
	}
	
	@Override
	public void handlerLogic(){
		System.out.println("priority = "+priority);
	}

	@Override
	public void setPriority(int p){
		priority = p;
	}
	
	@Override
	public int getPriority(){
		return priority;
	}

}
