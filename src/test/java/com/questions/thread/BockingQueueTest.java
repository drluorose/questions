package com.questions.thread;

import java.util.Random;

/**
 * Created by dongjiejie on 16/3/23.
 */
public class BockingQueueTest {


    public static void main(String[] args) {

        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);

        final Random random = new Random();


        Thread threadA = new Thread("A") {
            public void run() {
                while (true) {
                    try {
                        blockingQueue.enqueue(random.nextInt(100));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread threadB = new Thread("B") {
            public void run() {
                while (true) {
                    try {
                        Integer integer = blockingQueue.dequeue();
                        System.out.println(integer);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        threadA.start();
        threadB.start();

    }


}
