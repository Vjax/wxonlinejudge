package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.ConfigConstant;
import com.weijiax.dao.DataAnalysisDao;
import com.weijiax.entity.DataAnalysis;
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
import java.sql.*;

@WebServlet(urlPatterns = "/data")
public class DataAnalysisServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        DataAnalysisDao dataAnalysisDao = new DataAnalysisDao(connection);
        PrintWriter writer = resp.getWriter();
        String role = req.getParameter("role");
        int user_id = CastUtil.castInt(req.getParameter("user_id"));
        int totalProblemCount = dataAnalysisDao.getTotalProblemCount();
        int finishedProblemCount = dataAnalysisDao.getFinishedProblemCount(role,user_id);
        int submitProblemCount = dataAnalysisDao.getSubmitProblemCount(role,user_id);
        DataAnalysis data = new DataAnalysis(totalProblemCount-submitProblemCount,
                finishedProblemCount,submitProblemCount-finishedProblemCount);
        writer.write(JsonUtil.toJson(data,"data"));
        DatabaseHelper.closeConnection(connection);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }






}
