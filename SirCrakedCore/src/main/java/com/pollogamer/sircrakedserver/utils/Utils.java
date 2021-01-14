package com.pollogamer.sircrakedserver.utils;

import com.pollogamer.sircrakedserver.objects.SirPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.Random;

public class Utils {

    public static final Random random = new Random();

    public static String serializeLoc(Location l) {
        return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getYaw() + "," + l.getPitch();
    }

    public static Location deserializeLoc(String s) {
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
    }

    public static boolean moved(PlayerMoveEvent event) {
        return !(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ());
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static String getLevelBar(SirPlayer player) {
        double level = player.getLevel();
        String bar = "§9";
        for (double i = level; i > 0.02d; i -= 0.02d) {
            bar = bar + "|";
        }
        bar = bar + "§7";
        for (int i = bar.length(); i < 50; i++) {
            bar = bar + "|";
        }
        return bar;
    }

    public static <T> T getObjectRandomfromArray(T[] array) {
        return array[random.nextInt(array.length)];
    }

    public static int getPercentage(SirPlayer player) {
        double level = player.getLevel();
        return (int) (level * 100);
    }
}
