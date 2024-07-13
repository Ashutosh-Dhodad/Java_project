package com.mysqlcompiler.View;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.mysqlcompiler.Controllers.SqlController;
import com.mysqlcompiler.utils.Utils;

public class homePage extends Application {
    public static String DataBase_Name="aa";
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static void setCredentials(String username, String password){
        DB_USER = username;
        DB_PASSWORD = password;
        System.out.println(DB_USER);
    }

   public static void setDataBase(String Database){
        DataBase_Name = Database;
        System.out.println("MY Database "+DataBase_Name);
        DB_URL = "jdbc:mysql://localhost:3306/"+ DataBase_Name;
        System.out.println(DB_URL);
    }

    String getDataBase (){
        System.out.println("MY Database2 "+DataBase_Name);
        DB_URL = "jdbc:mysql://localhost:3306/"+ DataBase_Name;
        return DataBase_Name;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Utils.authdialog();
        // Ensure the username and password is set
        if (DB_USER == null || DB_PASSWORD == null) {
            System.out.println("Please enter username and password!!!");
            return;
        }

        Utils.openDialog(DataBase_Name,DB_URL,DB_USER,DB_PASSWORD);

        // Ensure the database name is set
        if (getDataBase() == null || getDataBase().isEmpty()) {
            System.out.println("Database name must be set before proceeding!!!");
            return;
        }
          ///Dailogue window
        HBox DataBaseDialougeBox = new HBox();

        TextArea queryArea = new TextArea();
        VBox.setMargin(queryArea, new Insets(0, 0, 0, 350)); 
        queryArea.setPrefHeight(400);
        queryArea.setMaxWidth(1000);
        queryArea.setWrapText(true);

        TextArea resultArea = new TextArea();
        VBox.setMargin(resultArea, new Insets(0, 0, 100, 350)); 
        resultArea.setPrefHeight(600);
        resultArea.setPrefWidth(1000);
        resultArea.setWrapText(true);


        //Execute button
        Button executeQueryButton = new Button("Execute Query");
        executeQueryButton.applyCss();
        executeQueryButton.setStyle("-fx-background-color: #006400; -fx-text-fill: white;");
        executeQueryButton.setOnAction(e -> SqlController.executeQuery(queryArea.getText(), resultArea,DB_URL,DB_USER,DB_PASSWORD));


        //clear button
        Button clearButton = new Button("Clear Query");
        clearButton.applyCss();
        clearButton.setStyle("-fx-background-color: #006400; -fx-text-fill: white;");
        clearButton.setOnAction(e-> {
             queryArea.clear();
             resultArea.clear();
        });

        Button openDialogButton = new Button("Use DataBase");
        openDialogButton.setStyle("-fx-background-color: #006400; -fx-text-fill: white;");
        openDialogButton.applyCss();
        openDialogButton.setOnAction(e -> Utils.openDialog(DataBase_Name,DB_URL,DB_USER,DB_PASSWORD));
    

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(openDialogButton, new Insets(0, 0, 0, 300));
        HBox.setMargin(executeQueryButton, new Insets(0, 0, 0, 50)); 
        HBox.setMargin(clearButton, new Insets(0, 0, 0, 50));
        
        hBox.getChildren().add(openDialogButton);
        hBox.getChildren().add(executeQueryButton);
        hBox.getChildren().add(clearButton);
        

        VBox vbox = new VBox(10, queryArea, hBox, DataBaseDialougeBox,resultArea);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(70)); 

        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(vbox);

        Scene scene = new Scene(root);

        primaryStage.setTitle("MySQL Query Executor");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
