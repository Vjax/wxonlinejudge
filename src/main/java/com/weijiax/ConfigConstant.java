package com.weijiax;

import java.util.HashMap;
import java.util.Map;

public interface ConfigConstant {

    /**
     * properties文件的常量
     */

    /**
     * 配置文件名称
     */
    String CONFIG_FILE = "config.properties";

    /**
     * JDBC参数
     */
    String JDBC_DRIVER = "com.weijiax.jdbc.driver";
    String JDBC_URL = "com.weijiax.jdbc.url";
    String JDBC_USERNAME = "com.weijiax.jdbc.username";
    String JDBC_PASSWORD = "com.weijiax.jdbc.password";

    /**
     * 每次最多从数据库中取多少条任务
     */
    String FETCH_COUNT = "com.weijiax.fetchcount";

    /**
     * 普通用户判题目录
     */
    String USER_JUDGE_DIR = "com.weijiax.userjudgedir";

    /**
     * 学生判题目录
     */
    String STUDENT_JUDGE_DIR = "com.weijiax.studentjudgedir";

    /**
     * 作业判题目录
     */
    String HOMEWORK_JUDGE_DIR = "com.weijiax.homeworkjudgedir";

    /**
     * problem文件目录
     */
    String PROBLEM_DIR = "com.weijiax.problemdir";

    /**
     * 主函数文件夹
     */
    String MAINMETHOD_DIR = "com.weijiax.mainmethoddir";

    /**
     * 测试数据文件夹
     */
    String TESTDATUM_DIR = "com.weijiax.testdatumdir";

    /**
     * 队列容量
     */
    String QUEUE_SIZE = "com.weijiax.queuesize";

    /**
     * 生产者线程数目
     */
    String PRODUCER_COUNT = "com.weijiax.producercount";

    /**
     * 消费者线程数目
     */
    String CONSUMER_COUNT = "com.weijiax.consumercount";

    String INITIAL_POOL_SIZE = "com.weijiax.c3p0.mysql.initialPoolSize";

    String MAX_IDLE_TIME = "com.weijiax.c3p0.mysql.maxIdleTime";

    String MAX_POOL_SIZE = "com.weijiax.c3p0.mysql.maxPoolSize";

    String MIN_POOL_SIZE = "com.weijiax.c3p0.mysql.minPoolSize";

    String MAX_STATEMENTS = "com.weijiax.c3p0.mysql.maxStatements";

    enum STATUS{unprocessed,processing,finished}

    enum ROLE{user,student,teacher}

    enum RESULT{accepted,wrong_answer,time_exceeded,compile_error,runtime_error}

    String SELECT_COUNT = "com.weijiax.selectCount";

    String DATE_FORMAT = "com.weijiax.dateformat";

    String ARTICAL_DIR = "com.weijiax.articaldir";

}
