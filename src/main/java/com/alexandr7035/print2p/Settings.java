package com.alexandr7035.print2p;

import java.io.File;

public final class Settings {

    private static final String WORKING_DIR_PATH = System.getProperty("user.home") +
                                                     File.separator + ".print2p";

    private static final File WORKING_DIR = new File(WORKING_DIR_PATH);

    private static final File DOC_DIR = new File(WORKING_DIR_PATH + "/doc");

    public static void initApp() {

        if (! WORKING_DIR.exists()) {
            WORKING_DIR.mkdir();
        }

        if (! DOC_DIR.exists()) {
            DOC_DIR.mkdir();
        }

    }
}