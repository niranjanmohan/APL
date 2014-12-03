package com.aeh;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.realtime.AsyncEventHandler;

import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.AsyncEventWrapper;
import com.aeh.commonobjects.LockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.commonobjects.ThreadPool;

public class AEHHolder {
	private static volatile AEHHolder instance;
	ThreadPool threadPool;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AsyncEventWrapper>> handlerQueues;
	LockUtility lockUtil;
	int priority;




	public static AEHHolder getInstance(){
		if(instance == null){
			synchronized(AEHHolder.class){
				if(instance == null)
					instance = new AEHHolder(); 
			}
		}
		return instance;
	}

	private AEHHolder(){
		lockUtil = new AEHLockUtility();
	}

	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}



	public void enQueueHandler(AsyncEventHandler aehHolder){
		//aehHolder
		lockUtil.getQLock(priority);
		handlerQueues.get(priority);


	}

}


/*
	private static volatile AEHHolder instance = null;
	public static AEHHolder getInstance(){
		AEHHolder temp = instance;
		if(temp == null){
			synchronized(AEHHolder.class){
				instance = new AEHHolder(); 
			}
		}
		return instance;
	}
	private AEHHolder(){

	}
 */