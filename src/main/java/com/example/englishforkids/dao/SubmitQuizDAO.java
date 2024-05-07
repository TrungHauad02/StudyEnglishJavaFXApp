package com.example.englishforkids.dao;

import com.example.englishforkids.model.SubmitQuiz;
import com.example.englishforkids.model.Vocabulary;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubmitQuizDAO extends EngSysDAO<SubmitQuiz, String>{
    public static final String INSERT_SUBMIT_QUIZ_QUERY=
            "INSERT INTO SUBMITQUIZ (Score, StartTime, EndTime, IdQuiz, IdUser)\n" +
                    "VALUES (?,?,?,?,?);\n";
    public static final String SELECT_LASTED_ID_QUERY =
            "SELECT IdSubmitQuiz\n" +
                    "FROM SUBMITQUIZ\n" +
                    "ORDER BY IdSubmitQuiz DESC\n" +
                    "LIMIT 1;";
    public boolean insert(SubmitQuiz entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBMIT_QUIZ_QUERY)) {
                preparedStatement.setInt(1, entity.getScore());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getStartTime()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getEndTime()));
                preparedStatement.setString(4, entity.getIdQuiz());
                preparedStatement.setString(5, entity.getIdUser());

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

    public String getLastestID(){
        String result = "";
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LASTED_ID_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    result = resultSet.getString("IdSubmitQuiz");
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
        return result;
    }
}
