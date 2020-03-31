package com.alexandr7035.print2p;

import java.io.File;

public final class Settings {

    private static final String WORKING_DIR_PATH = System.getProperty("user.home") +
                                                     File.separator + ".print2p";

    private static final File WORKING_DIR = new File(WORKING_DIR_PATH);
    public static final File DOC_DIR = new File(WORKING_DIR_PATH + "/doc");
    public static final File LOG_DIR = new File(WORKING_DIR_PATH + "/log");
    
    public static final String LOG_PATTERN = LOG_DIR.getAbsolutePath() + "/log";
    public static final int LOG_FILES_NUMBER = 10;
    public static final int LOG_LIMIT = 1000000;

    public static void initApp() {

        if (! WORKING_DIR.exists()) {
            WORKING_DIR.mkdir();
        }

        if (! DOC_DIR.exists()) {
            DOC_DIR.mkdir();
        }

        if (! LOG_DIR.exists()) {
            LOG_DIR.mkdir();
        }

    }
}