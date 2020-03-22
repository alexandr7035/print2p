package com.alexandr7035.print2p;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGTH = 300;

    private Button printFirstBtn;
    private Button printSecondBtn;
    
    private GridPane mainLayout;
    private HBox buttonsLayout;


    // Class constructor
    public Main() {
        
        // Init widgets
        //this.mainLayout = new VBox();
        this.buttonsLayout = new HBox();

        this.printFirstBtn = new Button("Print first");
        this.printSecondBtn = new Button("Print Second");

    }

    @Override
    public void start(Stage stage) {
        
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/views/main.fxml"));
        this.mainLayout = null;
        
        try {
            mainLayout = (GridPane) loader.load(); 

        } catch (IOException e1) {
            System.out.println("EXCEEEEEEEEEEEEEEEEEPTION");
            e1.printStackTrace();
        }

        stage.setScene(new Scene(mainLayout));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
