package com.weijiax.dao;

import com.weijiax.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private Connection connection;

    public StudentDao(Connection connection){
        this.connection = connection;
    }

    public List<Teacher> getStudentTeachers(int student_id){
        TeacherDao teacherDao = new TeacherDao(connection);
        String sql = "select teacher_id from student_teacher where student_id = "+student_id;
        Statement statement = null;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int teacher_id = set.getInt("teacher_id");
                teachers.add(teacherDao.getTeacher(teacher_id));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teachers;
    }

    public List<Integer> getTeacherIdList(int student_id){
        String sql = "select teacher_id from student_teacher where student_id = "+student_id;
        Statement statement = null;
        List<Integer> teacherIdList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int teacher_id = set.getInt("teacher_id");
                teacherIdList.add(teacher_id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teacherIdList;
    }

    public int getTeacherId(int student_id){
        return 1;
    }
    public int selectTeacher(int student_id,int teacher_id){
        String sql = "insert into student_teacher values(?,?)";
        PreparedStatement statement = null;
        int success = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,student_id);
            statement.setInt(2,teacher_id);
            success = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }

    public int deleteTeacher(int student_id,int teacher_id){
        String sql = "delete from student_teacher where student_id = ? and teacher_id = ?";
        PreparedStatement statement = null;
        int row = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,student_id);
            statement.setInt(2,teacher_id);
            row = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return row;
    }

}
