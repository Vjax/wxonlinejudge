package com.weijiax.servlet;

import com.weijiax.dao.UserInfoDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(urlPatterns = "/userInfo")
public class UserInfoServlet extends HttpServlet{

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        UserInfoDao dao = new UserInfoDao(connection);
        int id = CastUtil.castInt(req.getParameter("id"));
        String role = req.getParameter("role");
        resp.getWriter().write(dao.getUserInfo(id,role));
        DatabaseHelper.closeConnection(connection);
    }
}
