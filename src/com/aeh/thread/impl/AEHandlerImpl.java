package com.aeh.thread.impl;

import com.aeh.thread.AEHandler;

public class AEHandlerImpl implements AEHandler {
	private int priority;
	private String desc;
	
//	public AEHandlerImpl(int p){
//		priority = p;
//	}
	
	public AEHandlerImpl(String str){
		desc = str;
	}
	
	@Override
	public void handlerLogic(){
		System.out.println(desc+" with priority = "+priority);
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
