package com.sure.communicate.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 指令，消息分发器
 * @author franson
 *
 */
public class MsgDispatcher {  
	 private static final int MAX_THREAD_NUM = 500; //50 
	    private static ExecutorService executorService =  
	            Executors.newFixedThreadPool(MAX_THREAD_NUM);  
	    public static void submit(Object msgObject)    
	            throws InstantiationException, IllegalAccessException {  
	        if(msgObject instanceof MsgHandlerBase){
	        	MsgHandlerBase executor =(MsgHandlerBase) msgObject;  
	 	        executorService.submit(executor); 
	        }
	    } 
}
