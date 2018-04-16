package com.redis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerTest {

	public static void main(String[] args) throws ParseException {
		//timerTest();
		//timerTest2();
		//timerTest3();
		ScheduledTest();
	}
	
	/**间隔时间执行*/
	public static void timerTest(){
		for(int i = 0; i < 10; i++){
			new Timer("timer - "+i).schedule(new TimerTask(){
				@Override
				public void run(){
					System.out.println(Thread.currentThread().getName()+"run");
				}
			}, 10000);
		}
	}
	/** 固定时间执行 */
	public static void timerTest2() throws ParseException{
		for(int i = 0; i < 10; i++){
			new Timer("timer - "+i).schedule(new TimerTask(){
				@Override
				public void run(){
					System.out.println(Thread.currentThread().getName()+"run");
				}
			}, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-16 10:56:00"));
		}
	}
	
	/**延迟delay并隔period执行一次*/
	public static void timerTest3(){
		for (int i = 0; i < 10; ++i) {
            new Timer("timer - " + i).schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " run ");
                }
            }, 2000 , 5000);
        }
	}
	
	/**利用ScheduledThreadPoolExecutor 实现定时任务*/
	public static void ScheduledTest(){
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(10);
		for(int i = 0 ; i < 10; i++){
			executor.schedule(new Runnable() {
				
				public void run() {
					System.out.println(Thread.currentThread().getName()+"run");					
				}
			}, 10, TimeUnit.SECONDS);
		}
		executor.shutdown();
	}
}
