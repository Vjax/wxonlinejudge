package com.weijiax.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubmitDao {

    private Connection connection;

    public SubmitDao(Connection connection){
        this.connection  = connection;
    }

    public void addToTask(String code,String language,String role,int homework_id,int problem_id,int user_id,boolean is_test){
        PreparedStatement statement = null;
        String sql = "insert into task(code,language,problem_id,user_id,role,homework_id,is_test) values(?,?,?,?,?,?,?) " +
                "on duplicate key update code = ?,status = 0,is_test = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,code);
            statement.setString(2,language);
            statement.setInt(3,problem_id);
            statement.setInt(4,user_id);
            statement.setString(5,role);
            statement.setInt(6,homework_id);
            statement.setBoolean(7,is_test);
            statement.setString(8,code);
            statement.setBoolean(9,is_test);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
