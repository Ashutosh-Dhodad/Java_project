package com.mysqlcompiler.utils;
import com.mysqlcompiler.Controllers.SqlController;
import com.mysqlcompiler.View.homePage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utils {
   static String dbNamesQuery = "Show Databases";

    /*
     * Use DataBase function
     *  Author : AKshay
     *  Date:9-7-2024
     * 
     */
        public static void authdialog() {
        // Create a new stage for the pop-up dialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Enter Details");
    
        // Create layout for the dialog
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);

        TextField userNameTextField = new TextField();
        userNameTextField.setMaxWidth(400);

        TextField passwordTextField = new TextField();
        passwordTextField.setMaxWidth(400);

        Button okButton = new Button("OK");
        okButton.setMaxWidth(100);
            
        // Set action for the button
        okButton.setOnAction(e -> {
            // Handle button click (e.g., get text from textField)
            String username = userNameTextField.getText();
            String password = passwordTextField.getText();
            
            homePage.setCredentials(username, password);
            // Close the dialog
            dialogStage.close();
        });
        // Add components to the layout
        dialogVbox.getChildren().addAll(new Label("Enter User Name:"), userNameTextField);
        dialogVbox.getChildren().addAll(new Label("Enter Password:"), passwordTextField, okButton);
        // Create a scene with layout for the dialog
        Scene dialogScene = new Scene(dialogVbox, 800, 600);
    
        // Set the scene for the dialog stage
        dialogStage.setScene(dialogScene);
        // Set modality of the dialog (blocking interactions with primary stage)
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        // Show the dialog and wait until it is closed
        dialogStage.showAndWait();
    }


    /*
     * Dailogue Box open for Entering Name ob DataBase
     * Auther: Akshay Jadhav
     * Date:-13/7/24
     */
    public static void openDialog(String DataBase_Name,String DB_URL,String DB_USER,String DB_PASSWORD) {
        // Create a new stage for the pop-up dialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("DataBase Name");
    
        // Create layout for the dialog
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        textField.setMaxWidth(400);
    
        Button okButton = new Button("OK");
        okButton.setMaxWidth(100);
    
        TextArea databaseNamesArea = new TextArea();
        //.setMargin(databaseNamesArea new Insets(0, 0, 0, 350)); 
        databaseNamesArea.setPrefHeight(300);
        databaseNamesArea.setMaxWidth(200);
        databaseNamesArea.setWrapText(true);
    
        Button show_databasesButton = new Button("show databases");
        
        show_databasesButton.setMaxWidth(300);
        show_databasesButton.setOnAction(e -> {
            // Handle button click (e.g., get text from textField)
            // Close the dialog
            homePage.setDataBase(DataBase_Name);
            SqlController.executeQueryToSetDataBase(dbNamesQuery, databaseNamesArea,DB_URL,DB_USER,DB_PASSWORD);
            SqlController.showDatabaseNames(dbNamesQuery, databaseNamesArea,DB_URL,DB_USER,DB_PASSWORD);
            
        });
    
        VBox vbox = new VBox(30);
        
        HBox diaHBox = new HBox(30,dialogVbox,databaseNamesArea, show_databasesButton, vbox);
        
        
        // Set action for the button
        okButton.setOnAction(e -> {
            // Handle button click (e.g., get text from textField)
            String inputText = textField.getText();
            homePage.setDataBase(inputText);
            // Close the dialog
            dialogStage.close();
        });
        // Add components to the layout
        vbox.getChildren().addAll(new Label("Enter Database name:"), textField, okButton);
        // Create a scene with layout for the dialog
        Scene dialogScene = new Scene(diaHBox, 800, 600);
    
        // Set the scene for the dialog stage
        dialogStage.setScene(dialogScene);
        // Set modality of the dialog (blocking interactions with primary stage)
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        // Show the dialog and wait until it is closed
        dialogStage.showAndWait();
    }
    
}
