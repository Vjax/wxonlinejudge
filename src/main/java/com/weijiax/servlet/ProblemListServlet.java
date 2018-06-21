package com.weijiax.servlet;

import com.weijiax.entity.Problem;
import com.weijiax.helper.ConfigHelper;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

/**
 * 最后处理排序
 */
@WebServlet(urlPatterns = "/problemList")
public class ProblemListServlet extends HttpServlet {

    private Connection connection;
    int page = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        connection = DatabaseHelper.getConnection();
        resp.setCharacterEncoding("utf8");
        req.setCharacterEncoding("utf8");
        int selectCount = ConfigHelper.getSelectCount();
        PrintWriter writer = resp.getWriter();
        String sql = null;
        Statement statement =null;
        ResultSet set = null;
        ArrayList<Problem> problems = new ArrayList<>();
        String difficulty_parameter = req.getParameter("difficulty");
        String type_parameter = req.getParameter("subject_type");
        String page_parameter = req.getParameter("page");
        if (page_parameter != null && !page_parameter.equals("")){
            page = CastUtil.castInt(req.getParameter("page"));
        }
        if (difficulty_parameter != null && !difficulty_parameter.equals("") &&  type_parameter != null && !type_parameter.equals("")){
            sql = "select problem_id,title,difficulty,pass_rate,type,description from problem where difficulty = "+CastUtil.castInt(difficulty_parameter)
                    +" and type = "+"'"+type_parameter+"'"+" limit "+(page-1)*selectCount+","+page*selectCount;
        }else if (difficulty_parameter == null || difficulty_parameter.equals("")&& type_parameter != null && !type_parameter.equals("")){
            sql = "select problem_id,title,difficulty,pass_rate,type,description from problem where type = "+"'"+type_parameter+"'"+" limit "+(page-1)*selectCount+","+page*selectCount;
        }else if (type_parameter == null || type_parameter.equals("")&& difficulty_parameter != null && !difficulty_parameter.equals("")){
            sql = "select problem_id,title,difficulty,pass_rate,type,description from problem where difficulty = "+CastUtil.castInt(difficulty_parameter)
                    +" limit "+(page-1)*selectCount+","+page*selectCount;
        }else {
            sql = "select problem_id,title,difficulty,pass_rate,type,description from problem "+" limit "+(page-1)*selectCount+","+page*selectCount;
        }

        try {
            statement = connection.createStatement();
            set = statement.executeQuery(sql);
            while (set.next()){
                int problem_id = set.getInt("problem_id");
                String title = set.getString("title");
                byte difficult = set.getByte("difficulty");
                byte pass_rate = set.getByte("pass_rate");
                String type = set.getString("type");
                String description = set.getString("description");
                Problem problem = new Problem(problem_id,title,type,pass_rate,difficult,description);
                problems.add(problem);
            }
            writer.write(JsonUtil.toJson(problems,"problems"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseHelper.closeConnection(connection);
        }
    }
}
