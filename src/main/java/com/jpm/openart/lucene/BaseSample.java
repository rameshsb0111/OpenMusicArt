package com.jpm.openart.lucene;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class BaseSample {

	  protected ListeningExecutorService executorService;

	    public BaseSample(){
	        executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
	    }

	    public  void shutDown(){
	        executorService.shutdownNow();
	    }
}
