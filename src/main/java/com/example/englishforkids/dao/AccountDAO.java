package com.example.englishforkids.dao;

import com.example.englishforkids.model.Account;
import com.example.englishforkids.myconnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDAO extends EngSysDAO<Account, String>{
    private static final String SELECT_ACCOUNT_BY_USERNAME_PASSWORD_QUERY =
                    "SELECT A.IdAccount ,A.Username, A.Password, A.Role " +
                    "FROM ACCOUNT A " +
                    "WHERE Username = ? AND Password = ?;";
    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO ACCOUNT(IdAccount, Username, Password, Role) VALUE (?,?,?,?)";
    private static final String UPDATE_ACCOUNT_QUERY = "UPDATE ACCOUNT SET Username = ?, Password = ?, Role = ? WHERE IdAccount = ?";
    private static final String SELECT_ACCOUNT_BY_ID =
            "SELECT A.IdAccount ,A.Username, A.Password, A.Role\n" +
                    "FROM ACCOUNT A \n" +
                    "WHERE IdAccount = ?";

    public boolean insert(Account entity){
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
                    return true;
                } else {
                    System.out.println("Insertion failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    public void update(Account entity){
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_QUERY)) {
                preparedStatement.setString(1, entity.getUsername());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setString(3, entity.getRole().toString());
                preparedStatement.setString(4, entity.getIdAccount());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Update successful.");
                    return;
                } else {
                    System.out.println("No rows affected. Update failed.");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return;
    }

    public void delete(String id){

    }

    public Account selectById(String id){
        Account account = null;
        Connection connection = MySQLConnection.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    account = new Account();
                    account.setIdAccount(resultSet.getString("IdAccount"));
                    account.setUsername(resultSet.getString("Username"));
                    account.setPassword(resultSet.getString("Password"));
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
