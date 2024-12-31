package com.yourpackage;

import com.yourpackage.dao.UserDAO;
import com.yourpackage.model.User;
import com.yourpackage.exception.DatabaseException;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        try {
            // Add a new user
            User newUser = new User("John Doe", "john.doe@example.com");
            userDAO.addUser(newUser);

            // Get all users
            List<User> users = userDAO.getAllUsers();
            System.out.println("Users: " + users);

            // Update a user
            newUser.setName("John Updated");
            userDAO.updateUser(newUser);

            // Delete a user (example: delete by ID)
            userDAO.deleteUser(newUser.getId());
            
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
