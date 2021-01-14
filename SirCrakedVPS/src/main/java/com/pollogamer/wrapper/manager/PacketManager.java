package com.pollogamer.wrapper.manager;

import com.pollogamer.wrapper.objects.McServer;
import com.pollogamer.wrapper.packet.PacketWrapper;
import com.pollogamer.wrapper.packet.packets.PacketCommand;
import com.pollogamer.wrapper.packet.packets.PacketRemove;
import com.pollogamer.wrapper.packet.packets.PacketServer;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {

    private static List<PacketWrapper> packets = new ArrayList<>();

    public PacketManager() {
        registerPacket(new PacketRemove());
        registerPacket(new PacketCommand());
        registerPacket(new PacketServer());
    }

    public void
    sendPacket(String[] args, McServer mcServer) {
        PacketWrapper packetWrapper = getPacket(args);
        if (packetWrapper != null) {
            packetWrapper.execute(args, mcServer);
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
