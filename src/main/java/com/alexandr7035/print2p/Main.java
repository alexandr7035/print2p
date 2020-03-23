package com.alexandr7035.print2p;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

import javafx.scene.input.*;

public class Main extends Application {
    
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGTH = 300;

    private Button printFirstBtn;
    private Button printSecondBtn;
    private TextArea printedFileField;

    private GridPane mainLayout;
    private Scene scene;


    @Override
    public void start(Stage stage) {
        
        // Load FXML
        this.loadFXML();
        this.scene = new Scene(mainLayout);
        stage.setScene(scene);
        
        // Widgets
        this.printedFileField = (TextArea) scene.lookup("#printedFileField");
        this.printFirstBtn = (Button) scene.lookup("#printFirstBtn");
        this.printSecondBtn = (Button) scene.lookup("#printSecondBtn");

        // Allow dropping file to textArea
        this.enableFileDropping();

        stage.show();

    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/views/main.fxml"));
        this.mainLayout = null;
        
        try {
            this.mainLayout = (GridPane) loader.load(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPrintedFile(String fileName) {
        this.printedFileField.setText(fileName);
    }

    private void enableFileDropping() {
        this.printedFileField.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != printedFileField
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        this.printedFileField.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    // CHOOSE FILE 
                    Main.this.setPrintedFile(db.getFiles().get(0).getAbsolutePath());
                    //printedFileField.setText();
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
