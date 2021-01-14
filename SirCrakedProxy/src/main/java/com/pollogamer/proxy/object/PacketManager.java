package com.pollogamer.proxy.object;

import com.pollogamer.proxy.packet.PacketWrapper;
import com.pollogamer.proxy.packet.packets.PacketCommand;
import com.pollogamer.proxy.packet.packets.PacketDisconnect;
import com.pollogamer.proxy.packet.packets.PacketServer;
import net.md_5.bungee.BungeeCord;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {

    private static List<PacketWrapper> packets = new ArrayList<>();

    public PacketManager() {
        registerPacket(new PacketDisconnect());
        registerPacket(new PacketCommand());
        registerPacket(new PacketServer());
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

    public String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public void log(String text) {
        BungeeCord.getInstance().getConsole().sendMessage(text);
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }

}
