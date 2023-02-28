package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    private static String HOST = "localhost"; // website ip address
    private static String USER = "postgres";
    private static String PASS = "";
    private static String DATABASE = "homework";

    private static Connection connection;

    protected static Connection getInstance() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }

    private static Connection connect() {
        try {
            //jdbc:postgresql://localhost:5432/homework
            return DriverManager.getConnection("jdbc:postgresql://"+HOST+":5432/"+DATABASE+"?autoReconnect=true", USER, PASS);
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return null;
        }
    }



}
