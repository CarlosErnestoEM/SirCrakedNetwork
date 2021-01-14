package com.pollogamer.proxy.antibot;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pollogamer.proxy.Principal;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AntibotManager implements Listener {

    private Cache<String, Byte> playersJoined = CacheBuilder.newBuilder().maximumSize(50).expireAfterWrite(1, TimeUnit.SECONDS).build();
    private Cache<String, Byte> playersIP = CacheBuilder.newBuilder().maximumSize(50).expireAfterWrite(1, TimeUnit.SECONDS).build();
    private List<String> ipsbanned = new ArrayList<>();
    private boolean enabledAntibot = false;

    public AntibotManager() {
        Principal.getPlugin().getProxy().getPluginManager().registerListener(Principal.getPlugin(), this);
    }

    @EventHandler
    public void preLogin(PreLoginEvent event) {
        if (!isBannedIP(event.getConnection())) {
            String playerName = event.getConnection().getName();
            String ip = event.getConnection().getAddress().getHostName();
            playersJoined.put(playerName, (byte) 0);
            if (isEnabled()) {
                if (isbot(playerName)) {
                    event.setCancelled(true);
                    event.setCancelReason("§4§lSir§1§lCraked §7» §cPoshoAntibot §adice que eres 1 pendejo por mandar bots compa");
                    if (playersIP.getIfPresent(ip) != null) {
                        ipsbanned.add(ip);
                        Principal.getPlugin().getLogger().info("Banned ip " + ip);
                    }
                    playersIP.put(ip, (byte) 0);
                }
            } else {
                if (playersIP.getIfPresent(ip) != null) {
                    ipsbanned.add(ip);
                    Principal.getPlugin().getLogger().info("Banned ip " + ip);
                }
                playersIP.put(ip, (byte) 0);
            }
        } else {
            event.setCancelled(true);
            event.setCancelReason("§4§lSir§1§lCraked §7» §cPoshoAntibot §ate baneo la IP xdxd");
        }
    }

    public boolean isBannedIP(Connection connection) {
        return ipsbanned.contains(connection.getAddress().getHostName());
    }

    private boolean isEnabled() {
        if (!enabledAntibot) {
            if (playersJoined.size() > 5) {
                enabledAntibot = true;
                Principal.getPlugin().getLogger().info("Posible ataque de bots... Activando antibot");
            }
        } else {
            if (playersJoined.size() < 5) {
                enabledAntibot = false;
                Principal.getPlugin().getLogger().info("Ataque de bots detenido alv xd");
            }
        }
        return enabledAntibot;
    }

    private boolean isbot(String string) {
        if (string.length() > 7) {
            int alerts = (getPortionsNumber(string) + getPortionsUppercase(string));
            alerts += (string.contains("_") ? 1 : 0);
            alerts += (getUppercase(string) >= 4 ? 1 : 0);
            alerts += (getNumbers(string) >= 4 ? 1 : 0);
            alerts += (string.length() >= 12 ? 1 : 0);
            if (alerts >= 3) {
                return true;
            }
        }
        return false;
    }

    public int getPortionsUppercase(String string) {
        int portions = 0;
        int counter = 0;
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (isLetter(c)) {
                counter++;
                if (i == (string.toCharArray().length - 1)) {
                    if (counter >= 2) {
                        portions++;
                        counter = 0;
                    }
                }
            } else {
                if (counter >= 2) {
                    portions++;
                }
                counter = 0;
            }
        }
        return portions;
    }

    public int getPortionsNumber(String string) {
        int portions = 0;
        int counter = 0;
        for (int i = 0; i < string.toCharArray().length; i++) {
            char c = string.charAt(i);
            if (isDigit(c)) {
                counter++;
                if (i == (string.toCharArray().length - 1)) {
                    if (counter >= 2) {
                        portions++;
                        counter = 0;
                    }
                }
            } else {
                if (counter >= 2) {
                    portions++;
                }
                counter = 0;
            }
        }
        return portions;
    }

    public boolean isLetter(char c) {
        return Character.getType(c) == Character.UPPERCASE_LETTER;
    }

    public boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private int getUppercase(String string) {
        int upper = 0;
        int index = 0;
        while (index < string.length()) {
            int cp = string.codePointAt(index);
            index += Character.charCount(cp);
            int type = Character.getType(cp);
            switch (type) {
                case Character.UPPERCASE_LETTER:
                    upper++;
                    break;
            }
        }
        return upper;
    }

    private int getLowercase(String string) {
        int lower = 0;
        int index = 0;
        while (index < string.length()) {
            int cp = string.codePointAt(index);
            index += Character.charCount(cp);
            int type = Character.getType(cp);
            switch (type) {
                case Character.LOWERCASE_LETTER:
                    lower++;
                    break;
            }
        }
        return lower;
    }

    private int getNumbers(String string) {
        int count = 0;
        for (int i = 0, len = string.length(); i < len; i++) {
            if (Character.isDigit(string.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public boolean isEnabledAntibot() {
        return enabledAntibot;
    }
}
