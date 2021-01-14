package com.pollogamer.auth;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;

public class PluginMessageTask extends BukkitRunnable {


    private final Principal plugin;
    private final Player player;
    private final ByteArrayOutputStream bytes;

    public PluginMessageTask(Principal plugin, Player player, ByteArrayOutputStream bytes) {
        this.plugin = plugin;
        this.player = player;
        this.bytes = bytes;
    }

    public void run() {
        this.player.sendPluginMessage(this.plugin, this.plugin.outgoingChannel, this.bytes.toByteArray());
    }
}
