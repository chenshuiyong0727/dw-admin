package com.hf.common.infrastructure.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chensy
 * @function
 * @date 2022/4/19
 **/
@Slf4j
public class ThreadPoolUtils {

  /**
   * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
   */
  private static final int SIZE_CORE_POOL = 5;
  /**
   * 线程池维护线程的最大数量
   */
  private static final int SIZE_MAX_POOL = 200;
  /**
   * 线程池维护线程所允许的空闲时间
   */
  private static final long ALIVE_TIME = 2000;
  /**
   * 线程缓冲队列
   */
  private static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<>(1000);
  private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL,
      ALIVE_TIME, TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());

  static {
    pool.prestartAllCoreThreads();
  }

  public static ThreadPoolExecutor getPool() {
    return pool;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      int sout = i;
      ThreadPoolUtils.getPool().execute(() -> {
        log.info("i = {}", sout);
      });
    }
  }
}
