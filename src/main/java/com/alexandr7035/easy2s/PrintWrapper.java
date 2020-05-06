package com.alexandr7035.easy2s;

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

    public static int printFirst(Document document) {
        ArrayList<String> command = getPrintFirstCommand(document.getPreparedDocPath());
        return CmdExecutor.executeSilentCommand(command);
    }

    public static int printSecond(Document document) {
        ArrayList<String> command = getPrintSecondCommand(document.getPreparedDocPath());
        return CmdExecutor.executeSilentCommand(command);
    }
    
}

