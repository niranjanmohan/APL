package com.aeh.test;



import javax.realtime.NoHeapRealtimeThread;



public class HelloWorld {

	Runnable r1;


	public static void main(String[] args) throws RuntimeException {
		Runnable r1 = new Runnable(){
			@Override
			public void run(){
				while(true){
					System.out.println(" thread 1 thread 1");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Runnable r2 = new Runnable(){
			@Override
			public void run(){
				while(true){
					System.out.println("thread 2 thread 2");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};


		NoHeapRealtimeThread nh = new NoHeapRealtimeThread(r1);
		NoHeapRealtimeThread nh2 = new NoHeapRealtimeThread(r2);
		nh.start();
		nh2.start();

		System.out.println("Hello Fiji");


	}
}