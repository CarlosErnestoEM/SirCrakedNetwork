package com.pollogamer.wrapper.packet;

import com.pollogamer.wrapper.objects.McServer;

public abstract class PacketWrapper {

    private String packetName;

    public PacketWrapper(String packetName) {
        this.packetName = packetName;
    }

    public abstract void execute(String[] args, McServer mcServer);

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public String getPacketName() {
        return packetName;
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
