package com.alexandr7035.print2p;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.*;

public class PrintWrapper {

    private Document printedDoc;

    private static ArrayList<String> getPrintFirstCommand(String pdfPath) {
        String print_command =  "pdftops \"" +  pdfPath + "\" - | psselect -e | lp -o outputorder=reverse";
        
        ArrayList<String> command = new ArrayList(Arrays.asList("/bin/bash", "-c", print_command));
        return command;
    }

    private static ArrayList<String> getPrintSecondCommand(String pdfPath) {
        String print_command =  "lp -o page-set=odd -o outputorder=normal \"" + pdfPath + "\"";
        
        ArrayList<String> command = new ArrayList(Arrays.asList("/bin/bash", "-c", print_command));
        return command;
    }

    public static void printFirst(Document document) {
        System.out.println("PrintWrapper: printfirst called");

        ArrayList<String> command = getPrintFirstCommand(document.getPreparedDocPath());
        executeCommand(command);
    }

    public static void printSecond(Document document) {
        System.out.println("PrintWrapper: printsecond called");

        ArrayList<String> command = getPrintSecondCommand(document.getPreparedDocPath());
        executeCommand(command);
    }
    

    private static int executeCommand(ArrayList<String> command) {
        try { 
            
             ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
             Process p = pb.start();
             
             p.waitFor();
             return p.exitValue();
        }

        catch (IOException|InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

