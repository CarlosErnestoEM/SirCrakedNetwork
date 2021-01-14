package com.pollogamer.sircrakedserver.packet.packets;

import com.pollogamer.sircrakedserver.objects.McServer;
import com.pollogamer.sircrakedserver.packet.PacketWrapper;
import org.bukkit.Bukkit;

public class PacketServer extends PacketWrapper {

    public PacketServer() {
        super("server");
    }

    @Override
    public void execute(String[] args) {
        format("Packet-Server", "Received packet server");
        if (args.length >= 3) {
            String action = args[1];
            String serverName = args[2];
            switch (action.toLowerCase()) {
                case "add":
                    if (!serverName.equalsIgnoreCase(Bukkit.getServerName())) {
                        format("Packet-Server", "Adding new McServer object " + serverName);
                        McServer mcServer = McServer.fromJson(serverName);
                        break;
                    }
                default:
                    format("ERROR", "Only can usage server add <ServerName>");
                    break;
            }
        } else {
            format("ERROR", "Packet are bad...");
        }
    }
}
