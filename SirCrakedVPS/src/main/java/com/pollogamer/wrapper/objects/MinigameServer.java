package com.pollogamer.wrapper.objects;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.utils.FileUtils;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinigameServer {

    private static List<MinigameServer> servers = new CopyOnWriteArrayList<>();

    private File fileDirectory;
    private String svType;
    private Integer port;
    private Screen screen;

    public MinigameServer(File fileDirectory, String svType, Integer port) {
        this.fileDirectory = fileDirectory;
        this.svType = svType;
        this.port = port;
        servers.add(this);
        screen = new Screen(svType + "-" + port);
        format("ServerCreator", "Created " + svType + "-" + port);
        init();
        screen.sendCommand("cd /home/Wrapper/temp/" + svType + "-" + port);
        screen.startServer("java -jar -Xmx768M spigot.jar -p " + port);
    }

    public void end() {
        format("MINIGAME-" + svType + "-" + port, "Stopping server...");
        screen.stopServer();
        format("MINIGAME-" + svType + "-" + port, "Deleting folder...");
        FileUtils.deleteFolder(fileDirectory);
        format("MINIGAME-" + svType + "-" + port, "Server stopped!");
        Main.getMain().getPortManager().putPortAvailable(port);
        screen.deleteScreen();
        servers.remove(this);
    }


    public void init() {
        try {
            Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileDirectory.getPath() + File.separator + "server.properties"), true), "UTF-8"));
            output.write("server-name=" + svType + "-" + port + "\n");
            output.write("server-port=" + port + "\n");
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            format("ERROR", "Writting files for server " + svType + "-" + port);
        }
    }

    public static List<MinigameServer> getServers() {
        return servers;
    }

    public static MinigameServer getMiniGame(String sv) {
        for (MinigameServer minigameServer : servers) {
            if ((minigameServer.getSvType() + "-" + minigameServer.getPort()).equalsIgnoreCase(sv)) {
                return minigameServer;
            }
        }
        return null;
    }

    public File getFileDirectory() {
        return fileDirectory;
    }

    public String getSvType() {
        return svType;
    }

    public Integer getPort() {
        return port;
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
