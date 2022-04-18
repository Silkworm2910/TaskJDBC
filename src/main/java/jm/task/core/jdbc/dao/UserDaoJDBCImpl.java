package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;
    private static long idCount = 0;

    public UserDaoJDBCImpl() {
      openConnection();
    }

    @Override
    public void openConnection() {
        connection = Util.getConnection();
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT not NULL, " +
                    " name VARCHAR(255), " +
                    " lastname VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String query = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(query);
            idCount = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, idCount++);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String query = "DELETE FROM users";
            statement.executeUpdate(query);
            idCount = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
