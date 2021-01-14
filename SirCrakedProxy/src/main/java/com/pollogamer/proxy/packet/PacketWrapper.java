package com.pollogamer.proxy.packet;

public abstract class PacketWrapper {

    private String packetName;

    public PacketWrapper(String packetName) {
        this.packetName = packetName;
    }

    public abstract void execute(String[] args);

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public String getPacketName() {
        return packetName;
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
