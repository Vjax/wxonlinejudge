package com.weijiax.dao;

import com.weijiax.ConfigConstant;
import com.weijiax.entity.Problem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExerciseListDao {

    private Connection connection;

    public ExerciseListDao(Connection connection){
        this.connection = connection;
    }

    public List<Problem> getAllExercise(int user_id ,String role){
        List<Problem> unacceptedExercise = getUnacceptedExercise(user_id,role);
        List<Problem> acceptedExercise = getAcceptedExercise(user_id,role);
        acceptedExercise.addAll(unacceptedExercise);
        return acceptedExercise;
    }

    public List<Problem> getUnacceptedExercise(int user_id,String role){
        return getExercise(user_id,role,false);
    }

    public List<Problem> getAcceptedExercise(int user_id,String role){
        return getExercise(user_id,role,true);
    }

    private List<Problem> getExercise(int user_id,String role,boolean accepted){
        String sql = null;
        if (accepted == true){
            sql = "select problem_id from task where user_id = ? and role = ? and result = ? and homework_id=0";
        }else{
            sql = "select problem_id from task where user_id = ? and role = ? and result > ? and homework_id=0";
        }
        ArrayList<Problem> problems = new ArrayList<>();
        ProblemDao problemDao = new ProblemDao(connection);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            int result = ConfigConstant.RESULT.accepted.ordinal();
            statement.setInt(1,user_id);
            statement.setString(2,role);
            statement.setInt(3,result);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                int problem_id = set.getInt("problem_id");
                Problem problem = problemDao.findProblem(problem_id);
                problems.add(problem);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return problems;
    }
}
