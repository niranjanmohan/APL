package com.aeh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.AEHandler;
import com.aeh.thread.impl.DedicatedWatchDogImpl;
import com.aeh.thread.impl.ServerThreadImpl;

public class AEHHolder {
	static volatile AEHHolder instance = null;
	Queue<ServerThreadImpl> threadPoolQ;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AEHandler>> handlerQueues;
	AEHLockUtility lockUtil;
	int priorityCount;
	int serverThreadCount;
	
	

//	public AEHHolder(){
//		priorityCount = 8;
//		// initialize lock utility
//		lockUtil = new AEHLockUtility();
//		initialize();
//	}
	
	public AEHHolder(int serverThreadCount, int priorityCount){
//		System.out.println("creating object inside constructor");
		this.priorityCount = priorityCount;
		this.serverThreadCount = serverThreadCount;
		handlerQueues = new ArrayList<Queue<AEHandler>>();
		threadPoolQ = new LinkedList<ServerThreadImpl>();
		pQueue = new PriorityQueue<Integer>();
	
		// initialize lock utility
		lockUtil = new AEHLockUtility(priorityCount);
		
		initialize();
//		System.out.println("finished creating object");
	}
	
	public void initialize(){
		priorityObjects = new HashMap<Integer,PObject>();
		//instantiate each priority object
		for(int i=0;i<priorityCount;i++){
			//System.out.println("adding pobject for "+ i);
			PObject pobject = new PObject(i,this);
			DedicatedWatchDogImpl watchDog = new DedicatedWatchDogImpl(i,this);
			pobject.setDedicatedWatchDog(watchDog);
			priorityObjects.put(i, pobject);
			watchDog.start();
//			pobject.setDedicatedThread(dedicatedServerThread);
//			pobject.setDedicatedWatchDog(dedicatedWatchDog);
			
			
			// initilize handlerQueues list
			Queue<AEHandler> q = new LinkedList<AEHandler>();
			handlerQueues.add(q);
		}
		
		for(int i=0; i<serverThreadCount; i++){
			threadPoolQ.add(new ServerThreadImpl(this));
		}	
		
	}


	public Queue<ServerThreadImpl> getThreadPoolQ() {
		return threadPoolQ;
	}
	public void setThreadPoolQ(Queue<ServerThreadImpl> threadPoolQ) {
		this.threadPoolQ = threadPoolQ;
	}

	public PriorityQueue<Integer> getpQueue() {
		return pQueue;
	}

	public void setpQueue(PriorityQueue<Integer> pQueue) {
		this.pQueue = pQueue;
	}

	public List<Queue<AEHandler>> getHandlerQueues() {
		return handlerQueues;
	}

	public void setHandlerQueues(List<Queue<AEHandler>> handlerQueues) {
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
	public Queue<AEHandler> getQueue(int priority){
		return handlerQueues.get(priority);
	}
	public void setQueue(int index,Queue<AEHandler> queue){
		handlerQueues.set(index, queue);
	}


	public boolean isThreadPoolEmpty(){
		return threadPoolQ.isEmpty();
	}

	public ServerThreadImpl getThreadFromThreadPool(){
		return threadPoolQ.poll();
	}
	
	public AEHLockUtility getLockUtil(){
		return lockUtil;
	}
	

//	public  void enQueueHandler(List<AEHandler> eventHandlers){
//		
//		int priority = eventHandlers.get(0).getPriority();
//		int handlerCount = eventHandlers.size();
//		lockUtil.getQLock(priority);
//		Queue <AEHandler> queue = handlerQueues.get(priority);
//		queue.addAll(eventHandlers);
//		lockUtil.releaseQLock(priority);
//		PObject pObject = priorityObjects.get(priority);
//		//System.out.println("Entering synchronized block");
//		synchronized(pObject.getDedicatedWatchDog()){
//			pObject.count= pObject.count + handlerCount;//.incrementAndGet();
//			pObject.getDedicatedWatchDog().notify();
//		}
//	}

}
