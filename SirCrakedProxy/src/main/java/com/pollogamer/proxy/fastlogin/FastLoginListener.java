package com.pollogamer.proxy.fastlogin;

import com.google.common.base.Charsets;
import com.pollogamer.proxy.Principal;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class FastLoginListener implements Listener {

    private List<String> playersToConnect = new CopyOnWriteArrayList<>();

    @EventHandler
    public void onJoin(PreLoginEvent event) {
        if (!Principal.getPlugin().getAntibotManager().isEnabledAntibot()) {
            PendingConnection connection = event.getConnection();
            Boolean premiumEnabled = getBooleanFromMySQL(connection.getName());
            if (premiumEnabled != null) {
                if (premiumEnabled) {
                    playersToConnect.add(connection.getName());
                    connection.setOnlineMode(true);
                }
            }
        }
    }

    private UUID getOfflineUUID(String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin2(ServerConnectEvent event) {
        if (playersToConnect.contains(event.getPlayer().getName())) {
            changeUUID(event.getPlayer().getPendingConnection());
            event.setTarget(Principal.getPlugin().getProxy().getServerInfo("Hub-1"));
            event.getPlayer().sendMessage("§a§lCUENTA §7»§f Logueado automaticamente por ser premium!");
            playersToConnect.remove(event.getPlayer().getName());
        }
    }

    public void changeUUID(PendingConnection pendingConnection) {
        try {
            Class<?> initialHandlerClass = pendingConnection.getClass();
            Field uniqueIdField = initialHandlerClass.getDeclaredField("uniqueId");
            uniqueIdField.setAccessible(true);
            uniqueIdField.set(pendingConnection, getOfflineUUID(pendingConnection.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPremium(PendingConnection pendingConnection) {
        try {
            Class<?> initialHandlerClass = pendingConnection.getClass();
            Field uniqueIdField = initialHandlerClass.getDeclaredField("onlineMode");
            uniqueIdField.setAccessible(true);
            uniqueIdField.setBoolean(pendingConnection, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean getBooleanFromMySQL(String playerName) {
        Boolean premium = null;
        if (Principal.getPlugin().getModuleManager().getFastLogin().isPremium(playerName) != null) {
            return Principal.getPlugin().getModuleManager().getFastLogin().isPremium(playerName);
        } else {
            try {
                ResultSet resultSet = Principal.getPlugin().getModuleManager().getFastLogin().getHikariSQLManager().Query("SELECT `enabled` FROM `premium` WHERE `username`= '" + playerName.toLowerCase() + "'");
                if (resultSet.next()) {
                    premium = resultSet.getBoolean(1);
                    Principal.getPlugin().getModuleManager().getFastLogin().getPremiumPlayers().put(playerName, premium);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return premium;
    }
}
