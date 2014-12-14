package com.aeh.thread.impl;

import com.aeh.thread.AEHandler;

public class AEHandlerImpl implements AEHandler {
	private int priority;
	private String desc;
	private long firetime;
	
//	public AEHandlerImpl(int p){
//		priority = p;
//	}
	
	public AEHandlerImpl(String str){
		desc = str;
	}
	
	@Override
	public void handlerLogic(){
		long avg = (System.nanoTime() - firetime)/1000;
		System.out.println(desc+"["+priority+"] {"+avg+"}");
	}

	@Override
	public void setPriority(int p){
		priority = p;
	}
	
	@Override
	public int getPriority(){
		return priority;
	}
	
	@Override
	public void setFiretime(long firetime){
		this.firetime = firetime;
	}

}
