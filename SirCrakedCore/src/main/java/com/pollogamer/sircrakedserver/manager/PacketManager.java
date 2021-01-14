package com.pollogamer.sircrakedserver.manager;

import com.pollogamer.sircrakedserver.packet.PacketWrapper;
import com.pollogamer.sircrakedserver.packet.packets.PacketCommand;
import com.pollogamer.sircrakedserver.packet.packets.PacketDisconnect;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {

    private static List<PacketWrapper> packets = new ArrayList<>();

    public PacketManager() {
        registerPacket(new PacketCommand());
        registerPacket(new PacketDisconnect());
        //registerPacket(new PacketServer());
    }

    public void runPacket(String[] args) {
        PacketWrapper packetWrapper = getPacket(args);
        if (packetWrapper != null) {
            packetWrapper.execute(args);
        } else {
            format("ERROR", "Packet " + args[0] + " not exist!");
        }
    }

    public PacketWrapper getPacket(String[] args) {
        for (PacketWrapper packetWrapper : packets) {
            if (packetWrapper.getPacketName().equalsIgnoreCase(args[0])) {
                return packetWrapper;
            }
        }
        return null;
    }

    public void registerPacket(PacketWrapper packetWrapper) {
        packets.add(packetWrapper);
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }

}
