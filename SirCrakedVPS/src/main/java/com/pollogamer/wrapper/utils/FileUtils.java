package com.pollogamer.wrapper.utils;

import java.io.*;

public class FileUtils {

    public static boolean deleteFolder(File paramFile) {
        if (paramFile.exists()) {
            File[] arrayOfFile = paramFile.listFiles();
            for (int i = 0; i < arrayOfFile.length; i++) {
                if (arrayOfFile[i].isDirectory()) {
                    deleteFolder(arrayOfFile[i]);
                } else {
                    arrayOfFile[i].delete();
                }
            }
        }
        return paramFile.delete();
    }

    public static void createDirectory(File file) {
        if (!file.exists()) {
            file.mkdirs();
            format("SYSTEM", "Directory " + file.getName() + " created!");
        }
    }

    public static boolean existFile(File file) {
        return file.exists();
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
