package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.StudentDao;
import com.weijiax.dao.TeacherDao;
import com.weijiax.entity.Student;
import com.weijiax.entity.Teacher;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.apache.commons.dbutils.QueryRunner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@WebServlet(urlPatterns = "/selectTeacher")
public class SelectTeacherServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        Statement statement = null;
        PrintWriter writer = resp.getWriter();
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        int student_id = CastUtil.castInt(req.getParameter("student_id"));
        StudentDao studentDao = new StudentDao(connection);
        int success = studentDao.selectTeacher(student_id,teacher_id);
        writer.write(JsonUtil.toJson(success,"message"));
        DatabaseHelper.closeConnection(connection);
    }
}
