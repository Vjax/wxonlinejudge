package com.weijiax.servlet;

import com.weijiax.dao.GradeDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/studentsGrade")
public class ShowStudentsGradeServlet extends HttpServlet {

    private Connection connection;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShowStudentsGradeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        GradeDao gradeDao = new GradeDao(connection);
        PrintWriter writer = resp.getWriter();
        int teacher_id = CastUtil.castInt(req.getParameter("id"));
        LOGGER.info(req.getParameter("teacher_id"));
        LOGGER.info(" "+teacher_id);
        LOGGER.info(JsonUtil.toJson(gradeDao.getStudentsGrade(7)));
        writer.write(JsonUtil.toJson(gradeDao.getStudentsGrade(teacher_id),"grades"));
    }
}
