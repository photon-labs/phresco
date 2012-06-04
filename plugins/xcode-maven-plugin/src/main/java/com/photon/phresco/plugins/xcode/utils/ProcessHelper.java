package com.photon.phresco.plugins.xcode.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper for shutdown iPhone Simulator
 */
public class ProcessHelper {

    private static final String EMULATOR_COMMAND = "ps axo pid,command | grep 'iPhone Simulator'";
    private static final String KILL = "killall 'iPhone Simulator'";

    public static boolean isProcessRunning() throws IOException {

        String[] commands = new String[]{"/bin/sh", "-c", EMULATOR_COMMAND};
        Process p = Runtime.getRuntime().exec(commands);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("iPhone Simulator.app")) {
                return true;
            }
        }
        return false;
    }

    public static void killSimulatorProcess() throws IOException {
        String[] commands = new String[]{"/bin/sh", "-c", KILL};
        Runtime.getRuntime().exec(commands);
    }
}