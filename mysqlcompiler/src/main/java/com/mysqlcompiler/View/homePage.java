package com.mysqlcompiler.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class homePage extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/minipro";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "PFH#23kgrw9"; // Replace with your actual password

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea queryArea = new TextArea();
        queryArea.setPrefHeight(100);
        queryArea.setPrefWidth(400);
        queryArea.setWrapText(true);

        //add button color
        Button executeQueryButton = new Button("Execute Query");
        TextArea resultArea = new TextArea();
        resultArea.setPrefHeight(300);
        resultArea.setPrefWidth(400);
        resultArea.setWrapText(true);

        executeQueryButton.setOnAction(e -> executeQuery(queryArea.getText(), resultArea));

        VBox vbox = new VBox(10, queryArea, executeQueryButton, resultArea);
        Scene scene = new Scene(vbox, 600, 600);

        primaryStage.setTitle("MySQL Query Executor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void executeQuery(String query, TextArea resultArea) {
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

    private void displayResultSet(ResultSet resultSet, TextArea resultArea) throws SQLException {
        StringBuilder result = new StringBuilder();
        int columnCount = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.append(resultSet.getString(i)).append("\t");
            }
            result.append("\n");
        }

        resultArea.setText(result.toString());
    }

}
