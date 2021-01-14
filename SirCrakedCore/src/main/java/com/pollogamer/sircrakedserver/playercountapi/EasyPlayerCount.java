package com.pollogamer.sircrakedserver.playercountapi;

public class EasyPlayerCount {

    public static int GetPlayers(String IP, String port, int Timeout) {
        int onlineplayer = ServerPing.onlineint(IP, Integer.parseInt(port), Timeout);
        return onlineplayer;
    }

    public static int GetMaxPlayers(String IP, String port, int Timeout) {
        int maxplayers = ServerPing.maxonlineint(IP, Integer.parseInt(port), Timeout);
        return maxplayers;
    }

    public static String GetMotd(String IP, String port, int Timeout) {
        String motd = ServerPing.motdserver(IP, Integer.parseInt(port), Timeout);
        return motd;
    }
}
