package com.example.englishforkids.dao;

import com.example.englishforkids.model.User;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends EngSysDAO<User, String>{
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM USER WHERE IdUser = ?";
    private static final String SELECT_USER_BY_ID_ACCOUNT_QUERY = "SELECT * FROM USER WHERE IdAccount = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM USER WHERE Email = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO USER(Fullname, Birthday, Status, Avatar, School, Class, Address, EmailParent) " +
                                                "VALUE (?,?,?,?,?,?,?,?)";
    public boolean insert(User entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {
                preparedStatement.setString(1, entity.getFullname());
                preparedStatement.setDate(2, new java.sql.Date(entity.getBirthday().getTime()));
                preparedStatement.setBoolean(3, entity.isStatus());
                preparedStatement.setString(4, entity.getAvatar());
                preparedStatement.setString(5, entity.getSchool());
                preparedStatement.setString(6, entity.getGrade());
                preparedStatement.setString(7, entity.getAddress());
                preparedStatement.setString(8, entity.getEmailParent());
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
                return false;
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

    public void update(User entity){

    }

    public void delete(String id){

    }

    public User selectById(String id){
        User user = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY)) {
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user = new User();
                    user.setIdUser(resultSet.getString("IdUser"));
                    user.setFullname(resultSet.getString("Fullname"));
                    user.setBirthday(resultSet.getDate("Birthday"));
                    user.setStatus(resultSet.getBoolean("Status"));
                    user.setAvatar(resultSet.getString("Avatar"));
                    user.setSchool(resultSet.getString("School"));
                    user.setGrade(resultSet.getString("Class"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setEmailParent(resultSet.getString("EmailParent"));
                    user.setScore(resultSet.getInt("Score"));
                    user.setIdAccount(resultSet.getString("IdAccount"));
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
        return user;
    }

    public User selectByIdAccount(String idAccount){
        User user = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_ACCOUNT_QUERY)) {
                preparedStatement.setString(1, idAccount);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user = new User();
                    user.setIdUser(resultSet.getString("IdUser"));
                    user.setFullname(resultSet.getString("Fullname"));
                    user.setBirthday(resultSet.getDate("Birthday"));
                    user.setStatus(resultSet.getBoolean("Status"));
                    user.setAvatar(resultSet.getString("Avatar"));
                    user.setSchool(resultSet.getString("School"));
                    user.setGrade(resultSet.getString("Class"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setEmailParent(resultSet.getString("EmailParent"));
                    user.setScore(resultSet.getInt("Score"));
                    user.setIdAccount(resultSet.getString("IdAccount"));
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
        return user;
    }

    public User selectByEmail(String email){
        User user = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user = new User();
                    user.setIdUser(resultSet.getString("IdUser"));
                    user.setFullname(resultSet.getString("Fullname"));
                    user.setBirthday(resultSet.getDate("Birthday"));
                    user.setStatus(resultSet.getBoolean("Status"));
                    user.setAvatar(resultSet.getString("Avatar"));
                    user.setSchool(resultSet.getString("School"));
                    user.setGrade(resultSet.getString("Class"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setEmailParent(resultSet.getString("EmailParent"));
                    user.setScore(resultSet.getInt("Score"));
                    user.setIdAccount(resultSet.getString("IdAccount"));
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
        return user;
    }

    public List<User> selectAll(){
        return null;
    }

    protected List<User> selectBySql(String sql, Object...args){
        return null;
    }

}
