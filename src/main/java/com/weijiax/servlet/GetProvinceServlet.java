package com.weijiax.servlet;

import com.weijiax.dao.ProvinceDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class GetProvinceServlet extends HttpServlet {

    public Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        ProvinceDao dao = new ProvinceDao(connection);
        writer.write(JsonUtil.toJson(dao.getProvinceList(),"provinces"));
        DatabaseHelper.closeConnection(connection);
    }
}
