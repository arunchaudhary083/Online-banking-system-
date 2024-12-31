package com.yourpackage.dao;

import com.yourpackage.model.User;
import com.yourpackage.util.DBUtil;
import com.yourpackage.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    // Add a new user
    public void addUser(User user) throws DatabaseException {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
            logger.info("User added successfully: {}", user);
        } catch (SQLException e) {
            logger.error("Error adding user: {}", user, e);
            throw new DatabaseException("Error adding user", e);
        }
    }

    // Get all users
    public List<User> getAllUsers() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = DBUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            logger.info("Users fetched successfully.");
        } catch (SQLException e) {
            logger.error("Error fetching users", e);
            throw new DatabaseException("Error fetching users", e);
        }
        return users;
    }

    // Update a user
    public void updateUser(User user) throws DatabaseException {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
            logger.info("User updated successfully: {}", user);
        } catch (SQLException e) {
            logger.error("Error updating user: {}", user, e);
            throw new DatabaseException("Error updating user", e);
        }
    }

    // Delete a user
    public void deleteUser(int userId) throws DatabaseException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            logger.info("User deleted successfully: ID {}", userId);
        } catch (SQLException e) {
            logger.error("Error deleting user with ID {}", userId, e);
            throw new DatabaseException("Error deleting user", e);
        }
    }
}
