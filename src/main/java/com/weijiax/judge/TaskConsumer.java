package com.weijiax.judge;

import com.weijiax.ConfigConstant;
import com.weijiax.dao.HomeworkDao;
import com.weijiax.helper.ConfigHelper;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.StreamUtil;
import com.weijiax.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TaskConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumer.class);

    private TaskQueue taskQueue;

    private static Map<String,String> COMPILE_COMMAND;

    private static Map<String,String> RUN_COMMAND;

    private Connection connection;


    static {
        /**
         * 编译命令
         */
        COMPILE_COMMAND = new HashMap<>();
        COMPILE_COMMAND.put("c","gcc main.c -o main");
        COMPILE_COMMAND.put("java","javac main.java");

        /**
         * 运行命令
         */
        RUN_COMMAND = new HashMap<>();
        RUN_COMMAND.put("c","./main");
        RUN_COMMAND.put("java","java main");

    }

    public TaskConsumer(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }

    /**
     * 从任务队列中取出任务
     * 先编译后运行
     */
    public void consume(){
        Task task = taskQueue.get();
        long startTime = System.currentTimeMillis();
        connection = DatabaseHelper.getConnection();
        int user_id = task.getUser_id();
        int problem_id = task.getProblem_id();
        int homework_id = task.getHomeword_id();
        String role = task.getRole();
        boolean is_test = task.isIs_test();
        LOGGER.info("consume solution: ");
        String language = task.getLanguage();
        boolean compileresult = compile(language,role,problem_id,user_id,homework_id,is_test);
        if (compileresult == true){
            LOGGER.info("compile success");
        }else {
            DatabaseHelper.closeConnection(connection);
            LOGGER.info("compile failure");
            return;
        }
        int timeLimit = getTimeLimit(problem_id,language);
        boolean judgeresult = judge(language,timeLimit,role,user_id,problem_id,homework_id,is_test);
        long endTime = System.currentTimeMillis();
        LOGGER.info("consume take "+(endTime-startTime)+"millsecond");
        DatabaseHelper.closeConnection(connection);
    }

    public int getTimeLimit(int problem_id,String language){
        return 300;
    }

    public void taskDone(String language,String role,int user_id,int problem_id,int homework_id){
        String sql = "update task set status = 2 where language = ? and role = ? and user_id = ? and problem_id = ? and homework_id = ?";
        PreparedStatement statement = null;
        try {
            int status = ConfigConstant.STATUS.finished.ordinal();
            statement = connection.prepareStatement(sql);
            statement.setString(1,language);
            statement.setString(2,role);
            statement.setInt(3,user_id);
            statement.setInt(4,problem_id);
            statement.setInt(5,homework_id);
            int row = statement.executeUpdate();
            LOGGER.error("task done"+row);
        }catch (SQLException e){
            LOGGER.error("task done failure");
            e.printStackTrace();
        }
    }
    /**
     * 编译
     * Process中exitValue（）返回的值若为0则编译没有错误 则返回true
     * 若为1 有错误 更新编译错误信息 并返回false
     * @return
     */
    public boolean compile(String language,String role,int problem_id,int user_id,int homework_id,boolean is_test){
        int result = ConfigConstant.RESULT.wrong_answer.ordinal();
        String extra = null;
        InputStream errorStream = null;
        Process p = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            p = runtime.exec(COMPILE_COMMAND.get(language),null,new File(ConfigHelper.getJudgeDir(role,homework_id)+"/"+user_id+"/"+problem_id));
            p.waitFor();
        }catch (Exception e){
            LOGGER.error("compile failure");
            e.printStackTrace();
        }
        if (p.exitValue() == 0){
            LOGGER.info("compile success");
            return true;
        }
        /**
         * TO-DO
         * 更新错误信息
         */
        errorStream = p.getErrorStream();
        result = ConfigConstant.RESULT.compile_error.ordinal();
        extra = StreamUtil.readStream(errorStream);
        updatePassRate(0,1,language,role,problem_id,user_id,homework_id);
        updateResult(result,extra,language,role,problem_id,user_id,homework_id);
        if (homework_id != 0 && is_test == false){
            updateHomeworkGrade(false,homework_id,user_id,0,1);
        }
        LOGGER.error(" compile failure");
        return false;
    }

    /**
     * 测试数据
     * 测试数据文件第一行为测试数据的个数
     * 每次从测试数据文件读取一行从正确输出文件读取一行
     * 运行后比对输出是否和正确输出相同
     * @param language
     * @param problem_id
     * @return
     */
    public boolean judge(String language,int timeLimit,String role,int user_id,int problem_id,int homework_id,boolean is_test){
        //测试数据的文件
        File inputDataFile = new File(ConfigHelper.getTestDatumDir()+"/"+problem_id+"/"+"in");
        //正确输出的文件
        File correctDataFile = new File(ConfigHelper.getTestDatumDir()+"/"+problem_id+"/"+"out");
        BufferedReader inputDataReader = null;
        BufferedReader correctDataReader = null;
        String inputData = null;
        String correctData = null;
        boolean result = true;
        int totalTestDataCount = 0;
        int passedDataCount = 0;
        try {
            inputDataReader = new BufferedReader(new FileReader(inputDataFile));
            correctDataReader = new BufferedReader(new FileReader(correctDataFile));
            totalTestDataCount = Integer.parseInt(inputDataReader.readLine());
            for (int i = 0;i < totalTestDataCount;i++){
                inputData = inputDataReader.readLine();
                correctData = correctDataReader.readLine();
                if (judgeOne(language,inputData,correctData,timeLimit,role,problem_id,user_id,homework_id)){
                    passedDataCount++;
                } else {
                    result = false;
                    LOGGER.info("passed "+passedDataCount+"data");
                    break;
                }
            }
            if(homework_id != 0 && is_test == false){
                /**
                 * 更新作业成绩
                 */
                updateHomeworkGrade(true,homework_id,user_id,passedDataCount,totalTestDataCount);
            }
            updatePassRate(passedDataCount,totalTestDataCount,language,role,problem_id,user_id,homework_id);
            if (passedDataCount == totalTestDataCount){
                updateResult(ConfigConstant.RESULT.accepted.ordinal(),"",language,role,problem_id,user_id,homework_id);
            }
        }catch (IOException e){
            LOGGER.error("read input data file failure");
            e.printStackTrace();
        }finally {
            try {
                inputDataReader.close();
                correctDataReader.close();
            }catch (IOException e){
                LOGGER.error("close reader failure");
                e.printStackTrace();
            }
        }
        return result;
    }

    public void updateHomeworkGrade(boolean is_compile_success,int homework_id,int student_id,int passedDataCount,int totalTestDataCount){
        HomeworkDao homeworkDao = new HomeworkDao(connection);
        int pass_rate = (int)(100*passedDataCount/totalTestDataCount);
        int grade = 0;
        if (is_compile_success == true && passedDataCount == 0){
            grade = 20;
        }else {
            grade = pass_rate;
        }
        homeworkDao.upgradeHomeworkGrade(student_id,homework_id,pass_rate,grade);
    }
    public void updatePassRate(int passedDataCount,int totalTestDataCount,String language,String role,int problem_id,int user_id,int homework_id){
        String sql = "update task set pass_rate = ? where language = ? and role = ? and problem_id = ? and user_id = ? and homework_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            byte passRate = (byte)(100*passedDataCount/totalTestDataCount);
            statement.setByte(1,passRate);
            statement.setString(2,language);
            statement.setString(3,role);
            statement.setInt(4,problem_id);
            statement.setInt(5,user_id);
            statement.setInt(6,homework_id);
            int row = statement.executeUpdate();
            LOGGER.info("update pass rate "+row);
        }catch (SQLException e){
            LOGGER.error("update pass rate failure");
            e.printStackTrace();
        }
    }


    /**
     * 测试一条数据
     * @param language
     * @param inputData
     * @param correctData
     * @return
     */
    public boolean judgeOne(String language,String inputData,String correctData,int timeLimit,String role,int problem_id,int user_id,int homework_id){
        int resultInfo = ConfigConstant.RESULT.wrong_answer.ordinal();
        String extra = null;
        Process p = null;
        Runtime runtime = Runtime.getRuntime();
        InputStream outputStream = null;
        OutputStream inputStream = null;
        InputStream errorStream = null;
        BufferedWriter writer = null;
        boolean result = false;
        try {
            p = runtime.exec(RUN_COMMAND.get(language),null,new File(ConfigHelper.getJudgeDir(role,homework_id)+"/"+user_id+"/"+problem_id));
            inputStream = p.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(inputStream));
            writer.write(inputData);
            writer.flush();
            writer.close();
            boolean isFinsihed = p.waitFor(timeLimit,TimeUnit.MILLISECONDS);
            if(isFinsihed == false){
                /**
                 * 超时
                 */
                resultInfo = ConfigConstant.RESULT.time_exceeded.ordinal();
                extra = inputData;
                updateResult(resultInfo,extra,language,role,problem_id,user_id,homework_id);
                return false;
            }
            outputStream = p.getInputStream();
            errorStream = p.getErrorStream();
            String output = StreamUtil.readStream(outputStream);
            String error = StreamUtil.readStream(errorStream);
            //错误流不为空 出现运行时错误
            if (StringUtil.isNotEmpty(error)){
                /**
                 * 更新错误信息
                 */
                resultInfo = ConfigConstant.RESULT.runtime_error.ordinal();
                extra = error;
                updateResult(resultInfo,extra,language,role,problem_id,user_id,homework_id);
                LOGGER.error("solution "+" error occur"+"\n"+error);
                return false;
            }
            LOGGER.info("solution "+" outputs "+output);
            result = judgeResult(output,correctData);
            if (result == false){
                /**
                 * 更新错误信息
                 */
                resultInfo = ConfigConstant.RESULT.wrong_answer.ordinal();
                extra = "input:"+inputData+"\noutput:"+output+"\nexpected:"+correctData;
                updateResult(resultInfo,extra,language,role,problem_id,user_id,homework_id);
                LOGGER.error("solution "+" result is wrong ");
                LOGGER.error("your answer: "+output+" correct answer: "+correctData);
                return false;
            }
        }catch (Exception e){
            LOGGER.error("judge error");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断输出是否正确
     * @param result
     * @param correctData
     * @return
     */
    public boolean judgeResult(String result ,String correctData){
        result = result.trim();
        correctData = correctData.trim();
        return result.equals(correctData);
    }

    public void updateResult(int result,String extra,String language,String role,int problem_id,int user_id,int homework_id){
        if (connection == null){
            connection = DatabaseHelper.getConnection();
        }
        String sql = "update task set result = ?,extra = ? where language = ? and role = ? and problem_id = ? and user_id = ? and homework_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,result);
            statement.setString(2,extra);
            statement.setString(3,language);
            statement.setString(4,role);
            statement.setInt(5,problem_id);
            statement.setInt(6,user_id);
            statement.setInt(7,homework_id);
            statement.executeUpdate();
            taskDone(language,role,user_id,problem_id,homework_id);
        }catch (SQLException e){
            LOGGER.error("update result failure");
            e.printStackTrace();
        }

    }
}
