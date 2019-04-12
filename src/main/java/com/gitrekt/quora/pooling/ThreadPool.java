package com.gitrekt.quora.pooling;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {

  private ThreadPoolExecutor executor;
  private int numOfThreads;

  private ThreadPool() {
    numOfThreads = Integer.parseInt(System.getenv("THREAD_POOL_COUNT"));
    executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
  }

  public void run(Runnable runnable) {
    executor.execute(runnable);
  }

  public ThreadPoolExecutor getExecutor() {
    return executor;
  }

  public int getNumOfThreads() {
    return numOfThreads;
  }

  public void setNumOfThreads(int numOfThreads) {
    this.numOfThreads = numOfThreads;
    executor.setCorePoolSize(numOfThreads);
  }

  private static class ThreadPoolHelper {
    private static final ThreadPool INSTANCE = new ThreadPool();
  }

  public static ThreadPool getInstance() {
    return ThreadPoolHelper.INSTANCE;
  }
}
