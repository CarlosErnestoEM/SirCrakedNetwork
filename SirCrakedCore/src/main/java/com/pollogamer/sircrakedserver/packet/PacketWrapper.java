package com.pollogamer.sircrakedserver.packet;

import org.bukkit.Bukkit;

public abstract class PacketWrapper {

    private String packetName;

    public PacketWrapper(String packetName) {
        this.packetName = packetName;
    }

    public abstract void execute(String[] args);

    public String getPacketName() {
        return packetName;
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }
}
