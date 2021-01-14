package com.pollogamer.proxy.fastlogin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pollogamer.proxy.Principal;
import com.pollogamer.proxy.manager.HikariSQLManager;
import com.pollogamer.proxy.object.Module;
import net.md_5.bungee.BungeeCord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class FastLogin extends Module {

    private Cache<String, Boolean> premiumPlayers = CacheBuilder.newBuilder().maximumSize(300).expireAfterWrite(10, TimeUnit.MINUTES).build();
    private HikariSQLManager hikariSQLManager;

    @Override
    public void onEnable() {
        format("FastLogin", "Enabling FastLogin...");
        registerListener(new FastLoginListener());
        format("FastLogin", "Listener registered...");
        registerCommand(new CMDPremium());
        format("FastLogin", "Registered commands...");
        format("FastLogin", "Connecting to the database...");
        hikariSQLManager = new HikariSQLManager("sircraked");
        createTable();
        format("FastLogin", "Loaded successfully");
    }

    public Boolean isPremium(String playerName) {
        if (premiumPlayers.getIfPresent(playerName) != null) {
            return premiumPlayers.getIfPresent(playerName);
        } else {
            return null;
        }
    }

    public void checkIsPremium(String playerName) {
        if (!existOnCache()) {
            boolean isPremium;
            BungeeCord.getInstance().getScheduler().runAsync(Principal.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    boolean isPremium = false;
                    String result = "null";
                    try {
                        final HttpURLConnection conn = getConnection("https://api.mojang.com/users/profiles/minecraft/" + playerName);
                        if (conn.getResponseCode() == 200) {
                            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            result = reader.readLine();
                        }
                    } catch (MalformedURLException ex) {
                    } catch (IOException ex2) {
                    }
                    isPremium = (result.length() >= 16 && !result.equals(""));
                    premiumPlayers.put(playerName, isPremium);
                    uploadToMySQL(playerName, isPremium);
                }
            });
        }
    }

    public static boolean isPremiumDB(String playerName) {
        ResultSet resultSet = Principal.getPlugin().getModuleManager().getFastLogin().getHikariSQLManager().Query("SELECT `enabled` FROM `premium` WHERE `username`= '" + playerName.toLowerCase() + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void uploadToMySQL(String playerName, boolean premium) {
        Principal.getPlugin().getModuleManager().getFastLogin().getHikariSQLManager().Update("INSERT INTO premium(username, enabled) VALUES('" + playerName.toLowerCase() + "', '" + (premium ? 1 : 0) + "') ON DUPLICATE KEY UPDATE enabled=" + (premium ? 1 : 0));
    }

    private HttpURLConnection getConnection(final String url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Premium-Checker");
        return connection;
    }

    public void createTable() {
        Principal.getPlugin().getModuleManager().getFastLogin().getHikariSQLManager().createTable("CREATE TABLE IF NOT EXISTS `premium` (`username` VARCHAR(20), `enabled` BOOLEAN)");
        format("FastLogin", "Created table");
    }

    private boolean existOnCache() {
        if (premiumPlayers.getIfPresent(premiumPlayers) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Cache<String, Boolean> getPremiumPlayers() {
        return premiumPlayers;
    }

    public HikariSQLManager getHikariSQLManager() {
        return hikariSQLManager;
    }
}
