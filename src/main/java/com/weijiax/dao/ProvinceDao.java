package com.weijiax.dao;

import com.weijiax.entity.Province;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDao {

    public Connection connection;

    public ProvinceDao(Connection connection){
        this.connection = connection;
    }

    public List<Province> getProvinceList(){
        String sql = "select province_id,province_name from province";
        List<Province> provinceList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                int province_id = set.getInt("province_id");
                String province_name =  set.getString("province_name");
                provinceList.add(new Province(province_id,province_name));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return provinceList;
    }
}
