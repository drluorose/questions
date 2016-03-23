package com.questions.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongjiejie on 16/3/23.
 */
public class BlockingQueueT<T> {

    private List<T> data = new LinkedList<>();

    private int limit;

    private ReentrantLock lock;

    private Condition full;

    private Condition empty;


    public BlockingQueueT(int limit) {
        this.limit = limit;
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();
    }

    /**
     * 入队列
     *
     * @param obj
     * @throws InterruptedException
     */
    public void enqueue(T obj) throws InterruptedException {
        lock.lock();
        while (data.size() == limit) {
            full.await();
        }
        if (data.size() == 0) {
            empty.signalAll();
        }
        empty.signal();
        data.add(obj);
        lock.unlock();
    }

    /**
     * 出队列
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized T dequeue() throws InterruptedException {
        lock.lock();
        while (data.size() == 0) {
            empty.await();
        }
        if (data.size() == limit) {
            full.signalAll();
        }
        full.signal();

        T obj = data.remove(0);
        lock.unlock();
        return obj;

    }


}
