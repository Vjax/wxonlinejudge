package com.weijiax.helper;

import com.weijiax.ConfigConstant;
import com.weijiax.util.PropUtil;

import java.util.Properties;

public class ConfigHelper {

    /**
     * 读取配置文件
     */
    public static final Properties CONFIG_PROPS = PropUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动
     */
    public static String getJdbcDriver(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC URL
     */
    public static String getJdbcUrl(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }

    /**
     * 获取JDBC用户名
     */
    public static String getJdbcUsername(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取JDBC密码
     */
    public static String getJdbcPassword(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取一次查询的task的数量
     */
    public static int getFetchCount(){ return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.FETCH_COUNT); }

    /**
     * 获取普通用户判题目录
     */
    public static String getUserJudgeDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.USER_JUDGE_DIR);
    }

    /**
     * 获取学生判题目录
     */
    public static String getStudentJudgeDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.STUDENT_JUDGE_DIR);
    }

    /**
     * 获取作业判题目录
     */
    public static String getHomeworkJudgeDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.HOMEWORK_JUDGE_DIR);
    }

    public static String getJudgeDir(String role,int homework_id){
        if (homework_id != 0){
            return getHomeworkJudgeDir();
        } else {
            if (role.equals("user")){
                return getUserJudgeDir();
            } else {
                return getStudentJudgeDir();
            }
        }
    }

    /**
     * 获取problem目录
     */
    public static String getProblemDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.PROBLEM_DIR);
    }

    /**
     * 获取测试数据文件夹
     */
    public static String getTestDatumDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.TESTDATUM_DIR);
    }

    /**
     * 获取主函数文件夹
     */
    public static String getMainMethodDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.MAINMETHOD_DIR);
    }

    /**
     * 获取队列容量
     */
    public static int getQueueSize(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.QUEUE_SIZE);
    }

    /**
     * 获取生产者线程数量
     */
    public static int getConsumerCount(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.CONSUMER_COUNT);
    }

    /**
     * 获取消费者线程数量
     */
    public static int getProducerCount(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.PRODUCER_COUNT);
    }

    public static int getInitialPoolSize(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.INITIAL_POOL_SIZE);
    }

    public static int getMaxIdleTime(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.MAX_IDLE_TIME);
    }

    public static int getMaxPoolSize(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.MAX_POOL_SIZE);
    }

    public static int getMinPoolSize(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.MIN_POOL_SIZE);
    }

    public static int getMaxStatement(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.MAX_STATEMENTS);
    }

    public static int getSelectCount(){
        return PropUtil.getInt(CONFIG_PROPS,ConfigConstant.SELECT_COUNT);
    }

    public static String getDateFormat(){return PropUtil.getString(CONFIG_PROPS,ConfigConstant.DATE_FORMAT);}

    public static String getArticalDir(){
        return PropUtil.getString(CONFIG_PROPS,ConfigConstant.ARTICAL_DIR);
    }
}
