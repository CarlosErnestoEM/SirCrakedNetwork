package com.pollogamer.wrapper;

import com.pollogamer.wrapper.manager.CommandManager;
import com.pollogamer.wrapper.manager.JedisManager;
import com.pollogamer.wrapper.manager.PacketManager;
import com.pollogamer.wrapper.manager.PortManager;
import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.objects.Server;
import com.pollogamer.wrapper.objects.ServersConfiguration;
import com.pollogamer.wrapper.utils.FileUtils;

import java.io.File;
import java.util.Scanner;

public class Main {

    private Server server;
    private static Main main;
    private CommandManager commandManager;
    private PacketManager packetManager;
    private PortManager portManager;
    private JedisManager jedisManager;
    private ServersConfiguration uhcSimulator;

    public Main() {
        main = this;
        commandManager = new CommandManager();
        server = new Server("Servidor", 4575);
        //jedisManager = new JedisManager();
        packetManager = new PacketManager();
        portManager = new PortManager();
        FileUtils.createDirectory(new File("temp"));
        FileUtils.createDirectory(new File("templates"));
        //uhcSimulator = new ServersConfiguration("UHCSimulator", 2, 2);
        readCommands();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void readCommands() {
        Scanner scanner = new Scanner(System.in);
        String next;
        try {
            while ((next = scanner.nextLine()) != null) {
                String[] string = next.trim().split(" ");
                commandManager.runCommand(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
            readCommands();
        }
    }

    public void closeAllConnections() {
        format("SYSTEM", "Closing all connections...");
        for (McServer mcServer : McServer.getServers()) {
            mcServer.disconnectMcServer();
            mcServer.stop(false);
        }
        format("SYSTEM", "All connections are closed!");
    }

    public void dispatchCommand(String[] command) {
        commandManager.runCommand(command);
    }

    public Server getServer() {
        return server;
    }

    public static Main getMain() {
        return main;
    }

    public PortManager getPortManager() {
        return portManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
