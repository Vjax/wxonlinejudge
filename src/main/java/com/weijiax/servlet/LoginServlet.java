package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.SignUpDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.JsonUtil;
import com.weijiax.util.MD5Util;
import com.weijiax.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    public Connection connection;

    private Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        SignUpDao signUpDao = new SignUpDao(connection);
        String nick_name = req.getParameter("nick_name");
        String role = req.getParameter("role");
        String password = signUpDao.getPassword(nick_name,role);
        if (password == null){
            /**
             * 用户不存在
             */
            writer.write("{ \"message\":0}");
        } else {
            if (password.equals(req.getParameter("password"))){
                /**
                 * 密码正确
                 */
                int id = signUpDao.getUserId(nick_name,role);
                writer.write("{ \"message\":1,\"id\":"+id+"}");
            } else {
                /**
                 * 密码不正确
                 */
                writer.write("{ \"message\":2}");
            }
        }
        DatabaseHelper.closeConnection(connection);
    }




}
