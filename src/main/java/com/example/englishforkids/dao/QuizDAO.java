package com.example.englishforkids.dao;

import com.example.englishforkids.model.Quiz;
import com.example.englishforkids.model.Vocabulary;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class QuizDAO extends EngSysDAO<Quiz,String>{
    public static final String SELECT_QUIZ_FROM_LESSON_QUERY=
            "SELECT Q.IdQuiz, Q.Title, Q.Status\n" +
            "FROM QUIZ Q\n" +
            "JOIN QUIZPART QP ON Q.IdQuiz = QP.IdQuiz\n" +
            "JOIN LESSONPART LP ON QP.IdLessonPart = LP.IdLessonPart\n" +
            "WHERE LP.IdLesson = ?;\n";
    public boolean insert(Quiz entity){
        return true;
    }

    public void update(Quiz entity){

    }
    public void delete(String id){

    }
    public Quiz selectById(String id){
        return null;
    }

    public List<Quiz> selectAll(){
        return null;
    }
    public List<Quiz> selectBySql(String sql, Object...args){
        List<Quiz> lstQuiz = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setIdQuiz(resultSet.getString("IdQuiz"));
                    quiz.setTitle(resultSet.getString("Title"));
                    quiz.setStatus(Quiz.QuizStatus.valueOf(resultSet.getString("Status").toUpperCase()));
                    lstQuiz.add(quiz);
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
        return lstQuiz;
    }
}
