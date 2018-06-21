package com.weijiax.judge;

public class ProducerThread extends Thread{

    private TaskProducer producer;

    public ProducerThread(TaskQueue queue){
        producer = new TaskProducer(queue);
    }

    @Override
    public void run() {
        while (true){
            producer.produce();
            try {
                Thread.sleep(150);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
