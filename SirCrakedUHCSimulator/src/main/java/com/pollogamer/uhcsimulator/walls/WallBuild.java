package com.pollogamer.uhcsimulator.walls;

import com.pollogamer.uhcsimulator.extras.Lang;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class WallBuild {
    public static int count;
    public static int total;
    public static ArrayList<Block> change = new ArrayList<>();

    public static Location getCenterOfaBlock(final Location location) {
        if (location.getBlock().getX() > 0) {
            location.add(0.5, 0.0, 0.0);
        }
        if (location.getBlock().getZ() > 0) {
            location.add(0.0, 0.0, 0.5);
        }
        if (location.getBlock().getX() < 0) {
            location.add(0.5, 0.0, 0.0);
        }
        if (location.getBlock().getZ() < 0) {
            location.add(0.0, 0.0, 0.5);
        }
        return location;
    }

    public static void tpRandom(final Player p) {
        int border = Lang.bordersize;
        int i = border - 10;
        int j = -border + 10;
        int k = border - 10;
        int m = -border + 10;
        int n = getRandom(j, i);
        int i2 = getRandom(m, k);
        int i3 = Bukkit.getWorld("game").getHighestBlockYAt(n, i2) + 1;
        World w = Bukkit.getWorld("game");
        Location l = new Location(w, (double) n, (double) (i3 - 1), (double) i2);
        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(l);
    }

    public static Location getRandomLocation() {
        int border = Lang.bordersize;
        int i = border - 10;
        int j = -border + 10;
        int k = border - 10;
        int m = -border + 10;
        int n = getRandom(j, i);
        int i2 = getRandom(m, k);
        int i3 = Bukkit.getWorld("game").getHighestBlockYAt(n, i2) + 1;
        World w = Bukkit.getWorld("game");
        Location l = new Location(w, (double) n, (double) (i3 - 1), (double) i2);
        return l;
    }

    public static int getRandom(final int i, final int i2) {
        return new Random().nextInt(i2 - i + 1) + i;
    }

    public static void build(final int n, final World world) {
        WallBuild.change.clear();
        final int n2 = n + 1;
        final Location centerOfaBlock = getCenterOfaBlock(new Location(world, 0.0, (double) world.getHighestBlockYAt(0, 0), 0.0));
        final Location clone = centerOfaBlock.clone();
        final Location clone2 = centerOfaBlock.clone();
        final Location clone3 = centerOfaBlock.clone();
        final Location clone4 = centerOfaBlock.clone();
        clone.setX(centerOfaBlock.getX() - n2);
        clone.setZ(centerOfaBlock.getZ() - n2);
        clone.setY(1.0);
        clone2.setX(centerOfaBlock.getX() + n2);
        clone2.setZ(centerOfaBlock.getZ() - n2);
        clone2.setY(1.0);
        clone3.setX(centerOfaBlock.getX() + n2);
        clone3.setZ(centerOfaBlock.getZ() + n2);
        clone3.setY(1.0);
        clone4.setX(centerOfaBlock.getX() - n2);
        clone4.setZ(centerOfaBlock.getZ() + n2);
        clone4.setY(1.0);
        set(clone, clone2, Material.BEDROCK, world);
        set(clone2, clone3, Material.BEDROCK, world);
        set(clone3, clone4, Material.BEDROCK, world);
        set(clone4, clone, Material.BEDROCK, world);
        for (int i = 0; i < WallBuild.change.size(); ++i) {
            Location location = WallBuild.change.get(i).getLocation();
            Location location2 = WallBuild.change.get(i).getLocation();
            final int highestBlockYAt = world.getHighestBlockYAt(location);
            final int highestBlockYAt2 = world.getHighestBlockYAt(location2);
            location.setY((double) highestBlockYAt);
            location2.setY((double) highestBlockYAt2);
            for (int j = 0; j < 6; ++j) {
                location.getBlock().setType(Material.BEDROCK);
                location = location.add(0.0, 1.0, 0.0);
            }
            for (int k = 0; k < 2; ++k) {
                location2.getBlock().setType(Material.BEDROCK);
                location2 = location2.add(0.0, -1.0, 0.0);
            }
        }
    }

    public static void remove(final int n, final World world) {
        WallBuild.change.clear();
        final int n2 = n + 1;
        final Location location = new Location(world, 0.0, (double) world.getHighestBlockYAt(0, 0), 0.0);
        final Location clone = location.clone();
        final Location clone2 = location.clone();
        final Location clone3 = location.clone();
        final Location clone4 = location.clone();
        clone.setX(location.getX() - n2);
        clone.setZ(location.getZ() - n2);
        clone.setY(1.0);
        clone2.setX(location.getX() + n2);
        clone2.setZ(location.getZ() - n2);
        clone2.setY(1.0);
        clone3.setX(location.getX() + n2);
        clone3.setZ(location.getZ() + n2);
        clone3.setY(1.0);
        clone4.setX(location.getX() - n2);
        clone4.setZ(location.getZ() + n2);
        clone4.setY(1.0);
        set(clone, clone2, Material.AIR, world);
        set(clone2, clone3, Material.AIR, world);
        set(clone3, clone4, Material.AIR, world);
        set(clone4, clone, Material.AIR, world);
        for (int i = 0; i < WallBuild.change.size(); ++i) {
            Location location2 = WallBuild.change.get(i).getLocation();
            Location location3 = WallBuild.change.get(i).getLocation();
            final int highestBlockYAt = world.getHighestBlockYAt(location2);
            world.getHighestBlockYAt(location3);
            location2.setY((double) highestBlockYAt);
            for (int j = 0; j < 6; ++j) {
                location2.getBlock().setType(Material.BEDROCK);
                location2 = location2.add(0.0, 1.0, 0.0);
            }
            for (int k = 0; k < 2; ++k) {
                location3.getBlock().setType(Material.BEDROCK);
                location3 = location3.add(0.0, -1.0, 0.0);
            }
        }
    }

    public static void set(final Location location, final Location location2, final Material material, final World world) {
        final int min = Math.min(location.getBlockX(), location2.getBlockX());
        final int min2 = Math.min(location.getBlockY(), location2.getBlockY());
        final int min3 = Math.min(location.getBlockZ(), location2.getBlockZ());
        final int max = Math.max(location.getBlockX(), location2.getBlockX());
        final int max2 = Math.max(location.getBlockY(), location2.getBlockY());
        final int max3 = Math.max(location.getBlockZ(), location2.getBlockZ());
        for (int i = min; i <= max; ++i) {
            for (int j = min2; j <= max2; ++j) {
                for (int k = min3; k <= max3; ++k) {
                    WallBuild.change.add(world.getBlockAt(i, j, k));
                    for (final Entity entity : world.getEntities()) {
                        if (entity instanceof Item) {
                            entity.remove();
                        }
                    }
                }
            }
        }
    }
}
