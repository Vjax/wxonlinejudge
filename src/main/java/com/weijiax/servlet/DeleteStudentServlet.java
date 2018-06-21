package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.TeacherDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(DeleteStudentServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        String students = req.getParameter("students");
        LOGGER.info(teacher_id+" "+students);
        TeacherDao teacherDao = new TeacherDao(connection);
        teacherDao.deleteStudents(students,teacher_id);
        DatabaseHelper.closeConnection(connection);
    }
}
