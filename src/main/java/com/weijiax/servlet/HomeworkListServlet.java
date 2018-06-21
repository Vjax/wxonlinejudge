package com.weijiax.servlet;
import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.HomeworkDao;
import com.weijiax.entity.Homework;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import com.weijiax.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = "/homeworkListForTeacher")
public class HomeworkListServlet extends HttpServlet {
    public Connection connection;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        int teacher_id= CastUtil.castInt(req.getParameter("teacher_id"));
        boolean finished = CastUtil.castBoolean(req.getParameter("finished"));
        HomeworkDao homeworkDao = new HomeworkDao(connection);
        writer.write(JsonUtil.toJson(homeworkDao.getHomeworkListForTeacher(teacher_id,finished),"homeworks"));
        DatabaseHelper.closeConnection(connection);
    }
}
