package com.aeh.thread.impl;

import com.aeh.AEHHolder;
import com.aeh.thread.DedicatedWatchDog;

public class DedicatedWatchDogImpl implements DedicatedWatchDog{
	int priority;
	AEHHolder aehHolder;
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Override
	public void initiateProcess() {
		// TODO Auto-generated method stub
		aehHolder = AEHHolder.getInstance();
	}
	@Override
	public void run() {
		System.out.println("Enter the Dedicated thread for priority ----->"+priority);
		//get the count 
		while(true){
			System.out.println("the priority is   :"+ aehHolder.getPriorityObjects().get(priority).getCount());
		}
	}
	
	
	

}
