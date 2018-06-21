package com.weijiax.dao;

import com.weijiax.util.CastUtil;

import java.sql.*;

public class SignUpDao {

    private Connection connection;

    public SignUpDao(Connection connection){
        this.connection = connection;
    }

    public void signUpUser(String username,String password){
        String sql = "insert into user(nick_name,pass_word) values(?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int signUpStudent(String real_name,String nick_name,String password,String student_id){
        String sql = "insert into student(student_number,student_name,nick_name,pass_word) values(?,?,?,?)";
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,student_id);
            statement.setString(2,real_name);
            statement.setString(3,nick_name);
            statement.setString(4,password);
            result = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public void signUpTeacher(String real_name,String nick_name,String password,String teacher_id){
        String sql = "insert into teacher(teacher_number,teacher_name,nick_name,pass_word) values(?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,teacher_id);
            statement.setString(2,real_name);
            statement.setString(3,nick_name);
            statement.setString(4,password);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPassword(String username,String role){
        String sql = "select pass_word from "+role+" where nick_name = ?";
        PreparedStatement statement = null;
        String password = null;
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                password = result.getString("pass_word");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return password;
    }
    public int getUserId(String nick_name,String role){
        String sql = "select "+role+"_id from "+role+" where nick_name = "+"'"+nick_name+"'";
        Statement statement = null;
        int id = 0;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            set.next();
            id = set.getInt(role+"_id");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
}
