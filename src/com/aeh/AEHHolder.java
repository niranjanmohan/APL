package com.aeh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.realtime.AsyncEventHandler;
import javax.realtime.PriorityParameters;

import com.aeh.commonobjects.AEHLockUtility;
import com.aeh.commonobjects.PObject;
import com.aeh.thread.DedicatedWatchDog;
import com.aeh.thread.ServerThread;
import com.aeh.thread.impl.DedicatedThread;
import com.aeh.thread.impl.DedicatedWatchDogImpl;

public class AEHHolder {
	private static volatile AEHHolder instance;



	Queue<ServerThread> threadPoolQ;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AsyncEventHandler>> handlerQueues;
	AEHLockUtility lockUtil;
	final int priorityCount;

	private AEHHolder(){
		priorityCount = 8;
		initialize();
	}
	public void initialize(){
		priorityObjects = new HashMap<Integer,PObject>();
		//instantiate each priority object
		for(int i=0;i<priorityCount;i++){
			PObject pobject = new PObject(i);
			pobject.setDedicatedFree(true);
			DedicatedWatchDog dedicatedWatchDog = new DedicatedWatchDogImpl();
			DedicatedThread dedicatedServerThread = new DedicatedThread();
			pobject.setDedicatedThread(dedicatedServerThread);
			pobject.setDedicatedWatchDog(dedicatedWatchDog);
			priorityObjects.put(i, pobject);
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
	public Queue<AsyncEventHandler> getQueue(int priority){
		return handlerQueues.get(priority);
	}
	public void setQueue(int index,Queue<AsyncEventHandler> queue){
		handlerQueues.set(index, queue);
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
	public boolean isThreadPoolEmpty(){
		return threadPoolQ.isEmpty();
	}

	public ServerThread getThreadFromThreadPool(){
		return threadPoolQ.poll();
	}


	public void enQueueHandler(List<AsyncEventHandler> eventHandlers){
		//aehHolder
		PriorityParameters param = (PriorityParameters)eventHandlers.get(0).getSchedulingParameters();
		int priority = param.getPriority();
		try {
			lockUtil.getQLock(priority);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handlerQueues.get(priority);
		Queue <AsyncEventHandler> queue = handlerQueues.get(priority);
		queue.addAll(eventHandlers);
		lockUtil.releaseQLock(priority);
		PObject pObject;
		System.out.println("Entering synchronized block");
		synchronized(pObject = priorityObjects.get(priority)){
			pObject.count.incrementAndGet();
			pObject.getDedicatedThread().notify();
		}
		//			lockUtil.notifyWatchDog(priority);

	}

}
