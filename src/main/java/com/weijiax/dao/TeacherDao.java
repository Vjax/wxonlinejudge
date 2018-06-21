package com.weijiax.dao;

import com.alibaba.fastjson.JSONArray;
import com.weijiax.entity.Student;
import com.weijiax.entity.Teacher;
import com.weijiax.util.JsonUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {

    private Connection connection;

    public TeacherDao(Connection connection){
        this.connection = connection;
    }

    public String getTeacherName(int teacher_id){
        String sql = "select teacher_name from teacher where teacher_id = "+teacher_id;
        Statement statement = null;
        String name = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                name = set.getString("teacher_name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }



    public Teacher getTeacher(int teacher_id){
        String sql = "select teacher_name,teacher_language,university from teacher where teacher_id = "+teacher_id;
        Teacher teacher = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                String teacher_name = set.getString("teacher_name");
                String teahcer_language = set.getString("teacher_language");
                String university = set.getString("university");
                teacher = new Teacher(teacher_id,teacher_name,university,teahcer_language);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teacher;
    }

    public List<Teacher> getTeachers(int page){
        String sql = "select teacher_id,teacher_name,teacher_language,university from teacher limit "+(page-1)*10+","+page*10;
        ArrayList<Teacher> teachers = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int teacher_id = set.getInt("teacher_id");
                String teacher_name = set.getString("teacher_name");
                String teahcer_language = set.getString("teacher_language");
                String university = set.getString("university");
                teachers.add(new Teacher(teacher_id,teacher_name,university,teahcer_language));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return teachers;
    }

    public Student getStudent(int student_id){
        String sql = "select student_name,student_number from student where student_id = "+student_id;
        Statement statement = null;
        Student student = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                String student_name = set.getString("student_name");
                String student_number= set.getString("student_number");
                student = new Student(student_name,student_number,student_id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return student;
    }
    public List<Student> getStudentList(int teacher_id){
       String sql = "select student_id from student_teacher where teacher_id = "+teacher_id;
       Statement statement = null;
       ArrayList<Student> students = new ArrayList<>();
       try {
           statement = connection.createStatement();
           ResultSet set = statement.executeQuery(sql);
           while (set.next()){
               int student_id = set.getInt("student_id");
               students.add(getStudent(student_id));
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
       return students;
    }

    public void deleteStudents(String students,int teacher_id){
        JSONArray array = JsonUtil.getJsonArray(students);
        for (int i = 0;i < array.size();i++){
            deleteOneStudent(array.getIntValue(i),teacher_id);
        }
    }
    public void deleteOneStudent(int student_id,int teacher_id){
        String sql = "delete from student_teacher where teacher_id = ? and student_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,teacher_id);
            statement.setInt(2,student_id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
