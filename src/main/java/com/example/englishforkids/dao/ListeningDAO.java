package com.example.englishforkids.dao;

import com.example.englishforkids.model.Listening;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ListeningDAO extends EngSysDAO<Listening, String>{
    public static final String SELECT_LISTENING_FROM_LESSON =
            "SELECT IdListening, CreateDay, Title, Description, Video, Script\n" +
                    "FROM LISTENING L\n" +
                    "JOIN LISTENINGPART LP ON L.IdListening = LP.IdListening\n" +
                    "JOIN LESSONPART LP2 ON LP.IdLessonPart = LP2.IdLessonPart\n" +
                    "WHERE LP2.IdLesson = ?;\n";
    public boolean insert(Listening entity){
        return true;
    }

    public void update(Listening entity){

    }
    public void delete(String id){

    }
    public Listening selectById(String id){
        return null;
    }

    public List<Listening> selectAll(){
        return null;
    }
    public List<Listening> selectBySql(String sql, Object...args) {
        List<Listening> lstListening = new LinkedList<>();
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Listening listening = new Listening();
                    listening.setIdListening(resultSet.getString("IdListening"));
                    listening.setTitle(resultSet.getString("Tile"));
                    listening.setDescription(resultSet.getString("Description"));
                    listening.setScript(resultSet.getString("Script"));
                    listening.setVideo(resultSet.getBytes("Video"));
                    lstListening.add(listening);
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
        return lstListening;
    }
}
