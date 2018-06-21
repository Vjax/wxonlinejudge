package com.weijiax.servlet;

import com.weijiax.dao.ShoppingCartDao;
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

@WebServlet(urlPatterns = "/showShoppingCart")
public class ShowShoppingCartServlet extends HttpServlet {

    private Logger LOGGER = LoggerFactory.getLogger(AddToShoppingCartServlet.class);
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        LOGGER.info(teacher_id+"get shopping cart");
        ShoppingCartDao dao = new ShoppingCartDao(connection);
        resp.getWriter().write(JsonUtil.toJson(dao.getShoppingCart(teacher_id),"problems"));
        DatabaseHelper.closeConnection(connection);
    }
}
