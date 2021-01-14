/*
 * Decompiled with CFR 0_118.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Firework
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package me.felipefonseca.plugins.utils;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class Tools {
    public static void firework(Location loc, Color color1, Color color2, Color color3, FireworkEffect.Type type) {
        World world = loc.getWorld();
        for (int i = -2; i < 3; ++i) {
            Firework firework = (Firework) world.spawn(new Location(loc.getWorld(), loc.getX() + (double) (i * 5), loc.getY(), loc.getZ()), Firework.class);
            FireworkMeta data = firework.getFireworkMeta();
            data.addEffects(new FireworkEffect[]{FireworkEffect.builder().withColor(color1).withColor(color2).withColor(color3).with(type).trail(new Random().nextBoolean()).flicker(new Random().nextBoolean()).build()});
            data.setPower(new Random().nextInt(2) + 2);
            firework.setFireworkMeta(data);
        }
    }

    public static String locationToString(Location l) {
        return String.valueOf(new StringBuilder(String.valueOf(l.getWorld().getName())).append(":").append(l.getBlockX()).toString()) + ":" + String.valueOf(l.getBlockY()) + ":" + String.valueOf(l.getBlockZ());
    }

    public static Location stringToLoc(String s) {
        Location l = null;
        try {
            World world = Bukkit.getWorld((String) s.split(":")[0]);
            Double x = Double.parseDouble(s.split(":")[1]);
            Double y = Double.parseDouble(s.split(":")[2]);
            Double z = Double.parseDouble(s.split(":")[3]);
            l = new Location(world, x + 0.5, y.doubleValue(), z + 0.5);
        } catch (Exception world) {
            // empty catch block
        }
        return l;
    }

    public static String transform(double time) {
        boolean negative = false;
        if (time < 0.0) {
            negative = true;
            time *= -1.0;
        }
        int hours = (int) time / 3600;
        int minutes = (int) (time - (double) (hours * 3600)) / 60;
        int seconds = (int) time - hours * 3600 - minutes * 60;
        String hoursString = "" + hours + "";
        String minutesString = "" + minutes + "";
        String secondsString = "" + seconds + "";
        while (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }
        while (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }
        return (negative ? "-" : "") + (hours == 0 ? "" : new StringBuilder().append(hoursString).append(":").toString()) + minutesString + ":" + secondsString;
    }
}

