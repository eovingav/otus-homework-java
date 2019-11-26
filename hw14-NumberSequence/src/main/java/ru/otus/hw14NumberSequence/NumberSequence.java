package ru.otus.hw14NumberSequence;

import java.util.concurrent.atomic.AtomicInteger;

public class NumberSequence extends Thread{

        private Object lock;
        private int maxCount = 10;
        private int minCount = 1;

        public NumberSequence(String name, Object lock){
            super(name);
            this.lock = lock;
        }

        public void run()
        {
           boolean wait = true;
           for (int i = minCount; i <= maxCount; i++){
               makeStep(i, wait);
           }
            for (int i = maxCount - 1; i >= minCount; i--){
                wait = i > minCount;
                makeStep(i, wait);
            }
        }

        private void makeStep(int i, boolean wait){
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + ": " + i);
                lock.notify();
                if (wait){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {

            Object lock = new Object();
            NumberSequence threadNumbers1 = new NumberSequence("Поток1", lock);
            NumberSequence threadNumbers2 = new NumberSequence("Поток2", lock);
            threadNumbers1.start();
            threadNumbers2.start();
        }

}
