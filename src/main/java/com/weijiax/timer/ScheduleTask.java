package com.weijiax.timer;

import com.weijiax.helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;

public class ScheduleTask extends TimerTask {

    private int homework_id;

    public ScheduleTask(int homework_id){
        this.homework_id = homework_id;

    }

    @Override
    public void run() {
        System.out.println("timer run");
        Connection connection = DatabaseHelper.getConnection();
        String sql = "update homework set finished=true where homework_id = "+homework_id;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseHelper.closeConnection(connection);
        }
    }
}
