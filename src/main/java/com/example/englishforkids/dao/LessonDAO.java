package com.example.englishforkids.dao;

import com.example.englishforkids.model.Account;
import com.example.englishforkids.model.Lesson;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LessonDAO extends  EngSysDAO<Lesson, String>{
    final private static String SELECT_ALL_LESSON_QUERY = "SELECT IdLesson, Name, Serial, Description, Status\n" +
            "FROM LESSON\n" +
            "ORDER BY Serial ASC;";
    final private static String SELECT_LESSON_BY_ID_QUERY =
            "SELECT IdLesson, Name, Serial, Description, Status\n " +
            "FROM LESSON\n" +
            "WHERE IdLesson = ?;";
    public boolean insert(Lesson entity){
        return false;
    }

    public void update(Lesson entity){

    }

    public void delete(String id){

    }

    public Lesson selectById(String id){
        Lesson lesson = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LESSON_BY_ID_QUERY)) {
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    lesson = new Lesson();
                    lesson.setIdLesson(resultSet.getString("IdLesson"));
                    lesson.setName(resultSet.getString("Name"));
                    lesson.setDescription(resultSet.getString("Description"));
                    lesson.setSerial(resultSet.getInt("Serial"));
                    lesson.setStatus(Lesson.LessonStatus.valueOf(resultSet.getString("Status").toUpperCase()));
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
        return lesson;
    }

    public List<Lesson> selectAll(){
        List<Lesson> lstLesson = new LinkedList<Lesson>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LESSON_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setIdLesson(resultSet.getString("IdLesson"));
                    lesson.setName(resultSet.getString("Name"));
                    lesson.setDescription(resultSet.getString("Description"));
                    lesson.setSerial(resultSet.getInt("Serial"));
                    lesson.setStatus(Lesson.LessonStatus.valueOf(resultSet.getString("Status").toUpperCase()));

                    lstLesson.add(lesson);
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
        return lstLesson;
    }

    protected List<Lesson> selectBySql(String sql, Object...args){
        return null;
    }
}
