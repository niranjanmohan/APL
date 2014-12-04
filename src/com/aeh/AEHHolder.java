package com.aeh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.realtime.AsyncEventHandler;

import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.LockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.commonobjects.ThreadPool;

public class AEHHolder {
	private static volatile AEHHolder instance;
	ThreadPool threadPool;
	Map<Integer,PObject> priorityObjects;
	public Map<Integer, PObject> getPriorityObjects() {
		return priorityObjects;
	}

	public void setPriorityObjects(Map<Integer, PObject> priorityObjects) {
		this.priorityObjects = priorityObjects;
	}


	PriorityQueue<Integer> pQueue;
	List<Queue<AsyncEventHandler>> handlerQueues;
	LockUtility lockUtil;
	int priority;
	final int priorityCount = 8;


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
		initialize();
	}
	public void initialize(){
		lockUtil = new AEHLockUtility();
		priorityObjects = new HashMap<Integer,PObject>();
		//start all the threads and 

	}

	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public void enQueueHandler(List<AsyncEventHandler> eventHandlers){
		//aehHolder
		if(lockUtil.getQLock(priority)){
			handlerQueues.get(priority);
			Queue <AsyncEventHandler> queue = handlerQueues.get(priority);
			queue.addAll(eventHandlers);
			lockUtil.releaseQLock(priority);
			PObject pObject;
			System.out.println("Entering synchronized block");
			synchronized(pObject = priorityObjects.get(priority)){
				pObject.setCount(pObject.getCount()+1);
				pObject.getDedicatedThread().notify();
			}
			//			lockUtil.notifyWatchDog(priority);
		}
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