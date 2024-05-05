package com.example.englishforkids.dao;

import com.example.englishforkids.model.AnswerQuiz;
import com.example.englishforkids.model.AnswerSubmitQuiz;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AnswerSubmitQuizDAO extends EngSysDAO<AnswerSubmitQuiz, String> {
    private static final String INSERT_ANSWER_SUBMIT_QUIZ_QUERY =
            "INSERT INTO ANSWERSUBMITQUIZ (IdSubmitQuiz,IdAnswerQuiz)" +
                    "VALUES (?,?)";
    public boolean insert(AnswerSubmitQuiz entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ANSWER_SUBMIT_QUIZ_QUERY)) {
                preparedStatement.setString(1, entity.getIdSubmitQuiz());
                preparedStatement.setString(2, entity.getIdAnswerQuiz());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Insertion successful.");
                    return true;
                } else {
                    System.out.println("Insertion failed.");
                    return false;
                }
            }catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void update(AnswerSubmitQuiz entity){

    }
    public void delete(String id){

    }
    public AnswerSubmitQuiz selectById(String id){
        return null;
    }

    public List<AnswerSubmitQuiz> selectAll(){
        return null;
    }
    public List<AnswerSubmitQuiz> selectBySql(String sql, Object...args){
        return null;
    }
}
