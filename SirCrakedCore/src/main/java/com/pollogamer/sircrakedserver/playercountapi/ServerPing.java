package com.pollogamer.sircrakedserver.playercountapi;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerPing {

    public static int onlineint(String address, int port, int timeout) {
        int players = -1;
        NewServerPing server = new NewServerPing();
        server.setAddress(new InetSocketAddress(address, port));
        server.setTimeout(timeout);
        try {
            NewServerPing.StatusResponse response = server.fetchData();
            players = response.getPlayers().getOnline();
        } catch (IOException ex) {
        }
        return players;
    }

    public static int maxonlineint(String address, int port, int timeout) {
        int maxplayers = -1;
        NewServerPing server = new NewServerPing();
        server.setAddress(new InetSocketAddress(address, port));
        server.setTimeout(timeout);
        try {
            NewServerPing.StatusResponse response = server.fetchData();
            maxplayers = response.getPlayers().getMax();
        } catch (IOException ex) {
        }
        return maxplayers;
    }

    public static String motdserver(String address, int port, int timeout) {
        String motd = "Apagado";
        NewServerPing server = new NewServerPing();
        server.setAddress(new InetSocketAddress(address, port));
        server.setTimeout(timeout);
        try {
            NewServerPing.StatusResponse response = server.fetchData();
            motd = server.fetchData().getDescription();
        } catch (IOException ex) {
        }
        return motd;
    }

    public static String getAllInfo(String address, int port, int timeout) {
        String online = "-1,-1,Apagado";
        String motd = null;
        int players = -1;
        int maxplayers = -1;
        NewServerPing server = new NewServerPing();
        server.setAddress(new InetSocketAddress(address, port));
        server.setTimeout(timeout);
        try {
            NewServerPing.StatusResponse response = server.fetchData();
            String tempmotd = response.getDescription();
            motd = response.getDescription();
            players = response.getPlayers().getOnline();
            maxplayers = response.getPlayers().getMax();
            online = players + "," + maxplayers + "," + motd;
        } catch (IOException ex) {
        }
        return online;
    }
}
