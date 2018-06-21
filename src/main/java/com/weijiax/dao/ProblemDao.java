package com.weijiax.dao;

import com.weijiax.entity.Problem;
import com.weijiax.entity.ProblemDetail;
import com.weijiax.helper.DatabaseHelper;

import java.sql.*;

public class ProblemDao {

    private Connection connection;

    public ProblemDao(Connection connection){
        this.connection = connection;
    }

    public Problem findProblem(int problem_id){
        String sql = "select title,type,difficulty,pass_rate,description from problem where problem_id = "+problem_id;
        Statement statement = null;
        Problem problem = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                String title = set.getString("title");
                String type = set.getString("type");
                byte difficulty = set.getByte("difficulty");
                byte pass_rate = set.getByte("pass_rate");
                String description = set.getString("description");
                problem = new Problem(problem_id,title,type,pass_rate,difficulty,description);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return problem;
    }

    public String getProblemTitle(int problem_id){
        String sql = "select title from problem where problem_id = "+problem_id;
        Statement statement = null;
        String title = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            set.next();
            title = set.getString("title");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return title;
    }

    public ProblemDetail getProblemDetail(int problem_id,String role,int user_id,int homework_id,String language){
        String sql = "select description,sample_input,sample_output,hint,title from problem where problem_id = "+problem_id;
        Statement statement = null;
        ProblemDetail detail = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            set.next();
            String title = set.getString("title").replaceAll("\t","  ");
            String description = set.getString("description").replaceAll("\t","  ");
            String sample_output = set.getString("sample_output").replaceAll("\t","  ");
            String sample_input = set.getString("sample_input").replaceAll("\t","  ");
            String hint = set.getString("hint").replaceAll("\t","  ");
            String code = getCode(role,language,user_id,problem_id,homework_id).replaceAll("\t","  ");
            detail = new ProblemDetail(title,code,sample_input,sample_output,description,hint);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return detail;
    }

    public String getDefaultCode(String language,int problem_id){
        String sql = "select "+language+"_code from problem where problem_id = "+problem_id;
        Statement statement = null;
        String code = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            set.next();
            code = set.getString(language+"_code");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return code;
    }
    public String getCode(String role,String language,int user_id,int problem_id,int homework_id){
       String code = null;
       if (role.equals("teacher")){
           code = getTeacherCode(problem_id,language);
       }else {
           code = getUserCode(role,language,user_id,problem_id,homework_id);
           if (code == null){
               code = getDefaultCode(language,problem_id);
           }
       }
       return code;
    }

    public String getUserCode(String role,String language,int user_id,int problem_id,int homework_id){
        String sql = "select code from task where role = ? and language = ? and user_id = ? and problem_id = ? and homework_id = ?";
        PreparedStatement statement = null;
        String code = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,role);
            statement.setString(2,language);
            statement.setInt(3,user_id);
            statement.setInt(4,problem_id);
            statement.setInt(5,homework_id);
            ResultSet set = statement.executeQuery();
            set.next();
            if (set.getRow() != 0){
                code = set.getString("code");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return code;
    }

    public String getTeacherCode(int problem_id,String language){
        String sql = "select "+language+"_correct_code from problem where problem_id = "+problem_id;
        Statement statement = null;
        String code = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            set.next();
            code = set.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return code;
    }
}
