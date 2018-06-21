package com.weijiax.dao;

import com.weijiax.entity.StudentInfo;
import com.weijiax.entity.TeacherInfo;
import com.weijiax.util.JsonUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInfoDao {

    private Connection connection;

    public UserInfoDao(Connection connection){
        this.connection = connection;
    }

    public String getUserInfo(int id,String role){
        if (role.equals("teacher")){
            return getTeacherInfo(id);
        }else {
            return getStudentInfo(id);
        }
    }

    private String getTeacherInfo(int id){
        String sql = "select teacher_name,teacher_number from teacher where teacher_id = "+id;
        Statement statement = null;
        TeacherInfo info = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            if (set.next()){
                String teacher_name = set.getString("teacher_name");
                String teacher_number = set.getString("teacher_number");
                info = new TeacherInfo(teacher_name,teacher_number);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return JsonUtil.toJson(info,"user_info");
    }

    private String getStudentInfo(int id){
        String sql = "select student_name,student_number from student where student_id = "+id;
        Statement statement = null;
        StudentInfo info = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            if (set.next()){
                String student_name = set.getString("student_name");
                String student_number = set.getString("student_number");
                info = new StudentInfo(student_name,student_number);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return JsonUtil.toJson(info,"user_info");
    }
}
