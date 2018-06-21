package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.StudentDao;
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

@WebServlet(urlPatterns = "/showStudentTeachers")
public class ShowStudentTeachersServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        StudentDao studentDao = new StudentDao(connection);
        int student_id = CastUtil.castInt(req.getParameter("student_id"));
        List<Teacher> teachers = studentDao.getStudentTeachers(student_id);
        PrintWriter writer = resp.getWriter();
        writer.write(JsonUtil.toJson(teachers,"teachers"));
        DatabaseHelper.closeConnection(connection);
    }
}
