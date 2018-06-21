package com.weijiax.servlet;

import com.weijiax.dao.ExerciseListDao;
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
import java.sql.Connection;

@WebServlet(urlPatterns = "/exerciseList")
public class ExerciseListServlet extends HttpServlet {

    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        PrintWriter writer = resp.getWriter();
        ExerciseListDao exerciseListDao = new ExerciseListDao(connection);
        int user_id = CastUtil.castInt(req.getParameter("user_id"));
        String role = req.getParameter("role");
        String accepted = req.getParameter("accepted");
        if (accepted == null || accepted.equals("")){
            writer.write(JsonUtil.toJson(exerciseListDao.getAllExercise(user_id,role),"problems"));
        }else if (CastUtil.castBoolean(accepted) == true){
            writer.write(JsonUtil.toJson(exerciseListDao.getAcceptedExercise(user_id,role),"problems"));
        }else {
            writer.write(JsonUtil.toJson(exerciseListDao.getUnacceptedExercise(user_id,role),"problems"));
        }
        DatabaseHelper.closeConnection(connection);
    }
}
