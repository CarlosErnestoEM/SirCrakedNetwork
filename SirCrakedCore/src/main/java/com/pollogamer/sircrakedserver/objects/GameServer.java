package com.pollogamer.sircrakedserver.objects;


import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.playercountapi.ServerPing;
import org.bukkit.scheduler.BukkitRunnable;

public class GameServer extends BukkitRunnable {

    private String name;
    private String bungeename;
    private String ip;
    private int port;
    public int onlineplayers;
    public int maxplayers;
    public String motd;
    public boolean online;

    public GameServer(String name, String bungeename, String ip, int port) {
        this.name = name;
        this.bungeename = bungeename;
        this.ip = ip;
        this.port = port;
        runTaskTimerAsynchronously(SirCrakedCore.getCore(), 0, 4);
    }

    @Override
    public void run() {
        setV();
    }

    public void setV() {
        String info = ServerPing.getAllInfo(ip, port, 100);
        String[] infoo = info.split(",");
        onlineplayers = Integer.parseInt(infoo[0]);
        maxplayers = Integer.parseInt(infoo[1]);
        if (onlineplayers == -1 || maxplayers == -1) {
            online = false;
            return;
        } else {
            online = true;
            motd = infoo[2];
        }
    }

    public String getName() {
        return name;
    }

    public String getBungeename() {
        return bungeename;
    }
}
