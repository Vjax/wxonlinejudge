package com.weijiax.servlet;

import com.weijiax.dao.StudentDao;
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

@WebServlet(urlPatterns = "/deleteTeacher")
public class DeleteTeacherServlet extends HttpServlet {

    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(DeleteTeacherServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        StudentDao studentDao = new StudentDao(connection);
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        int student_id = CastUtil.castInt(req.getParameter("student_id"));
        LOGGER.info(teacher_id+"+"+student_id);
        int success = studentDao.deleteTeacher(student_id,teacher_id);
        resp.getWriter().write(JsonUtil.toJson(success,"message"));
        DatabaseHelper.closeConnection(connection);
    }
}
