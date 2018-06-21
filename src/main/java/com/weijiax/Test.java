package com.weijiax;

import com.weijiax.dao.*;
import com.weijiax.entity.ProblemDetail;
import com.weijiax.entity.Teacher;
import com.weijiax.helper.ConfigHelper;
import com.weijiax.helper.DatabaseHelper;
import com.weijiax.util.CastUtil;
import com.weijiax.util.JsonUtil;
import com.weijiax.util.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

public class Test {

    public static void main(String[] args){
        Connection connection = DatabaseHelper.getConnection();
        String sql = "insert into problem(title,difficulty,type,pass_rate,description,sample_input,sample_output" +
                ",hint,java_code,java_correct_code) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        String title = "斐波那契数列".replaceAll("\t"," ");
        int difficulty = 0;
        String type = "递归".replaceAll("\t"," ");
        int pass_rate = 50;
        String description = "编写一个程序，输入n,求n！（用递归的方式实现）。".replaceAll("\t"," ");
        String sample_input = "5".replaceAll("\t"," ");
        String sample_output = "120".replaceAll("\t"," ");
        String hint = "no hint think for yourself".replaceAll("\t"," ");
        String java_code = "public class Solution{\n" +
                "\t\tpublic long fac(int n){\n" +
                "\t}".replaceAll("\t","    ");
        String java_correct_code = "public class Solution{\n" +
                "\t\tpublic long fac(int n){\n" +
                "\t\t\tif(n<=0) return 0;\n" +
                "\t\t\telse if(n==1) return 1;\n" +
                "\t\t\telse return n*fac(n-1);\n" +
                "\t\t}\n" +
                "\t}".replaceAll("\t","     ");
        try {
            statement  = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setInt(2,difficulty);
            statement.setString(3,type);
            statement.setInt(4,pass_rate);
            statement.setString(5,description);
            statement.setString(6,sample_input);
            statement.setString(7,sample_output);
            statement.setString(8,hint);
            statement.setString(9,java_code);
            statement.setString(10,java_correct_code);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        GradeDao dao = new GradeDao(DatabaseHelper.getConnection());
        System.out.println(JsonUtil.toJson(dao.getStudentsGrade(7)));
//        ProblemDao problemDao = new ProblemDao(DatabaseHelper.getConnection());
//        System.out.println(JsonUtil.toJson(problemDao.getDefaultCode("java",8)));


    }
}
