package com.example.englishforkids.dao;

import com.example.englishforkids.model.Grammar;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GrammarDAO extends EngSysDAO<Grammar, String>{
    public static final String SELECT_ALL_GRAMMAR_IN_LESSON_QUERY =
            "SELECT g.IdGrammar, g.Title, g.Content, g.Rule, g.Image, g.Example\n" +
                    "FROM GRAMMAR g\n" +
                    "JOIN GRAMMARPART gp ON g.IdGrammar = gp.IdGrammar\n" +
                    "JOIN LESSONPART lp ON gp.IdLessonPart = lp.IdLessonPart\n" +
                    "JOIN LESSON l ON lp.IdLesson = l.IdLesson\n" +
                    "WHERE l.IdLesson = ?;\n";

    public boolean insert(Grammar entity){
        return true;
    }
    public void update(Grammar entity){
    }
    public void delete(String id){
    }
    public Grammar selectById(String id){
        return null;
    }
    public List<Grammar> selectAll(){
        return null;
    }
    public List<Grammar> selectBySql(String sql, Object...args){
        List<Grammar> lstGrammar = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Grammar grammar = new Grammar();
                    grammar.setIdGrammar(resultSet.getString("IdGrammar"));
                    grammar.setTitle(resultSet.getString("Title"));
                    grammar.setContent(resultSet.getString("Content"));
                    grammar.setRule(resultSet.getString("Rule"));
                    grammar.setImage(resultSet.getBytes("Image"));
                    grammar.setExample(resultSet.getString("Example"));
                    lstGrammar.add(grammar);
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
        return lstGrammar;
    }
}
