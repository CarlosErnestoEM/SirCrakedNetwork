package com.pollogamer.wrapper.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupServer {

    private static Map<String, GroupServer> groupServer = new HashMap<>();

    private String groupName;
    private List<McServer> mcServers;

    public GroupServer(String groupName) {
        this.groupName = groupName;
        this.mcServers = new ArrayList<>();
        groupServer.put(groupName, this);
        format("ServerGroup", "Now created " + groupName + " group!");
    }

    public void addServer(McServer mcServer) {
        mcServers.add(mcServer);
        format("ServerGroup", mcServer.getServerName() + " has added to group " + groupName);
    }

    public void removeServer(McServer mcServer) {
        mcServers.remove(mcServer);
        format("ServerGroup", mcServer.getServerName() + " removed from group " + groupName);
        if (mcServers.size() == 0) {
            deleteGroup();
        }
    }

    public void deleteGroup() {
        format("ServerGroup", "Group " + groupName + " deleted because is empty! :(");
        groupServer.remove(groupName);
    }

    public void sendCommand(String command) {
        for (McServer mcServer : mcServers) {
            mcServer.sendCommand(command);
        }
        format("ServerGroup", "Commands has been sended to all servers of group " + groupName);
    }

    public static void addToAGroup(McServer mcServer) {
        if (hasGroup(mcServer)) {
            GroupServer groupServer = getGroupServer(mcServer);
            groupServer.addServer(mcServer);
        }
    }

    public static void removeFromGroup(McServer mcServer) {
        if (hasGroup(mcServer)) {
            GroupServer groupServer = getGroupServer(mcServer);
            groupServer.removeServer(mcServer);
            if (mcServer.getServerName().contains("-")) {
                MinigameServer minigameServer = MinigameServer.getMiniGame(mcServer.getServerName());
                if (minigameServer != null) {
                    ServersConfiguration serversConfiguration = ServersConfiguration.getServerConfiguration(minigameServer.getSvType());
                    if (serversConfiguration != null) {
                        serversConfiguration.removeServer(minigameServer);
                    }
                    minigameServer.end();
                }
            }
        }
    }

    private static GroupServer getGroupServer(String[] svData) {
        if (groupServer.containsKey(svData[0])) {
            return groupServer.get(svData[0]);
        } else {
            return new GroupServer(svData[0]);
        }
    }

    private static GroupServer getGroupServer(McServer mcServer) {
        if (hasGroup(mcServer)) {
            return getGroupServer(mcServer.getServerName().split("-"));
        } else {
            return null;
        }
    }

    public static GroupServer getGroupServer(String groupName) {
        if (groupServer.containsKey(groupName)) {
            return groupServer.get(groupName);
        } else {
            return null;
        }
    }

    public static Map<String, GroupServer> getGroupServer() {
        return groupServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<McServer> getMcServers() {
        return mcServers;
    }

    private static boolean hasGroup(McServer mcServer) {
        return mcServer.getServerName().contains("-");
    }

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
