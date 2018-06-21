package com.weijiax.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weijiax.timer.ScheduleTask;
import com.weijiax.entity.Homework;
import com.weijiax.entity.Problem;
import com.weijiax.entity.StudentHomework;
import com.weijiax.helper.ConfigHelper;
import com.weijiax.util.JsonUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class HomeworkDao {

    private Connection connection;

    public HomeworkDao(Connection connection){
        this.connection = connection;
    }

    public List<Homework> getHomeworkListForTeacher(int teacher_id,boolean finished){
        String sql = "select homework_id,start_time,problem_id,end_time from homework where teacher_id = "+teacher_id+" and finished = "+finished;
        return getHomeworkList(sql,teacher_id);
    }

    public List<Homework> getHomeworkList(String sql,int teacher_id){
        String teacher_name = new TeacherDao(connection).getTeacherName(teacher_id);
        ProblemDao problemDao = new ProblemDao(connection);
        Statement statement = null;
        ArrayList<Homework> homeworks = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConfigHelper.getDateFormat());
        try {
            statement = connection.createStatement();
            ResultSet set =statement.executeQuery(sql);
            while (set.next()){
                int homework_id = set.getInt("homework_id");
                int problem_id = set.getInt("problem_id");
                Timestamp end_time_stamp = set.getTimestamp("end_time");
                Timestamp start_time_stamp = set.getTimestamp("start_time");
                Problem problem = problemDao.findProblem(problem_id);
                String start_time = simpleDateFormat.format(start_time_stamp);
                String end_time = simpleDateFormat.format(end_time_stamp);
                homeworks.add(new Homework(problem,teacher_name,end_time,start_time,homework_id));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return homeworks;
    }

    public List<Homework> getAllHomework(int teacher_id){
        String sql = "select homework_id,start_time,problem_id,end_time from homework where teacher_id = "+teacher_id;
        return getHomeworkList(sql,teacher_id);
    }
    public List<Homework> getUnfinishedHomeworkList(int student_id){
        StudentDao studentDao = new StudentDao(connection);
        String sql = "select homework_id from homework where teacher_id = ? and finished = false and homework_id not in (select homework_id from student_homework where student_id = "+student_id+" )";
        PreparedStatement statement = null;
        List<Integer> teacherIdList = studentDao.getTeacherIdList(student_id);
        ArrayList<Homework> homeworks = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            for (int teacher_id : teacherIdList){
                statement.setInt(1,teacher_id);
                ResultSet set = statement.executeQuery();
                while (set.next()){
                    int homework_id = set.getInt("homework_id");
                    homeworks.add(getHomework(homework_id));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return homeworks;
    }

    public Homework getHomework(int homework_id){
        String sql = "select homework_id,problem_id,start_time,end_time,teacher_id from homework where homework_id = "+homework_id;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConfigHelper.getDateFormat());
        ProblemDao problemDao = new ProblemDao(connection);
        TeacherDao teacherDao = new TeacherDao(connection);
        Statement statement = null;
        Homework homework = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int problem_id = set.getInt("problem_id");
                int teacher_id = set.getInt("teacher_id");
                Timestamp start_time_stamp = set.getTimestamp("start_time");
                Timestamp end_time_stamp = set.getTimestamp("end_time");
                String start_time = simpleDateFormat.format(start_time_stamp);
                String end_time = simpleDateFormat.format(end_time_stamp);
                Problem problem = problemDao.findProblem(problem_id);
                String teacher_name = teacherDao.getTeacherName(teacher_id);
                homework = new Homework(problem,teacher_name,end_time,start_time,homework_id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return homework;
    }


    public List<StudentHomework> getFinishedHomeworkList(int student_id){
        TeacherDao teacherDao = new TeacherDao(connection);
        StudentDao studentDao = new StudentDao(connection);
        ProblemDao problemDao = new ProblemDao(connection);
        String sql = "select grade,homework_id,pass_rate,finish_time from student_homework where student_id = "+student_id;
        ArrayList<StudentHomework> studentHomeworks = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConfigHelper.getDateFormat());
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int grade = set.getInt("grade");
                int homework_id = set.getInt("homework_id");
                int problem_id = getProblemId(homework_id);
                int pass_rate = set.getInt("pass_rate");
                Timestamp finish_time_stamp = set.getTimestamp("finish_time");
                String title = problemDao.getProblemTitle(getProblemId(homework_id));
                String teacher_name = teacherDao.getTeacherName(getTeacherId(homework_id));
                String finish_time = simpleDateFormat.format(finish_time_stamp);
                studentHomeworks.add(new StudentHomework(problem_id,grade,title,teacher_name,finish_time,pass_rate));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return studentHomeworks;
    }

    public int getTeacherId(int homework_id){
        String sql = "select teacher_id from homework where homework_id = "+homework_id;
        Statement statement = null;
        int teacher_id = 0;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()){
                teacher_id = set.getInt("teacher_id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teacher_id;
    }

    public int getProblemId(int homework_id){
        String sql = "select problem_id from homework where homework_id = "+homework_id;
        Statement statement = null;
        int problem_id = 0;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                problem_id = set.getInt("problem_id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return problem_id;
    }

    public void upgradeHomeworkGrade(int student_id,int homework_id,int pass_rate,int grade){
        String sql = "insert into student_homework(student_id,homework_id,pass_rate,grade,finish_time) values(?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement = connection.prepareStatement(sql);
            statement.setInt(1,student_id);
            statement.setInt(2,homework_id);
            statement.setInt(3,pass_rate);
            statement.setInt(4,grade);
            statement.setTimestamp(5,timestamp);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void assignHomeworks(int teacher_id,String homeworks_json,String end_time){
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao(connection);
        JSONArray array = JsonUtil.getJsonArray(homeworks_json);
        for (int i = 0;i < array.size();i++){
            JSONObject homework = array.getJSONObject(i);
            int problem_id = homework.getInteger("problem_id");
            String language = homework.getString("language");
            assignHomework(teacher_id,problem_id,language,end_time);
            shoppingCartDao.deleteFromShoppingCart(teacher_id,homeworks_json);
        }
    }
    public void  assignHomework(int teacher_id,int problem_id,String language,String end_time){
        String sql = "insert into homework(teacher_id,problem_id,language,start_time,end_time) values(?,?,?,?,?)";
        PreparedStatement statement = null;
        int homework_id = 0;
        try {
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConfigHelper.getDateFormat());
            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp end = new Timestamp(simpleDateFormat.parse(end_time).getTime());
            statement.setInt(1,teacher_id);
            statement.setInt(2,problem_id);
            statement.setString(3,language);
            statement.setTimestamp(4,start);
            statement.setTimestamp(5,end);
            statement.execute();
            ResultSet set = statement.getGeneratedKeys();
            while (set.next()){
                homework_id = set.getInt(1);
            }
            try {
                new Timer(true).schedule(new ScheduleTask(homework_id),new SimpleDateFormat(ConfigHelper.getDateFormat()).parse(end_time));
            }catch (ParseException e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
