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

@WebServlet(urlPatterns = "/addToShoppingCart")
public class AddToShoppingCartServlet extends HttpServlet {

    private Logger LOGGER = LoggerFactory.getLogger(AddToShoppingCartServlet.class);
    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        int problem_id = CastUtil.castInt(req.getParameter("problem_id"));
        int teacher_id = CastUtil.castInt(req.getParameter("teacher_id"));
        LOGGER.info(problem_id+" "+teacher_id);
        ShoppingCartDao dao = new ShoppingCartDao(connection);
        boolean success = dao.addOneToShoppingCart(teacher_id,problem_id);
        if (success == true){
            resp.getWriter().write(JsonUtil.toJson(true,"success"));
        }else{
            resp.getWriter().write(JsonUtil.toJson(false,"success"));
        }
        DatabaseHelper.closeConnection(connection);
    }
}
