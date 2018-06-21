package com.weijiax.judge;

public class ConsumerThread extends Thread{

    private TaskConsumer consumer;

    public ConsumerThread(TaskQueue queue){
        consumer = new TaskConsumer(queue);
    }

    @Override
    public void run() {
        while (true){
            consumer.consume();
            try {
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
