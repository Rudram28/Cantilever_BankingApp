package service;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {
    public boolean createAccount(int userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setDouble(2, 0.0);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deposit(int accountId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, amount);
            statement.setInt(2, accountId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(int accountId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check balance first
            String checkQuery = "SELECT balance FROM accounts WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, accountId);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                if (balance >= amount) {
                    String query = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setDouble(1, amount);
                    statement.setInt(2, accountId);
                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public double checkBalance(int accountId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT balance FROM accounts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
