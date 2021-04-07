package com.rbc.lordsofplanets.services;

import com.rbc.lordsofplanets.models.Lord;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LordServices {

    @Value("${spring.datasource.url}")
    private String url;

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createTableLords() {
        String sql = "CREATE TABLE IF NOT EXISTS lords (\n"
                + " id INTEGER PRIMARY KEY SERIAL,\n"
                + " name varchar(255) NOT NULL,\n"
                + " age INTEGER);";

        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLord(String name, int age) {
        String sql = "INSERT INTO lords (name, age) VALUES (?,?);";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLord(Lord lord) {
        String sql = "UPDATE lords SET name = ?, age = ? WHERE id = ?;";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, lord.getName());
                statement.setInt(2, lord.getAge());
                statement.setInt(3, lord.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLord(Lord lord) {
        String sql = "DELETE FROM lords WHERE id = ?;";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, lord.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lord> getTop10YoungestLords() {
        String sql = "SELECT id, name, age FROM lords ORDER BY age LIMIT 10;";

        List<Lord> lords = new ArrayList<>();

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    lords.add(new Lord(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lords;
    }

}
