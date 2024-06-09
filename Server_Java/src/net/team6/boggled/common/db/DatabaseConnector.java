package net.team6.boggled.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private Connection connection;
    private static DatabaseConnector instance;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/boggled_game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public DatabaseConnector() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Database connection initialized.");
        } catch (SQLException e) {
            System.out.println("Error initializing connection to the database." + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the JDBC driver.");
        }
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
