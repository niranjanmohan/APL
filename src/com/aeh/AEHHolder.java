package com.aeh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.realtime.AsyncEventHandler;
import javax.realtime.PriorityParameters;

import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.LockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.commonobjects.ThreadPool;

public class AEHHolder {
	private static volatile AEHHolder instance;
	ThreadPool threadPool;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AsyncEventHandler>> handlerQueues;

	LockUtility lockUtil;
	final int priorityCount;
	
	private AEHHolder(){
		priorityCount = 8;
		initialize();
	}
	public void initialize(){
		lockUtil = new AEHLockUtility();
		priorityObjects = new HashMap<Integer,PObject>();

	}
	
	
	
	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}

	public PriorityQueue<Integer> getpQueue() {
		return pQueue;
	}

	public void setpQueue(PriorityQueue<Integer> pQueue) {
		this.pQueue = pQueue;
	}

	public List<Queue<AsyncEventHandler>> getHandlerQueues() {
		return handlerQueues;
	}

	public void setHandlerQueues(List<Queue<AsyncEventHandler>> handlerQueues) {
		this.handlerQueues = handlerQueues;
	}


	
	public Map<Integer, PObject> getPriorityObjects() {
		return priorityObjects;
	}

	public void setPriorityObjects(Map<Integer, PObject> priorityObjects) {
		this.priorityObjects = priorityObjects;
	}


	public int getPriorityCount() {
		return priorityCount;
	}

	public static AEHHolder getInstance(){
		if(instance == null){
			synchronized(AEHHolder.class){
				if(instance == null)
					instance = new AEHHolder(); 
			}
		}
		return instance;
	}



	public void enQueueHandler(List<AsyncEventHandler> eventHandlers){
		//aehHolder
		PriorityParameters param = (PriorityParameters)eventHandlers.get(0).getSchedulingParameters();
		int priority = param.getPriority();
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