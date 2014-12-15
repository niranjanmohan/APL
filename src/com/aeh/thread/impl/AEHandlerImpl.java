package com.aeh.thread.impl;

import java.util.HashSet;
import java.util.Set;

import com.aeh.AEHHolder;
import com.aeh.thread.AEHandler;

public class AEHandlerImpl implements AEHandler {
	private int priority;
	private String desc;
	private long firetime;
	private AEHHolder aehHolder;

	//	public AEHandlerImpl(int p){
	//		priority = p;
	//	}

	public AEHandlerImpl(String str){
		desc = str;
	}
	
	public AEHandlerImpl(String str, AEHHolder holder){
		desc = str;
		aehHolder = holder;
	}

	@Override
	public void handlerLogic(){
		seivePrime(200000);
		long response = (System.nanoTime() - firetime)/1000;
		aehHolder.countHandlers.set(priority, aehHolder.countHandlers.get(priority)+ 1);
		aehHolder.responseTime.set(priority, aehHolder.responseTime.get(priority)+ Integer.parseInt(String.valueOf(response)));
		//System.out.println(desc+"["+priority+"] {"+response+"}");
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
	//sieve eratosthenes
	public  void seivePrime(int range){
		Boolean [] isPrime = new Boolean[range];
		Set<Integer> s = new HashSet<Integer>();
		for(int i=0;i<range;i++){
			isPrime[i] = true;
		}
		for(int i=2;i*i < range ;i++){
			if(isPrime[i]){
				for(int j=i;i*j< range ;j++){
					isPrime[j*i] = false;
				}
			}
		}
		//System.out.println(isPrime.length);
		for(int k=2;k<range;k++){
			if(isPrime[k])
				s.add(k);
		}
	}

}
