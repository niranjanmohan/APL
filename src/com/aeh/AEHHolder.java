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
import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.AEHandler;
import com.aeh.thread.ServerThread;
import com.aeh.thread.impl.DedicatedThread;
import com.aeh.thread.impl.DedicatedWatchDogImpl;
import com.aeh.thread.impl.ServerThreadImpl;

public class AEHHolder {
	private AEHHolder instance = null;
	
	Queue<ServerThread> threadPoolQ;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AEHandler>> handlerQueues;
	final AEHLockUtility lockUtil;
	final int priorityCount;
	private int serverThreadCount;

//	private AEHHolder(){
//		priorityCount = 8;
//
//		// initialize lock utility
//		lockUtil = new AEHLockUtility();
//		handlerQueues = new ArrayList<Queue<AEHandler>>();
//		initialize();
//	}
	
	public AEHHolder(int serverThreadCount, int priorityCount){
		this.instance = this ;
		this.priorityCount = priorityCount;
		this.serverThreadCount = serverThreadCount;
		
		// initialize lock utility
		lockUtil = new AEHLockUtility(priorityCount);
		handlerQueues = new ArrayList<Queue<AEHandler>>();
		initialize();
	}
	
	public void initialize(){
		priorityObjects = new HashMap<Integer,PObject>();
		//instantiate each priority object
		for(int i=0;i<priorityCount;i++){
			PObject pobject = new PObject(i,this);
			pobject.setDedicatedFree(true);
			//DedicatedWatchDog dedicatedWatchDog = new DedicatedWatchDogImpl();
			//DedicatedThread dedicatedServerThread = new DedicatedThread();
			//pobject.setDedicatedThread(dedicatedServerThread);
			//pobject.setDedicatedWatchDog(dedicatedWatchDog);
			priorityObjects.put(i, pobject);
			
			// initilize handlerQueues list
			
			Queue<AEHandler> q = new LinkedList<AEHandler>();
			handlerQueues.add(q);
		}
		
		// initialize thread pool
		
		threadPoolQ = new LinkedList<ServerThread>();
		
		for(int i=0; i<serverThreadCount; i++){
			threadPoolQ.add(new ServerThreadImpl());
		}
		
	}


	public Queue<ServerThread> getThreadPoolQ() {
		return threadPoolQ;
	}
	public void setThreadPoolQ(Queue<ServerThread> threadPoolQ) {
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

	public AEHHolder getInstance(){
//		if(instance == null){
//			synchronized(AEHHolder.class){
//				if(instance == null)
//					instance = new AEHHolder(5, 10); 
//			}
//		}
		return instance;
	}
	public boolean isThreadPoolEmpty(){
		return threadPoolQ.isEmpty();
	}

	public ServerThread getThreadFromThreadPool(){
		return threadPoolQ.poll();
	}
	
	public AEHLockUtility getLockUtil(){
		return lockUtil;
	}
	

	public  void enQueueHandler(List<AEHandler> eventHandlers){
		
		int priority = eventHandlers.get(0).getPriority();
		lockUtil.getQLock(priority);
		Queue <AEHandler> queue = handlerQueues.get(priority);
		queue.addAll(eventHandlers);
		lockUtil.releaseQLock(priority);
		System.out.println(priority);
		PObject pObject = priorityObjects.get(priority);
		System.out.println("Entering synchronized block");
		synchronized(pObject){
			pObject.count++;
			pObject.getDedicatedWatchDog().notify();
		}
	}

}
