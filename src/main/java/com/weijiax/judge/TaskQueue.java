package com.weijiax.judge;

import com.weijiax.helper.ConfigHelper;
import com.weijiax.judge.Task;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskQueue {

    private BlockingDeque<Task> taskQueue;

    public TaskQueue(){
        taskQueue = new LinkedBlockingDeque<>(ConfigHelper.getQueueSize());
    }

    public Task get(){
        Task task = null;
        try{
            task = taskQueue.take();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return task;
    }

    public void put(Task task){
        try {
            taskQueue.put(task);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public boolean isEmpty(){
        return taskQueue.isEmpty();
    }
}
