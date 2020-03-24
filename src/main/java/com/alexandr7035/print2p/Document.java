package com.alexandr7035.print2p;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.alexandr7035.print2p.Settings;

import java.util.Arrays;
import java.util.ArrayList;

public class Document {

    private String docPath;
    private String docName;
    private String preparedDocPath;
    private boolean isPrepared;

    private static List<String> prepareDocCommand = Arrays.asList("unoconv", "-o");

    public Document(File document) {
        this.docPath = document.getAbsolutePath();
        this.docName = document.getName();

        this.preparedDocPath = Settings.DOC_DIR + "/" + this.docName + ".pdf";

    }

    
    // Convert file to pdf using unoconv util
    // PDF is placed into WORKING_DIR/doc directory
    //
    // FIXME don't convert PDF to PDF, just copy them FIXME
    public boolean prepareDoc() {
        try {
            // Form unoconv command
            List<String> executedCommand = new ArrayList<String>(prepareDocCommand);
            // Output file
            executedCommand.add(this.preparedDocPath);
            // Printed file path
            executedCommand.add(this.docPath);

            // Run command and get exit status
            System.out.println("RUN COMMAND: " + executedCommand.toString());
            ProcessBuilder pb = new ProcessBuilder(executedCommand);
            Process p = pb.start();
            p.waitFor();
            System.out.println(p.exitValue());

            if (p.exitValue() == 0) {
                System.out.println("UNOCONV SUCCEEDED");
                
                this.isPrepared = true;
                return true;
            }
            else {
                System.out.println("UNOCONV FAILED");

                this.isPrepared = false;
                return false;
            }

        } catch (IOException|InterruptedException e) {
            System.out.println("UNOCONV FAILED. IOEXCEPTION CAUGHT");
            e.printStackTrace();

            this.isPrepared = false;
            return false;
        }

    }

    private void getPagesCound() {

    }

}