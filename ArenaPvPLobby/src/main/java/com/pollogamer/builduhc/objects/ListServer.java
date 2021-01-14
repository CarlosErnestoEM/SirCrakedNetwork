package com.pollogamer.builduhc.objects;

import com.pollogamer.builduhc.Principal;
import com.pollogamer.sircrakedserver.objects.GameServer;

import java.util.ArrayList;
import java.util.List;

public class ListServer {

    public static List<ListServer> listServers = new ArrayList<>();
    private String type;
    private List<GameServer> gameServers = new ArrayList<>();
    private List<String> allowedstages = new ArrayList<>();
    private List<String> startingstages = new ArrayList<>();

    public ListServer(String type) {
        this.type = type;
        init();
        listServers.add(this);
    }

    public void addServer(GameServer gameServer) {
        if (!gameServers.contains(gameServer)) {
            gameServers.add(gameServer);
        }
    }

    public static ListServer getListServer(String type) {
        for (ListServer listServer : listServers) {
            if (listServer.getType().equalsIgnoreCase(type)) {
                return listServer;
            }
        }
        return null;
    }


    public String getType() {
        return type;
    }

    public void init() {
        for (String s : Principal.getPlugin().getServers().getStringList("servers.listserver." + type + ".allowed-stages")) {
            allowedstages.add(s);
        }
        for (String s : Principal.getPlugin().getServers().getStringList("servers.listserver." + type + ".starting-stages")) {
            startingstages.add(s);
        }
    }

    public int getAllPlayers() {
        int players = 0;
        for (GameServer gameServer : gameServers) {
            if (gameServer.online) {
                players += gameServer.onlineplayers;
            }
        }
        return players;
    }

    public GameServer getRandomServer() {
        GameServer sv = null;
        int players = 0;
        for (GameServer gameServer : getGameServers()) {
            if (isAllowToJoin(gameServer)) {
                if (gameServer.onlineplayers >= players) {
                    sv = gameServer;
                    players = gameServer.onlineplayers;
                }
            }
        }
        return sv;
    }

    public boolean isAllowToJoin(GameServer gameServer) {
        if (gameServer.online) {
            if (gameServer.onlineplayers < gameServer.maxplayers) {
                if (getAllowedstages().contains(gameServer.motd) || getStartingstages().contains(gameServer.motd)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<GameServer> getGameServers() {
        return gameServers;
    }

    public List<String> getAllowedstages() {
        return allowedstages;
    }

    public List<String> getStartingstages() {
        return startingstages;
    }
}
