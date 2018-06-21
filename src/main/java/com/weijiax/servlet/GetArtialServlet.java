package com.weijiax.servlet;

import com.weijiax.dao.ArticalDao;
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

@WebServlet(urlPatterns = "/getArtical")
public class GetArtialServlet extends HttpServlet{

    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(GetArtialServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        ArticalDao articalDao = new ArticalDao(connection);
        int articalId = CastUtil.castInt(req.getParameter("artical_id"));
        writer.write(articalDao.getArtical(articalId));
        DatabaseHelper.closeConnection(connection);
    }
}
