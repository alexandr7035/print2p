package com.alexandr7035.print2p;

import java.io.IOException;
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;



import javafx.scene.input.*;


public class Main extends Application {
    
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGTH = 300;

    private Button printFirstBtn;
    private Button printSecondBtn;
    private Button setPrintedFileBtn;
    private Button resetPrintedFileBtn;

    private TextArea printedFileField;

    private GridPane mainLayout;
    private Scene scene;

    private Document printedDoc;

    // Class constructor
    public Main() {

        Settings.initApp();

        this.printedDoc = null;

    }

    @Override
    public void start(Stage stage) {
        
        // Load FXML
        this.loadFXML();
        this.scene = new Scene(mainLayout);
        stage.setScene(scene);
        
        // Widgets
        this.printedFileField = (TextArea) scene.lookup("#printedFileField");

        // printFirstBtn
         // FIXME use lambda
        this.printFirstBtn = (Button) scene.lookup("#printFirstBtn");
        this.printFirstBtn.setDisable(true);
        this.printFirstBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PRESSED: printFirstBtn");

                if (Main.this.printedDoc == null) {
                    System.out.println("WARNING: no printed document set");
                }

            }
        });

        // printSecondBtn
        // FIXME use lambda
        this.printSecondBtn = (Button) scene.lookup("#printSecondBtn");
        this.printSecondBtn.setDisable(true);
        this.printSecondBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PRESSED: printSecondBtn");

                if (Main.this.printedDoc == null) {
                    System.out.println("WARNING: no printed document set");
                }
            }
        });


        // setPrintedFileBtn
        // FIXME use lambda
        this.setPrintedFileBtn = (Button) scene.lookup("#setPrintedFileBtn");
        this.setPrintedFileBtn.setDisable(true);
        this.setPrintedFileBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PRESSED: setPrintedFileBtn");
            }
        });
        
        // resetPrintedFileBtn
        // FIXME use lambda
        this.resetPrintedFileBtn = (Button) scene.lookup("#resetPrintedFileBtn");
        this.resetPrintedFileBtn.setDisable(true);
        this.resetPrintedFileBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PRESSED: resetPrintedFileBtn");
                Main.this.resetPrintedFile();
            }
        });

        



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
        this.printedDoc = new Document(new File(fileName));

        // If the document succesfully converted to pdf
        if (this.printedDoc.prepareDoc()) {

            // Enable print buttons
            this.printFirstBtn.setDisable(false);
            this.printSecondBtn.setDisable(false);
            this.resetPrintedFileBtn.setDisable(false);

            System.out.println("PAGES: " + this.printedDoc.getPagesCount());
        }
        else {
            this.resetPrintedFile();
        }    
        
      }

    private void resetPrintedFile() {
        this.printedFileField.setText("");

        // Disable print buttons
        this.printFirstBtn.setDisable(true);
        this.printSecondBtn.setDisable(true);
        this.resetPrintedFileBtn.setDisable(true);

        // Set printedDoc to null
        this.printedDoc = null;
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
