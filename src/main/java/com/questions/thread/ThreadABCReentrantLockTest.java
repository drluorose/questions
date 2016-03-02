package com.questions.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongjiejie on 16/3/2.
 */
public class ThreadABCReentrantLockTest {

    public static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    public static final Condition A_CONDITION = REENTRANT_LOCK.newCondition();

    public static final Condition B_CONDITION = REENTRANT_LOCK.newCondition();

    public static final Condition C_CONDITION = REENTRANT_LOCK.newCondition();

    private static String task = "A";

    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadB().start();
        new ThreadC().start();
    }

    public static class ThreadA extends Thread {


        @Override
        public void run() {
            while (true) {
                REENTRANT_LOCK.lock();
                // TODO: 16/3/2
                if (!task.equals("A")) {
                    try {
                        A_CONDITION.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("A");
                task = "B";
                B_CONDITION.signal();
                REENTRANT_LOCK.unlock();
            }
        }
    }

    public static class ThreadB extends Thread {


        @Override
        public void run() {
            while (true) {
                REENTRANT_LOCK.lock();
                if (!task.equals("B")) {
                    try {
                        B_CONDITION.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("B");
                task ="C";
                C_CONDITION.signal();
                REENTRANT_LOCK.unlock();
            }
        }
    }

    public static class ThreadC extends Thread {

        @Override
        public void run() {
            while (true) {

                REENTRANT_LOCK.lock();
                if (!task.equals("C")) {
                    try {
                        C_CONDITION.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // TODO: 16/3/2
                System.out.print("C\n");
                task="A";
                A_CONDITION.signal();

                REENTRANT_LOCK.unlock();
            }
        }
    }


}
