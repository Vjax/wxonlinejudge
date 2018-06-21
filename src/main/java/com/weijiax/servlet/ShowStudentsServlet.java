package com.weijiax.servlet;

import com.weijiax.dao.TeacherDao;
import com.weijiax.entity.Student;
import com.weijiax.entity.Teacher;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/showStudents")
public class ShowStudentsServlet extends HttpServlet {

    Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        TeacherDao teacherDao = new TeacherDao(connection);
        List<Student> students = teacherDao.getStudentList(teacher_id);
        PrintWriter writer = resp.getWriter();
        writer.write(JsonUtil.toJson(students,"students"));
        DatabaseHelper.closeConnection(connection);
    }
}
