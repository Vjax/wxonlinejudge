package com.weijiax.dao;

import com.weijiax.ConfigConstant;
import com.weijiax.entity.DataAnalysis;

import java.sql.*;

public class DataAnalysisDao {

    private Connection connection;

    public DataAnalysisDao(Connection connection){
        this.connection = connection;
    }
    public int getTotalProblemCount(){
        String sql = "select count(*) from problem";
        Statement statement = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                count = set.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public int getFinishedProblemCount(String role,int user_id){
        String sql = "select count(*) from task where role = ? and user_id = ? and homework_id = 0 and status = ?";
        PreparedStatement statement = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,role);
            statement.setInt(2,user_id);
            statement.setInt(3, ConfigConstant.RESULT.accepted.ordinal());
            ResultSet set = statement.executeQuery();
            while (set.next()){
                count = set.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public int getSubmitProblemCount(String role,int user_id){
        String sql = "select count(*) from task where role = ? and user_id = ? and homework_id = 0";
        PreparedStatement statement = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,role);
            statement.setInt(2,user_id);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                count = set.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }
}
