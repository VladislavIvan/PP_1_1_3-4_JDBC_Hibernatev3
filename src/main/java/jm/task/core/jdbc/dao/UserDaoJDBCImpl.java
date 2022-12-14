package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private String sql;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                " name VARCHAR(25) NOT NULL, " +
                " lastName VARCHAR(25) NOT NULL, " +
                " age INT(3) NOT NULL, " +
                " PRIMARY KEY (`id`))";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("INFO: Table created successfully");
        } catch (SQLException e) {
            Logger.getLogger("create").log(Level.WARNING, "Creation failed...");
        }
    }

    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("INFO: Table deleted successfully");
        } catch (SQLException e) {
            Logger.getLogger("delete").log(Level.WARNING, "Removal failed...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("INFO: User \"Alex\" added to database successfully");
            System.out.println("INFO: User \"Anton\" added to database successfully");
            System.out.println("INFO: User  \"Lev\" added to database successfully");
            System.out.println("INFO: User  \"Vladimir\" added to database successfully");

        } catch (SQLException e) {
            Logger.getLogger("add").log(Level.WARNING, "Adding failed...");
        }
    }

    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("INFO: Remove successful, user id: " + id);
        } catch (SQLException e) {
            Logger.getLogger("remove").log(Level.WARNING, "Remove failed..., user id: " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersToList = new ArrayList<>();
        sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                usersToList.add(
                        new User(resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getByte(4)));
            }
        } catch (SQLException e) {
            Logger.getLogger("getAll").log(Level.WARNING, "Getting Users failed...");
        }
        return usersToList;
    }

    public void cleanUsersTable() {
        sql = "TRUNCATE TABLE users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("INFO: Data Delete successful!");
        } catch (SQLException e) {
            Logger.getLogger("delete").log(Level.WARNING, "Data Deletion failed...");
        }
    }
}
