package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.HomeworkDao;
import com.weijiax.entity.Homework;
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

@WebServlet(urlPatterns = "/showHomeworkForStudents")
public class StudentHomeworkServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        HomeworkDao homeworkDao = new HomeworkDao(connection);
        int student_id = CastUtil.castInt(req.getParameter("student_id"));
        boolean finished = CastUtil.castBoolean(req.getParameter("finished"));
        if (finished == true){
            writer.write(JsonUtil.toJson(homeworkDao.getFinishedHomeworkList(student_id),"homeworks"));
        }else {
            writer.write(JsonUtil.toJson(homeworkDao.getUnfinishedHomeworkList(student_id),"homeworks"));
        }
        DatabaseHelper.closeConnection(connection);
    }
}
