package com.questions.thread;

/**
 * Created by dongjiejie on 16/3/2.
 */
public class ThreadABCJoinTest {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("A");
                }
            });
            thread1.start();
            thread1.join(); //主线在这里挂起,等thread1执行完

            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("B");
                }
            });
            thread2.start();
            thread2.join();

            Thread thread3 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("C");
                }
            });
            thread3.start();
            thread3.join();
        }
    }
}
