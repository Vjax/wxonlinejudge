package com.weijiax.servlet;

import com.weijiax.dao.HomeworkDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(urlPatterns = "/assignHomework")
public class AssignHomeworkServlet extends HttpServlet{

    private Logger LOGGER = LoggerFactory.getLogger(AssignHomeworkServlet.class);
    private  Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        LOGGER.info(req.getQueryString());
        int teacher_id = CastUtil.castInt(req.getParameter("user_id"));
        String end_time = req.getParameter("end_time");
        String homeworks = req.getParameter("homeworks");
        String is_instant_parameter = req.getParameter("is_instant");
        LOGGER.info(teacher_id+" "+end_time+" "+homeworks);
        HomeworkDao homeworkDao = new HomeworkDao(connection);
        homeworkDao.assignHomeworks(teacher_id,homeworks,end_time);
        DatabaseHelper.closeConnection(connection);
    }
}
