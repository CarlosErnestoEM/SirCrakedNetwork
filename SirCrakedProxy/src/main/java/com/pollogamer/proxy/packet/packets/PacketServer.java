package com.pollogamer.proxy.packet.packets;

import com.pollogamer.proxy.packet.PacketWrapper;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetSocketAddress;

public class PacketServer extends PacketWrapper {

    public PacketServer() {
        super("server");
    }

    @Override
    public void execute(String[] args) {
        format("Packet-Manager", "Received packet " + getPacketName());
        if (args.length == 4) {
            if (args[1].equalsIgnoreCase("add")) {
                String[] ip = args[3].split(":");
                addServer(args[2], new InetSocketAddress(ip[0], Integer.parseInt(ip[1])), "None", false);
                format("Server-Added", "Server " + args[2] + " added to BungeeCord!");
            } else {
                format("ERROR", "The packet are bad...");
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("remove")) {
                try {
                    removeServer(args[2]);
                    format("Server-Removed", "Server " + args[2] + " removed from BungeeCord!");
                } catch (Exception e) {
                    format("ERROR", "Could to remove server " + args[2]);
                }
            } else {
                format("ERROR", "The packet are bad...");
            }
        } else {
            format("ERROR", "The packet are bad...");
        }
    }

    public void addServer(String name, InetSocketAddress address, String motd, boolean restricted) {
        ProxyServer.getInstance().getServers().put(name, ProxyServer.getInstance().constructServerInfo(name, address, motd, restricted));
    }

    public void removeServer(String name) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo(name).getPlayers()) {
            p.connect(BungeeCord.getInstance().getServerInfo("HUB-1"));
        }
        ProxyServer.getInstance().getServers().remove(name);
    }
}
