package com.weijiax.servlet;

import com.weijiax.dao.ShoppingCartDao;
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
import java.sql.Connection;

@WebServlet(urlPatterns = "/deleteFromShoppingCart")
public class DeleteFromShoppingCart extends HttpServlet {

    private Logger LOGGER = LoggerFactory.getLogger(AddToShoppingCartServlet.class);
    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        String problems = req.getParameter("problems");
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        LOGGER.info(problems+" "+teacher_id);
        ShoppingCartDao dao = new ShoppingCartDao(connection);
        dao.deleteFromShoppingCart(teacher_id,problems);
        resp.getWriter().write("删除作业成功！");
        DatabaseHelper.closeConnection(connection);
    }
}
