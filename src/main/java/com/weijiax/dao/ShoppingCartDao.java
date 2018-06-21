package com.weijiax.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weijiax.entity.Problem;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDao {

    private Connection connection;

    public ShoppingCartDao(Connection connection){
        this.connection = connection;
    }

    private boolean deleteOneFromShoppingCart(int teacher_id,int problem_id){
        String sql = "delete from shopping_cart where teacher_id = ? and problem_id = ?";
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,teacher_id);
            statement.setInt(2,problem_id);
            result = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (result == 1){
            return true;
        }else {
            return false;
        }
    }

    public boolean addOneToShoppingCart(int teacher_id,int problem_id){
        String sql = "insert into shopping_cart values(?,?)";
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,teacher_id);
            statement.setInt(2,problem_id);
            result = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (result == 1){
            return true;
        }else {
            return false;
        }
    }

    public void addToShoppingCart(int teacher_id,String problems){
        JSONArray array = JsonUtil.getJsonArray(problems);
        for (int i = 0;i < array.size();i++){
            int problem_id = CastUtil.castInt(array.get(i));
            addOneToShoppingCart(teacher_id,problem_id);
        }
    }

    public void deleteFromShoppingCart(int teacher_id,String problems){
        JSONArray array = JsonUtil.getJsonArray(problems);
        for (int i = 0;i < array.size();i++){
            int problem_id = CastUtil.castInt(array.getJSONObject(i).getInteger("problem_id"));
            deleteOneFromShoppingCart(teacher_id,problem_id);
        }
    }

    public List<Problem> getShoppingCart(int teacher_id){
        ProblemDao dao = new ProblemDao(connection);
        String sql = "select problem_id from shopping_cart where teacher_id = "+teacher_id;
        Statement statement = null;
        List<Problem> problems = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int problem_id = set.getInt("problem_id");
                problems.add(dao.findProblem(problem_id));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return problems;
    }
}
