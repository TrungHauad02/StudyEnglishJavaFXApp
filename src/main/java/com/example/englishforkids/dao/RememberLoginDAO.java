package com.example.englishforkids.dao;

import com.example.englishforkids.model.RememberLogin;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RememberLoginDAO extends EngSysDAO<RememberLogin, String>{
    private static final String SELECT_ACCOUNT_BY_IP_QUERY = "SELECT * FROM REMEMBERACCOUNT WHERE MacAddress = ?";
    private static final String INSERT_REMEMBER_LOGIN_QUERY = "INSERT INTO REMEMBERACCOUNT(IdAccount, MacAddress) VALUE (?,?)";
    private static final String DELETE_REMEMBER_LOGIN_QUERY = "DELETE FROM REMEMBERACCOUNT\n" +
            "WHERE IdAccount = ?\n" +
            "  AND MacAddress = ?;\n";
    public boolean insert(RememberLogin entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REMEMBER_LOGIN_QUERY)) {
                preparedStatement.setString(1, entity.getIdAccount());
                preparedStatement.setString(2, entity.getMacAddress());
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

    public void update(RememberLogin entity){

    }

    public void delete(String id){

    }

    public void delete(RememberLogin rememberLogin){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REMEMBER_LOGIN_QUERY)) {
                preparedStatement.setString(1, rememberLogin.getIdAccount());
                preparedStatement.setString(2, rememberLogin.getMacAddress());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Delete successful.");
                } else {
                    System.out.println("Delete failed.");
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
    }

    public RememberLogin selectById(String macAddress){
        RememberLogin rememberLogin = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_IP_QUERY)) {
                preparedStatement.setString(1, macAddress);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    rememberLogin = new RememberLogin();
                    rememberLogin.setIdAccount(resultSet.getString("IdAccount"));
                    rememberLogin.setMacAddress(resultSet.getString("MacAddress"));
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
        return rememberLogin;
    }

    public List<RememberLogin> selectAll(){
        return null;
    }

    protected List<RememberLogin> selectBySql(String sql, Object...args){
        return null;
    }

}
