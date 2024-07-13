package com.mysqlcompiler.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TextArea;

public class SqlController {

        public static void executeQuery(String query, TextArea resultArea,String DB_URL,String DB_USER,String DB_PASSWORD) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                boolean isResultSet = statement.execute(query);

                if (isResultSet) {
                    ResultSet resultSet = statement.getResultSet();
                    displayResultSet(resultSet, resultArea);
                } else {
                    int updateCount = statement.getUpdateCount();
                    resultArea.setText("Query OK, " + updateCount + " rows affected.");
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void executeQueryToSetDataBase(String query, TextArea resultArea,String DB_URL,String DB_USER,String DB_PASSWORD) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                boolean isResultSet = statement.execute(query);

                if (isResultSet) {
                    ResultSet resultSet = statement.getResultSet();
                    displayResultSet(resultSet, resultArea);
                } else {
                    int updateCount = statement.getUpdateCount();
                    resultArea.setText("Query OK, " + updateCount + " rows affected.");
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    public static void displayResultSet(ResultSet resultSet, TextArea resultArea) throws SQLException {
        StringBuilder result = new StringBuilder();
        int columnCount = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.append(resultSet.getString(i)+ "  |").append("\t");
            }
            result.append("\n");
        }

        resultArea.setText(result.toString());
    }

    public static void  showDatabaseNames(String dbNamesQuery , TextArea databaseNamesArea,String DB_URL,String DB_USER,String DB_PASSWORD){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                boolean isResultSet = statement.execute(dbNamesQuery);

                if (isResultSet) {
                    ResultSet resultSet = statement.getResultSet();
                    displayResultSet(resultSet, databaseNamesArea);
                } else {
                    int updateCount = statement.getUpdateCount();
                    databaseNamesArea.setText("Query OK, " + updateCount + " rows affected.");
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            databaseNamesArea.setText("Error: " + ex.getMessage());
        }
    }
}
