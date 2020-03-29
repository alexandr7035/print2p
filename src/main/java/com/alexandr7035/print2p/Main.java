package com.alexandr7035.print2p;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

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
    private Button viewDocBtn;

    private Label printedFileField;
    private Label pagesCountLabel;

    private ProgressBar progressBar;

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
        // stage.initStyle(StageStyle.UNDECORATED);
        
        // Apply styles
        scene.getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm());

        // Widgets
        this.printedFileField = (Label) scene.lookup("#printedFileField");
        this.pagesCountLabel = (Label) scene.lookup("#pagesCountLabel");
        this.progressBar = (ProgressBar) scene.lookup("#progressBar");

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

                PrintWrapper.printFirst(Main.this.printedDoc);

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

                PrintWrapper.printSecond(Main.this.printedDoc);
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

        // viewDocBtn
        // FIXME use lambda
        this.viewDocBtn = (Button) scene.lookup("#viewDocBtn");
        this.viewDocBtn.setDisable(true);
        this.viewDocBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PRESSED: viewDocBtn");

                ArrayList<String> viewCommand = new ArrayList(Arrays.asList("xdg-open", 
                                                         Main.this.printedDoc.getPreparedDocPath()));

                System.out.println(viewCommand.toString());

                CmdExecutor.executeSilentCommand(viewCommand);
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


    // Called when printed file is dropped to printFileLabel
    // or when Select button is presed.
    // Tries to convert document to pdf (see Document.prepareDocument() method)
    // If succeeded, enables print and other buttons
    // If no, calls resetPrintedFile() method
    private void setPrintedFile(String filePath) {

        // FIXME can document be extended from File???
        File doc_file = new File(filePath);
        this.printedDoc = new Document(doc_file);

        // Use background Task to prepare doc
        Task prepareDocTask = new Task<Boolean>() {

            @Override
            protected Boolean call() throws Exception {
                return Main.this.printedDoc.prepareDoc();
            }
        };

        // Execute when task finishes
        prepareDocTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, 
            new EventHandler<WorkerStateEvent>() {
               @Override
                  public void handle(WorkerStateEvent t) {
                    // Get the result of the task (boolean)
                    // True if document was converted to pdf succesfully
                    Boolean task_result = (Boolean) prepareDocTask.getValue();

                    if (task_result == true) { 
                        // Unbind progressBar 
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(0);
                        
                        // Enable print buttons
                        // printFirstButton is enabled only if PDF contains more than 1 page 
                        // (because it prints even pages)
                        if (printedDoc.getPagesCount() > 1 ) {
                             printFirstBtn.setDisable(false);
                        }
                        printSecondBtn.setDisable(false);
                        resetPrintedFileBtn.setDisable(false);
                        viewDocBtn.setDisable(false);

                        // Set info to widgets
                        printedFileField.setText(doc_file.getName());
                        pagesCountLabel.setText("" + printedDoc.getPagesCount());

                        System.out.println("PAGES: " + printedDoc.getPagesCount());
                        }

                        // If conversion to pdf is not successfull 
                        else {
                           // See resetPrintedFile() method
                           resetPrintedFile();
                        }    
                    }
                 
             });
             
        // Bind task to progressBar and run
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(prepareDocTask.progressProperty());
        new Thread(prepareDocTask).start();
        
      }

    
    // Clear all widgets and disable buttons as when program is started
    private void resetPrintedFile() {

        // Reset widgets
        this.printedFileField.setText("");
        this.pagesCountLabel.setText("");

        // Disable buttons
        this.printFirstBtn.setDisable(true);
        this.printSecondBtn.setDisable(true);
        this.resetPrintedFileBtn.setDisable(true);
        this.viewDocBtn.setDisable(true);

        // Set printedDoc to null
        this.printedDoc = null;
    }

    
    // This part is responsible for dropping file to printFileLabel
    // If dropped, Main.setPrintedFile() method is called
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
                    
                    // Reset printed file if was set before
                    Main.this.resetPrintedFile();
                    // Set printed file
                    Main.this.setPrintedFile(db.getFiles().get(0).getAbsolutePath());
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
