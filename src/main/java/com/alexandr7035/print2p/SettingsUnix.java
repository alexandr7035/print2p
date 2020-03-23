package com.alexandr7035.print2p;

import java.io.File;

public final class SettingsUnix extends Settings {

    public static final File WORKING_DIR = new File(System.getProperty("user.home") +
                                                    File.separator + ".print2p");

    public void initApp() {

        if (! WORKING_DIR.exists()) {
            WORKING_DIR.mkdir();
        }

    }
}