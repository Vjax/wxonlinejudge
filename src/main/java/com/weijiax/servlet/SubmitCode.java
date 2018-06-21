package com.weijiax.servlet;

import com.alibaba.fastjson.JSONObject;
import com.weijiax.ConfigConstant;
import com.weijiax.dao.SubmitDao;
import com.weijiax.entity.Message;
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
import java.sql.*;

/**
 * 提交代码的servlet
 */
@WebServlet(urlPatterns = "/submitCode")
public class SubmitCode extends HttpServlet {

    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(SubmitCode.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        SubmitDao submitDao = new SubmitDao(connection);
        String language = req.getParameter("language");
        String role = req.getParameter("role");
        String code = req.getParameter("code");
        int user_id = CastUtil.castInt(req.getParameter("user_id"));
        int problem_id = CastUtil.castInt(req.getParameter("problem_id"));
        int homework_id = CastUtil.castInt(req.getParameter("homework_id"));
        boolean is_test = CastUtil.castBoolean(req.getParameter("is_test"));
        LOGGER.info(language+" "+role+" "+code+" "+user_id+" "+problem_id+" "+homework_id+" "+is_test);
        submitDao.addToTask(code,language,role,homework_id,problem_id,user_id,is_test);
        String sql = "select status,result,extra,pass_rate from task where language = ? and role = ? and user_id = ? and problem_id = ? and homework_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,language);
            statement.setString(2,role);
            statement.setInt(3,user_id);
            statement.setInt(4,problem_id);
            statement.setInt(5,homework_id);
            while (true){
                resultSet = statement.executeQuery();
                resultSet.next();
                int status = resultSet.getInt("status");
                if (status == ConfigConstant.STATUS.finished.ordinal()){
                    Message message = new Message();
                    message.setResult(resultSet.getInt("result"));
                    message.setExtra(resultSet.getString("extra").replaceAll("\t"," "));
                    message.setPass_rate(resultSet.getInt("pass_rate"));
                    LOGGER.info(JsonUtil.toJson(message,"message"));
                    resp.getWriter().write(JsonUtil.toJson(message,"message"));
                    break;
                }else {
                    try {
                        Thread.sleep(30);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseHelper.closeConnection(connection);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }



}
