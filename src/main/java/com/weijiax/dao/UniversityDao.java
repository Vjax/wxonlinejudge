package com.weijiax.dao;

import com.weijiax.entity.University;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UniversityDao {

    public Connection connection;

    public UniversityDao(Connection connection){
        this.connection = connection;
    }

    public List<University> getUniversityForProvince(int province_id){
        String sql = "select university_name,university_id from university where province_id = "+province_id;
        Statement statement = null;
        List<University> universitieList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                String university_name = set.getString("university_name");
                int university_id = set.getInt("university_id");
                universitieList.add(new University(university_name,university_id));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return universitieList;
    }
}
