package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.dao.SignUpDao;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.*;
import org.slf4j.LoggerFactory;
import sun.security.provider.MD5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        SignUpDao signUpDao = new SignUpDao(connection);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        String nick_name = req.getParameter("nick_name");
        String role = req.getParameter("role");
        String real_name = req.getParameter("real_name");
        String id = req.getParameter("id");
        String password = signUpDao.getPassword(nick_name,role);
        if (password != null){
            /**
             * 用户已经存在
             */
            writer.write("{ \"message\":0}");
        } else {
            password = req.getParameter("pass_word");
            switch (role){
                case "user":
                    signUpDao.signUpUser(nick_name,password);
                    break;
                case "student":
                    signUpDao.signUpStudent(real_name,nick_name,password,id);
                    break;
                case "teacher":
                    signUpDao.signUpTeacher(real_name,nick_name,password,id);
                    break;
            }
            /**
             * 注册成功
             */
            writer.write("{ \"message\":1,\"id\":"+signUpDao.getUserId(nick_name,role)+"}");
        }
        DatabaseHelper.closeConnection(connection);
    }


}
