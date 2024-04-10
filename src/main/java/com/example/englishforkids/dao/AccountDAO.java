package com.example.englishforkids.dao;

import com.example.englishforkids.model.Account;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDAO extends EngSysDAO<Account, String>{
    private static final String SELECT_ACCOUNT_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM ACCOUNT WHERE Username = ? AND Password = ?";
    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO ACCOUNT(IdAccount, Username, Password, Role) VALUE (?,?,?,?)";

    public void insert(Account entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_QUERY)) {
                preparedStatement.setString(1, entity.getIdAccount());
                preparedStatement.setString(2, entity.getUsername());
                preparedStatement.setString(3, entity.getPassword());
                preparedStatement.setString(4, entity.getRole().toString());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Insertion successful.");
                } else {
                    System.out.println("Insertion failed.");
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

    public void update(Account entity){

    }

    public void delete(String id){

    }

    public Account selectById(String id){
        return null;
    }

    public List<Account> selectAll(){
        return null;
    }

    protected List<Account> selectBySql(String sql, Object...args){
        return null;
    }

    public Account login(String username, String password){
        Account account = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_USERNAME_PASSWORD_QUERY)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    account = new Account();
                    account.setIdAccount(resultSet.getString("IdAccount"));
                    account.setUsername(username);
                    account.setPassword(password);
                    account.setRole(resultSet.getString("Role"));
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
        return account;
    }
}
