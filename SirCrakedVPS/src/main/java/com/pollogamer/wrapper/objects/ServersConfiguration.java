package com.pollogamer.wrapper.objects;

import com.pollogamer.wrapper.manager.ServerCreator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServersConfiguration {

    private static List<ServersConfiguration> serversConfigurations = new CopyOnWriteArrayList<>();

    private List<MinigameServer> minigameServers = new CopyOnWriteArrayList<>();
    private List<String> motdCanJoin = new CopyOnWriteArrayList<>();
    private String template;
    private Integer minServers;
    private Integer maxServers;

    public ServersConfiguration(String template, Integer minServers, Integer maxServers) {
        this.template = template;
        this.minServers = minServers;
        this.maxServers = maxServers;
        serversConfigurations.add(this);
        createServers();
    }

    public void createServers() {
        for (int i = 0; i < minServers; i++) {
            if (i >= maxServers) {
                format("ERROR", "Already are opened the MaxServers!");
            } else {
                addServer(ServerCreator.createServer(template));
            }
        }
    }

    public void addServer(MinigameServer minigameServer) {
        if (minigameServer != null) {
            minigameServers.add(minigameServer);
        }
    }

    public void removeServer(MinigameServer minigameServer) {
        if (minigameServers.contains(minigameServer)) {
            minigameServers.remove(minigameServer);
        }
        if (needOther()) {
            addServer(ServerCreator.createServer(template));
        }
    }

    private boolean needOther() {
        log("Calculando si se necesita otro servidor");
        if (minigameServers.size() < minServers) {
            if (maxServers < minigameServers.size()) {
                return true;
            }
        }
        return false;
    }

    public static ServersConfiguration getServerConfiguration(String template) {
        for (ServersConfiguration serversConfiguration : serversConfigurations) {
            if (serversConfiguration.getTemplate().equalsIgnoreCase(template)) {
                return serversConfiguration;
            }
        }
        return null;
    }

    public String getTemplate() {
        return template;
    }

    public Integer getMinServers() {
        return minServers;
    }

    public Integer getMaxServers() {
        return maxServers;
    }

    public static List<ServersConfiguration> getServersConfigurations() {
        return serversConfigurations;
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
