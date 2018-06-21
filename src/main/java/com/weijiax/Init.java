package com.weijiax;

import com.weijiax.helper.ConfigHelper;
import com.weijiax.judge.ConsumerThread;
import com.weijiax.judge.ProducerThread;
import com.weijiax.judge.TaskQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {

    private Logger LOGGER = LoggerFactory.getLogger(Init.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /**
         * 多线程测试代码
         */
        LOGGER.info("context initialized");
        TaskQueue queue = new TaskQueue();
        ProducerThread[] producerThreads = new ProducerThread[ConfigHelper.getProducerCount()];
        ConsumerThread[] consumerThreads = new ConsumerThread[ConfigHelper.getConsumerCount()];

        for(ProducerThread producerThread : producerThreads){
            producerThread = new ProducerThread(queue);
            producerThread.setDaemon(true);
            producerThread.start();
        }
        for (ConsumerThread consumerThread : consumerThreads){
            consumerThread = new ConsumerThread(queue);
            consumerThread.setDaemon(true);
            consumerThread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("context destroyed");
    }
}
