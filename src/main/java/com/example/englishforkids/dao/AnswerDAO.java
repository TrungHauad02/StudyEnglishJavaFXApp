package com.example.englishforkids.dao;

import com.example.englishforkids.model.AnswerQuiz;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AnswerDAO extends EngSysDAO<AnswerQuiz, String> {
    public static final String SELECT_ANSWER_FROM_QUESTION_QUERY=
            "SELECT IdAnswerQuiz, Content, IsCorrect\n" +
                    "FROM ANSWERQUIZ\n" +
                    "WHERE IdQuestionQuiz = ?;\n";
    public boolean insert(AnswerQuiz entity){
        return true;
    }

    public void update(AnswerQuiz entity){

    }
    public void delete(String id){

    }
    public AnswerQuiz selectById(String id){
        return null;
    }

    public List<AnswerQuiz> selectAll(){
        return null;
    }
    public List<AnswerQuiz> selectBySql(String sql, Object...args){
        List<AnswerQuiz> lstAnswerQuiz = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    AnswerQuiz answerQuiz = new AnswerQuiz();
                    answerQuiz.setContent(resultSet.getString("Content"));
                    answerQuiz.setCorrect(resultSet.getBoolean("IsCorrect"));
                    answerQuiz.setIdAnswerQuiz(resultSet.getString("IdAnswerQuiz"));
                    lstAnswerQuiz.add(answerQuiz);
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
        return lstAnswerQuiz;
    }
}
