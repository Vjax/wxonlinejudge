package com.weijiax.servlet;

import com.weijiax.dao.ProblemDao;
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

@WebServlet(urlPatterns = "/getProblem")
public class GetProblemServlet extends HttpServlet{

    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(GetProblemServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        ProblemDao problemDao = new ProblemDao(connection);
        int problem_id = CastUtil.castInt(req.getParameter("problem_id"));
        LOGGER.info(problem_id+" ");
        resp.getWriter().write(JsonUtil.toJson(problemDao.findProblem(problem_id),"problem"));
        DatabaseHelper.closeConnection(connection);
    }
}
