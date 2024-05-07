package com.example.englishforkids.dao;

import com.example.englishforkids.model.Lesson;
import com.example.englishforkids.model.Vocabulary;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VocabularyDAO extends EngSysDAO<Vocabulary, String>{
    final public static String SELECT_ALL_VOCABULARY_IN_LESSON_QUERY =
            "SELECT V.IdVocabulary, V.Word, V.Mean, V.Image, V.Phonetic\n" +
            "FROM VOCABULARY V\n" +
            "INNER JOIN VOCABULARYPART VP ON V.IdVocabulary = VP.IdVocabulary\n" +
            "INNER JOIN LESSONPART LP ON VP.IdLessonPart = LP.IdLessonPart\n" +
            "INNER JOIN LESSON L ON LP.IdLesson = L.IdLesson\n" +
            "WHERE L.IdLesson = ?\n";
    final public static String SELECT_ALL_VOCABULARY_QUERY =
            "SELECT V.IdVocabulary, V.Word, V.Mean, V.Image, V.Phonetic\n" +
                    "FROM VOCABULARY V\n";
    final public static String SELECT_ALL_ANTONYMS_VOCABULARY_QUERY =
            "SELECT V.IdVocabulary, V.Word, V.Mean\n" +
                    "FROM VOCABULARY V\n" +
                    "INNER JOIN ANTONYMS A ON V.IdVocabulary = A.IdAntonyms\n" +
                    "WHERE A.IdVocabulary = ?\n";
    final public static String SELECT_ALL_SYNONYMS_VOCABULARY_QUERY =
            "SELECT V.IdVocabulary, V.Word, V.Mean\n" +
                    "FROM VOCABULARY V\n" +
                    "INNER JOIN SYNONYMS S ON V.IdVocabulary = S.IdSynonyms\n" +
                    "WHERE S.IdVocabulary = ?\n";
    public boolean insert(Vocabulary entity){
        return false;
    }

    public void update(Vocabulary entity){

    }

    public void delete(String id){

    }

    public Vocabulary selectById(String id){
        return null;
    }

    public List<Vocabulary> selectAll(){
        LinkedList<Vocabulary> lstVocabulary = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VOCABULARY_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Vocabulary vocabulary = new Vocabulary();
                    vocabulary.setIdVocabulary(resultSet.getString("IdVocabulary"));
                    vocabulary.setImage(resultSet.getBytes("image"));
                    vocabulary.setMean(resultSet.getString("mean"));
                    vocabulary.setWord(resultSet.getString("word"));
                    vocabulary.setPhonetic(resultSet.getString("phonetic"));
                    lstVocabulary.add(vocabulary);
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
        return lstVocabulary;
    }

    public List<Vocabulary> selectBySql(String sql, Object...args){
        LinkedList<Vocabulary> lstVocabulary = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Vocabulary vocabulary = new Vocabulary();
                    vocabulary.setIdVocabulary(resultSet.getString("IdVocabulary"));
                    vocabulary.setImage(resultSet.getBytes("image"));
                    vocabulary.setMean(resultSet.getString("mean"));
                    vocabulary.setWord(resultSet.getString("word"));
                    vocabulary.setPhonetic(resultSet.getString("phonetic"));
                    lstVocabulary.add(vocabulary);
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
        return lstVocabulary;
    }
}
