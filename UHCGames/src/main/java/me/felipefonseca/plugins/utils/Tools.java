package me.felipefonseca.plugins.utils;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class Tools {
    public static int getCentre(Player player) {
        Location location = Bukkit.getWorld("world").getWorldBorder().getCenter().clone();
        int n = (int) Math.floor(player.getLocation().distance(location));
        return n;
    }

    public static ItemStack glow(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack2 = CraftItemStack.asNMSCopy((ItemStack) itemStack);
        NBTTagCompound nBTTagCompound = null;
        if (!itemStack2.hasTag()) {
            nBTTagCompound = new NBTTagCompound();
            itemStack2.setTag(nBTTagCompound);
        }
        if (nBTTagCompound == null) {
            nBTTagCompound = itemStack2.getTag();
        }
        NBTTagList nBTTagList = new NBTTagList();
        nBTTagCompound.set("ench", (NBTBase) nBTTagList);
        itemStack2.setTag(nBTTagCompound);
        return CraftItemStack.asCraftMirror((net.minecraft.server.v1_8_R3.ItemStack) itemStack2);
    }

    public static String transform(double d) {
        boolean bl = false;
        if (d < 0.0) {
            bl = true;
            d *= -1.0;
        }
        int n = (int) d / 3600;
        int n2 = (int) (d - (double) (n * 3600)) / 60;
        int n3 = (int) d - n * 3600 - n2 * 60;
        String string = "" + n + "";
        String string2 = "" + n2 + "";
        String string3 = "" + n3 + "";
        while (string2.length() < 2) {
            string2 = "0" + string2;
        }
        while (string3.length() < 2) {
            string3 = "0" + string3;
        }
        return (bl ? "-" : "") + (n == 0 ? "" : new StringBuilder().append(string).append(":").toString()) + string2 + ":" + string3;
    }

    public static String locationToString(Location location) {
        return String.valueOf(new StringBuilder(String.valueOf(location.getWorld().getName())).append(":").append(location.getBlockX()).toString()) + ":" + String.valueOf(location.getBlockY()) + ":" + String.valueOf(location.getBlockZ() + ":" + String.valueOf(location.getYaw()) + ":" + String.valueOf(location.getPitch()));
    }

    public static Location stringToLoc(String string, Boolean old) {
        Location location = null;
        if (old) {
            try {
                World world = Bukkit.getWorld(string.split(":")[0]);
                Double d = Double.parseDouble(string.split(":")[1]);
                Double d2 = Double.parseDouble(string.split(":")[2]);
                Double d3 = Double.parseDouble(string.split(":")[3]);
                location = new Location(world, d + 0.5, d2.doubleValue(), d3 + 0.5);
            } catch (Exception var2_3) {
                // empty catch block
            }
        } else {
            try {
                World world = Bukkit.getWorld(string.split(":")[0]);
                Double d = Double.parseDouble(string.split(":")[1]);
                Double d2 = Double.parseDouble(string.split(":")[2]);
                Double d3 = Double.parseDouble(string.split(":")[3]);
                Float yaw = Float.parseFloat(string.split(":")[4]);
                Float pitch = Float.parseFloat(string.split(":")[5]);
                location = new Location(world, d + 0.5, d2.doubleValue(), d3 + 0.5, yaw, pitch);
            } catch (Exception var2_3) {
                // empty catch block
            }
        }
        return location;
    }

    public static void spawnFirework(Location loc) {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        int rt = r.nextInt(3) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.STAR;
        Color c1 = Color.ORANGE;
        Color c2 = Color.YELLOW;
        Color c3 = Color.GREEN;
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        int rp = 0;
        fwm.setPower(rp);
        fw.setFireworkMeta(fwm);
    }
}

