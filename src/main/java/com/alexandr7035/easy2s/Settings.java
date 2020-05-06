package com.alexandr7035.easy2s;

import java.io.File;

public final class Settings {

    public static final String PROJECT_NAME = "easy2s";
    public static final double PROJECT_VERSION = 1.0;

    public static final String WINDOW_TITLE = "Easy2S v" + PROJECT_VERSION;

    private static final String WORKING_DIR_PATH = System.getProperty("user.home") +
                                                     File.separator + "." + PROJECT_NAME;

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