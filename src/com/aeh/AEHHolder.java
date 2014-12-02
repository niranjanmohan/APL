package com.aeh;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.realtime.AsyncEventHandler;

import com.aeh.commonobjects.AsyncEventWrapper;
import com.aeh.commonobjects.PObject;
import com.aeh.commonobjects.ThreadPool;

public class AEHHolder {
	ThreadPool threadPool;
	Map<Integer,PObject> priorityObjects;
	PriorityQueue<Integer> pQueue;
	List<Queue<AsyncEventWrapper>> handlerQueues;
	
	public void enQueueHandler(AsyncEventHandler aehHolder){
		
		//aehHolder
		
	}
	
}
