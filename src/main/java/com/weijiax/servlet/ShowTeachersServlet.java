package com.weijiax.servlet;

import com.weijiax.dao.TeacherDao;
import com.weijiax.entity.Teacher;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/showTeachers")
public class ShowTeachersServlet extends HttpServlet {

    private Connection connection;
    int page = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        TeacherDao teacherDao = new TeacherDao(connection);
        String page_parameter = req.getParameter("page");
        if (page_parameter != null && !page_parameter.equals("")){
            page = CastUtil.castInt(page_parameter);
        }
        List<Teacher> teacherList = teacherDao.getTeachers(page);
        writer.write(JsonUtil.toJson(teacherList,"teachers"));
        DatabaseHelper.closeConnection(connection);
    }
}
