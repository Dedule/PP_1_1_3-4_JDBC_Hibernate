package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = Util.getConnection();
             Statement stat = con.createStatement()) {
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS user (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` TINYINT(100) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection();
             Statement stat = con.createStatement()) {
            stat.executeUpdate("drop table if exists user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection();
             PreparedStatement stat = con.prepareStatement("INSERT INTO user " +
                     "(`name`, `lastName`, `age`) VALUES (?, ?, ?);")) {
            stat.setString(1, name);
            stat.setString(2, lastName);
            stat.setByte(3, age);
            stat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection();
             PreparedStatement stat = con.prepareStatement("delete from user where id = ?")) {
            stat.setLong(1, id);
            stat.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = Util.getConnection();
             Statement stat = con.createStatement()) {
            stat.executeQuery("select * from user");
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                userList.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection();
             Statement stat = con.createStatement()) {
            stat.executeUpdate("delete from user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
