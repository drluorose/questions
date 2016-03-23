package com.questions.thread;

import java.util.LinkedList;

/**
 * Created by dongjiejie on 16/3/23.
 */
public class BlockingQueue<T> {

    private LinkedList<T> data = new LinkedList<>();

    private int limit = 100;

    /**
     * 构建一个limit大小限制的阻塞队列
     * @param limit
     */
    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * 入队列
     * @param obj
     * @throws InterruptedException
     */
    public synchronized void enqueue(T obj) throws InterruptedException {
        while (data.size() == limit) {
            wait();
        }

        if (data.size() == 0) {
            notifyAll();
        }

        data.add(obj);
    }

    /**
     * 出队列
     * @return
     * @throws InterruptedException
     */
    public synchronized T dequeue() throws InterruptedException {
        while (data.size() == 0) {
            wait();
        }
        if (data.size() == limit) {
            notifyAll();
        }
        return data.remove(0);
    }


}
