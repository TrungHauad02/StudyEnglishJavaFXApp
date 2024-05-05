package com.example.englishforkids.dao;

import com.example.englishforkids.model.SubmitQuiz;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubmitQuizDAO extends EngSysDAO<SubmitQuiz, String>{
    public static final String INSERT_SUBMIT_QUIZ_QUERY=
            "INSERT INTO SUBMITQUIZ (Score, StartTime, EndTime, IdQuiz, IdUser)\n" +
                    "VALUES (?,?,?,?,?);\n";
    public boolean insert(SubmitQuiz entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBMIT_QUIZ_QUERY)) {
                preparedStatement.setInt(2, entity.getScore());
                preparedStatement.setDate(3, new Date(entity.getStartTime().getTime()));
                preparedStatement.setDate(4, new Date(entity.getEndTime().getTime()));
                preparedStatement.setString(5, entity.getIdQuiz());
                preparedStatement.setString(6, entity.getIdUser());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Insertion successful.");
                    return true;
                } else {
                    System.out.println("Insertion failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void update(SubmitQuiz entity){

    }
    public void delete(String id){

    }
    public SubmitQuiz selectById(String id){
        return null;
    }

    public List<SubmitQuiz> selectAll(){
        return null;
    }
    protected List<SubmitQuiz> selectBySql(String sql, Object...args) {
        return null;
    }
}
