package com.weijiax.dao;

import com.weijiax.entity.Homework;
import com.weijiax.entity.Student;
import com.weijiax.entity.StudentGrade;
import com.weijiax.helper.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDao {

    private Connection connection;

    public GradeDao(Connection connection){
        this.connection = connection;
    }

    public List<StudentGrade> getStudentsGrade(int teacher_id){
        TeacherDao teacherDao = new TeacherDao(connection);
        HomeworkDao homeworkDao = new HomeworkDao(connection);
        List<Student> students = teacherDao.getStudentList(teacher_id);
        List<Homework> homeworks = homeworkDao.getAllHomework(teacher_id);
        String sql = "select grade from student_homework where homework_id = ? and student_id = ?";
        PreparedStatement statement = null;
        ArrayList<StudentGrade> result = new ArrayList<>();
        for (Student student : students){
            int student_id = student.getStudent_id();
            List<Integer> grades = new ArrayList<>();
            try {
                statement = connection.prepareStatement(sql);
                for (Homework homework : homeworks){
                    int grade = 0;
                    statement.setInt(1,homework.getHomework_id());
                    statement.setInt(2,student_id);
                    ResultSet set = statement.executeQuery();
                    while (set.next()){
                        grade = set.getInt("grade");
                    }
                    grades.add(grade);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            result.add(new StudentGrade(student.getStudent_name(),grades));
        }
        return result;
    }
}
