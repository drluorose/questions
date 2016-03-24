package com.questions.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by dongjiejie on 16/3/24.
 */
public class Storage<T> {

    private final int MAX_SIZE = 100;


    private LinkedList<T> data = new LinkedList<>();

    /**
     * 生产方法
     *
     * @param num
     * @param values
     */
    public void produce(int num, List<T> values) {
        synchronized (data) {
            while (data.size() + num > MAX_SIZE) {
                System.out.println("[要生产的产品数量]:" + num);
                System.out.println(" 【库存量】:" + data.size() + " 暂时不能执行生产任务!");

                try {
                    data.wait();// 由于条件不满足，生产阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 生产条件满足情况下，生产num个产品
            for (int i = 0; i < num; ++i) {
                data.add(values.get(i));
            }

            System.out.print("【已经生产产品数】:" + num);
            System.out.println(" 【现仓储量为】:" + data.size());

            data.notifyAll();
        }
    }


    // 消费num个产品
    public void consume(int num) {
        // 同步代码段
        synchronized (data) {
            // 如果仓库存储量不足
            while (data.size() < num) {
                System.out.print("【要消费的产品数量】:" + num);
                System.out.println(" 【库存量】:" + data.size() + " 暂时不能执行生产任务!");

                try {
                    // 由于条件不满足，消费阻塞
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i) {
                data.remove();
            }

            System.out.print("【已经消费产品数】:" + num);
            System.out.println(" 【现仓储)量为】:" + data.size());

            data.notifyAll();
        }
    }
}
