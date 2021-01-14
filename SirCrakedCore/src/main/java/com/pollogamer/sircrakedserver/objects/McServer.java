package com.pollogamer.sircrakedserver.objects;

import com.google.gson.Gson;
import com.pollogamer.SirCrakedCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class McServer {

    private static List<McServer> servers = new ArrayList<>();

    private String serverName;
    private int players;
    private final int maxPlayers;
    private String motd;

    public McServer() {
        this.serverName = Bukkit.getServerName();
        this.players = Bukkit.getOnlinePlayers().size();
        this.maxPlayers = Bukkit.getMaxPlayers();
        this.motd = Bukkit.getMotd();
        format("SYSTEM", "Object created for server " + serverName);
        refresh();
    }

    public void refresh() {
        new BukkitRunnable() {
            @Override
            public void run() {
                McServer tempSv = fromJson(serverName);
                players = tempSv.players;
                motd = tempSv.motd;
                format("DEBUG", "Refrescado players = " + players + " motd " + motd);
            }
        }.runTaskTimerAsynchronously(SirCrakedCore.getCore(), 10, 15);
    }

    public String serializeGson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static McServer fromJson(String serverName) {
        Gson gson = new Gson();
        McServer mcServer = gson.fromJson(SirCrakedCore.getCore().getJedisManager().getString(serverName), McServer.class);
        return mcServer;
    }

    public static List<McServer> getServers() {
        return servers;
    }

    public String getServerName() {
        return serverName;
    }

    public int getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getMotd() {
        return motd;
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }
}
