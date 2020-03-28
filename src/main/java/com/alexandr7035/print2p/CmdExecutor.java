package com.alexandr7035.print2p;

import java.util.ArrayList;
import java.io.IOException;

public class CmdExecutor {

    public static int executeSilentCommand(ArrayList<String> command) {
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