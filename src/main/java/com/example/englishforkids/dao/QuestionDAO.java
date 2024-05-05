package com.example.englishforkids.dao;

import com.example.englishforkids.model.QuestionQuiz;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class QuestionDAO extends EngSysDAO<QuestionQuiz, String> {
    public static final String SELECT_QUESTIONS_FROM_QUIZ_QUERY=
            "SELECT IdQuestionQuiz, Content, Serial, Image\n" +
                    "FROM QUESTIONQUIZ\n" +
                    "WHERE IdQuiz = ?;\n";
    public boolean insert(QuestionQuiz entity){
        return true;
    }

    public void update(QuestionQuiz entity){

    }
    public void delete(String id){

    }
    public QuestionQuiz selectById(String id){
        return null;
    }

    public List<QuestionQuiz> selectAll(){
        return null;
    }
    public List<QuestionQuiz> selectBySql(String sql, Object...args){
        List<QuestionQuiz> lstQuestionQuiz = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    QuestionQuiz questionQuiz = new QuestionQuiz();
                    questionQuiz.setIdQuestionQuiz(resultSet.getString("IdQuestionQuiz"));
                    questionQuiz.setContent(resultSet.getString("Content"));
                    questionQuiz.setImage(resultSet.getBytes("Image"));
                    questionQuiz.setSerial(resultSet.getInt("Serial"));
                    lstQuestionQuiz.add(questionQuiz);
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
        return lstQuestionQuiz;
    }
}
