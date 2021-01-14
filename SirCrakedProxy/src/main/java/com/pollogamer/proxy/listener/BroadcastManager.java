package com.pollogamer.proxy.listener;


import com.pollogamer.proxy.Principal;
import net.md_5.bungee.BungeeCord;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BroadcastManager implements Runnable {

    private String prefix;
    List<String> msg;

    public BroadcastManager(Principal plugin) {
        this.prefix = "§4§lSir§1§lCraked §7» §a";
        Principal.getPlugin().getProxy().getScheduler().schedule(plugin, this, 1, 3, TimeUnit.MINUTES);
    }

    public void run() {
        msg = Principal.config.getStringList("broadcast");
        Random r = new Random();
        String random = msg.get(r.nextInt(msg.size()));
        BungeeCord.getInstance().broadcast(" ");
        BungeeCord.getInstance().broadcast(prefix + random.replaceAll("&", "§"));
        BungeeCord.getInstance().broadcast(" ");
    }
}
