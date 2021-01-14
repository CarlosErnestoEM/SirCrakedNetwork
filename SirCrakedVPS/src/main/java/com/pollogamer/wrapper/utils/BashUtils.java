package com.pollogamer.wrapper.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BashUtils {

    public static boolean executeBashCommand(String command) {
        boolean success = false;
        Runtime r = Runtime.getRuntime();
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();
            success = true;
        } catch (Exception e) {
            System.err.println("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

}