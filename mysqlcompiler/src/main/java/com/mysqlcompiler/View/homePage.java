package com.mysqlcompiler.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class homePage extends Application {
    private  String DataBase_Name;
    private  final String DB_URL = "jdbc:mysql://localhost:3306/"+ DataBase_Name;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Akshay@123"; // Replace with your actual password

    void setDataBase(String Database){
        this.DataBase_Name = Database;
    }
    String getDataBase (){
        return this.DataBase_Name;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextArea queryArea = new TextArea();
        queryArea.setPrefHeight(100);
        queryArea.setPrefWidth(400);
        queryArea.setWrapText(true);

        //button
        Button executeQueryButton = new Button("Execute Query");
        TextArea resultArea = new TextArea();
        resultArea.setPrefHeight(300);
        resultArea.setPrefWidth(400);
        resultArea.setWrapText(true);
        executeQueryButton.applyCss();
        executeQueryButton.setStyle("-fx-background-color: #00ff00");
        executeQueryButton.setOnAction(e -> executeQuery(queryArea.getText(), resultArea));

        ///Dailogue window
        VBox DataBaseDialougeBox = new VBox();
        Button openDialogButton = new Button("Use DataBase");
        openDialogButton.applyCss();
        openDialogButton.setOnAction(e -> openDialog());
        DataBaseDialougeBox.getChildren().add(openDialogButton);

        VBox vbox = new VBox(10, queryArea, executeQueryButton,DataBaseDialougeBox,resultArea);
        Scene scene = new Scene(vbox, 600, 600);

        primaryStage.setTitle("MySQL Query Executor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
     * Use DataBase function
     *  Author : AKshay
     *  Date:9-7-2024
     * 
     */
    private void openDialog() {
    // Create a new stage for the pop-up dialog
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Enter DataBase Name");

    // Create layout for the dialog
    VBox dialogVbox = new VBox(20);
    TextField textField = new TextField();
    Button okButton = new Button("OK");

    // Set action for the button
    okButton.setOnAction(e -> {
        // Handle button click (e.g., get text from textField)
        String inputText = textField.getText();
        System.out.println("Input text: " + inputText);
        setDataBase(inputText);
        // Close the dialog
        dialogStage.close();
    });
    // Add components to the layout
    dialogVbox.getChildren().addAll(new Label("Enter some text:"), textField, okButton);
    // Create a scene with layout for the dialog
    Scene dialogScene = new Scene(dialogVbox, 300, 200);

    // Set the scene for the dialog stage
    dialogStage.setScene(dialogScene);
    // Set modality of the dialog (blocking interactions with primary stage)
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    // Show the dialog and wait until it is closed
    dialogStage.showAndWait();
}


 // 
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
