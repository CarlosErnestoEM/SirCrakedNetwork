package com.pollogamer.builduhc.manager;

import com.pollogamer.builduhc.Principal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class PlayerManager {

    public static int getPlayerPing(Player player) {
        int ping = 0;
        try {
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
            Object converted = craftPlayer.cast(player);
            Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
            Object entityPlayer = handle.invoke(converted, new Object[0]);
            Field pingField = entityPlayer.getClass().getField("ping");
            ping = pingField.getInt(entityPlayer);
            if (ping > 1000) {
                return 1000;
            } else {
                return ping;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ping;
    }

    private static String getServerVersion() {
        Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");

        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        String version = pkg.substring(pkg.lastIndexOf('.') + 1);
        if (!brand.matcher(version).matches()) {
            version = "";
        }
        return version;
    }

    public static void sendToServer(Player player, String targetServer) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Principal.getPlugin(), "BungeeCord", b.toByteArray());
    }
}
