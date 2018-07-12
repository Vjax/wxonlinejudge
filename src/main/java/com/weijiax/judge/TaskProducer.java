package com.weijiax.judge;

import com.weijiax.ConfigConstant;
import com.weijiax.helper.ConfigHelper;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TaskProducer {

    private static Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);

    private static Map<String,String> FILE_NAME;

    private TaskQueue taskQueue;

    private Connection connection;

    private static String getTaskSql = "select * from task where status=0 limit "+ConfigHelper.getFetchCount();
    private static String updateTaskStatusToProcessingSql = "update task set status = ? where solution_id = ?";

    public TaskProducer(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
        connection = DatabaseHelper.getConnection();
    }

    static{
        FILE_NAME = new HashMap<>();
        FILE_NAME.put("cpp","main.cpp");
        FILE_NAME.put("java","main.java");
    }

    /**
     * 生产任务 从数据库中取出n个task
     * 把代码存入文件
     * task的status 0代表未被处理 1代表正在被处理 2代表完成
     */
    public void produce(){
        try {
            if (connection.isClosed() || connection == null){
                connection = DatabaseHelper.getConnection();
            }
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(getTaskSql);
            PreparedStatement updateStatement = connection.prepareStatement(updateTaskStatusToProcessingSql);
            while (resultSet.next()){
                long startTime = System.currentTimeMillis();
                int status = ConfigConstant.STATUS.processing.ordinal();
                String language = resultSet.getString("language");
                String role = resultSet.getString("role");
                int problem_id = resultSet.getInt("problem_id");
                int user_id = resultSet.getInt("user_id");
                int homework_id = resultSet.getInt("homework_id");
                String code = resultSet.getString("code");
                boolean is_test = resultSet.getBoolean("is_test");
                LOGGER.info("produce solution ");
                updateStatus(ConfigConstant.STATUS.processing.ordinal(),language,role,user_id,problem_id,homework_id);
                writeIntoFile(code,language,problem_id,role,user_id,homework_id);
                Task task = new Task(problem_id,user_id,language,role,homework_id,is_test);
                taskQueue.put(task);
                long endTime = System.currentTimeMillis();
                LOGGER.info("produce take "+(endTime-startTime)+" millseconds");
            }
        }catch (Exception e){
            LOGGER.error("produce task failure",e);
        }
    }


    public void updateStatus(int status,String language,String role,int user_id,int problem_id,int homework_id){
        String sql = "update task set status = ? where language = ? and role = ? and user_id = ? and problem_id = ? and homework_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,status);
            statement.setString(2,language);
            statement.setString(3,role);
            statement.setInt(4,user_id);
            statement.setInt(5,problem_id);
            statement.setInt(6,homework_id);
            statement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("task done failure");
            e.printStackTrace();
        }
    }
    /**
     * 创建文件然后把代码写入文件
     * @param code
     * @param language
     */
    public  String writeIntoFile(String code,String language,int problem_id,String role,int user_id,int homework_id){
        File solutionFile = createFile(language,role,user_id,homework_id,problem_id);
        if (solutionFile != null){
            writeCodeIntoFile(code,solutionFile,problem_id,language);
        }else{
            LOGGER.error("write solution into file failure");
        }
        return solutionFile.getPath();
    }

    /**
     * 创建solution文件
     * @param language
     * @return
     */
    public File createFile(String language,String role,int user_id,int homework_id,int problem_id){
        String filename = FILE_NAME.get(language);
        File userDir = null;
        File solutionDir = null;
        File solutionFile = null;
        try{
            String userDirPath = ConfigHelper.getJudgeDir(role,homework_id)+"/"+user_id;
            userDir = new File(userDirPath);
            if (!userDir.exists()){
                userDir.setWritable(true,true);
                userDir.mkdir();
            }
            //创建solution文件夹
            String dirName = userDirPath+"/"+problem_id;
            solutionDir = new File(dirName);
            if (!solutionDir.exists()){
                solutionDir.setWritable(true,true);
                solutionDir.mkdir();
            }
            //创建solution文件
            String fullFilePath = dirName+"/"+filename;
            solutionFile = new File(fullFilePath);
        }catch (Exception e){
            LOGGER.error("create file failure");
            e.printStackTrace();
        }
        return solutionFile;
    }

    /**
     * 文件格式目前只参考java
     * 把代码和主函数写入文件
     * 通过problem_id找到主函数的文件
     * 然后把代码插入主函数 存到solution文件
     * @param code
     * @param solutionFile
     */
    public void writeCodeIntoFile(String code, File solutionFile,int problem_id,String language){
        FileWriter writer = null;
        String mainMethodFilePath = ConfigHelper.getMainMethodDir()+"/"+problem_id+"/"+FILE_NAME.get(language);
        InputStream mainMethodFileStream = null;
        try {
            //打开主函数文件读取文件流并转化为字符串
            mainMethodFileStream = new FileInputStream(new File(mainMethodFilePath));
            String mainMethod = StreamUtil.readStream(mainMethodFileStream);
            writer = new FileWriter(solutionFile);
            //从第一个大括号处切开 左边为类声明右边为主函数
            int splitPos = mainMethod.indexOf("{");
            writer.write(mainMethod.substring(0,splitPos+1));
            writer.write("\n");
            writer.write(code);
            writer.write("\n");
            writer.write(mainMethod.substring(splitPos+1));
        }catch (Exception e){
            LOGGER.error("write code into file failure");
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
