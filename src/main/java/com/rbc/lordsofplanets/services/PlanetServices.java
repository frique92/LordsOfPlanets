package com.rbc.lordsofplanets.services;

import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.models.Planet;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanetServices {

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

    public void createTablePlanet() {
        String sql = "CREATE TABLE IF NOT EXISTS planets (\n"
                + " id INTEGER PRIMARY KEY SERIAL,\n"
                + " name VARCHAR(255) NOT NULL,\n"
                + " lord_id INTEGER,\n"
                + " FOREIGN KEY (lord_id) REFERENCES lords (id));";

        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlanet(String name) {
        String sql = "INSERT INTO planets (name) VALUES (?);";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, name);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlanet(Planet planet) {
        String sql = "UPDATE planets SET name = ?, lord_id = ? WHERE id = ?;";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, planet.getName());
                statement.setInt(2, planet.getLord().getId());
                statement.setInt(3, planet.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlanet(Planet planet) {
        String sql = "DELETE FROM planets WHERE id = ?;";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, planet.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLordToPlanet(Planet planet, Lord lord) {
        String sql = "UPDATE planets SET lord_id = ? WHERE id = ?;";

        try (Connection conn = connect()) {
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, lord.getId());
                statement.setInt(2, planet.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
