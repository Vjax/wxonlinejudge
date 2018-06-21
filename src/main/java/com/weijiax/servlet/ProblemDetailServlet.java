package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.ProblemDao;
import com.weijiax.entity.ProblemDetail;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.omg.CORBA.DATA_CONVERSION;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/problemDetail")
public class ProblemDetailServlet extends HttpServlet {

    private Connection connection;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ProblemDetailServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        String role = req.getParameter("role");
        String language = req.getParameter("language");
        int user_id = CastUtil.castInt(req.getParameter("user_id"));
        int problem_id = CastUtil.castInt(req.getParameter("problem_id"));
        int homework_id = CastUtil.castInt(req.getParameter("homework_id"));
        ProblemDao problemDao = new ProblemDao(connection);
        ProblemDetail problemDetail = problemDao.getProblemDetail(problem_id,role,user_id,homework_id,language);
        LOGGER.info(JsonUtil.toJson(problemDetail,"detail"));
        writer.write(JsonUtil.toJson(problemDetail,"detail"));
        DatabaseHelper.closeConnection(connection);
    }
}
