package com.softisland.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * Created by liwx on 2016/4/26.
 */
public class CuratorLocks {

    private final InterProcessMutex lock;

    public CuratorLocks(CuratorFramework client, String lockPath) {
        lock = new InterProcessMutex(client, lockPath);
    }

    /**
     * 获取锁
     * @param time
     * @param unit
     * @throws Exception
     */
    public void getLock(long time, TimeUnit unit)throws Exception{
        if ( !lock.acquire(time, unit)){
            throw new IllegalStateException(Thread.currentThread().getName()+" could not acquire the lock");
        }
    }

    /**
     * 释放锁
     * @throws Exception
     */
    public void releaseLock()throws Exception{
        lock.release();
    }
}
