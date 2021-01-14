package com.pollogamer.uhcsimulator.extras;

import com.pollogamer.uhcsimulator.listener.PlayerListener;
import com.pollogamer.uhcsimulator.walls.WallBuild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Random;

public class Utils {

    public static Random random;

    public static boolean deleteWorld(File paramFile) {
        if (paramFile.exists()) {
            File[] arrayOfFile = paramFile.listFiles();
            for (int i = 0; i < arrayOfFile.length; i++) {
                if (arrayOfFile[i].isDirectory()) {
                    deleteWorld(arrayOfFile[i]);
                } else {
                    arrayOfFile[i].delete();
                }
            }
        }
        return paramFile.delete();
    }

    public static String serializeTime(int time) {
        return String.format("%01d:%02d", time / 60, time % 60);
    }

    public static void addRelativeBlocks(Block block) {
        Block up = block.getRelative(BlockFace.UP);
        if (up.getType().equals(Material.BEDROCK)) {
            up.setType(Material.AIR);
        } else {
            PlayerListener.blocks.add(up);
        }
    }

    public static void tpInside(final Player p) {
        final int i = Lang.bordersize - 10;
        final int j = -Lang.bordersize + 10;
        final int k = Lang.bordersize - 10;
        final int m = -Lang.bordersize + 10;
        final int n = WallBuild.getRandom(j, i);
        final int i2 = WallBuild.getRandom(m, k);
        final int i3 = Bukkit.getWorld("game").getHighestBlockYAt(n, i2);
        final World localWorld = Bukkit.getWorld("game");
        final Location localLocation = new Location(localWorld, (double) n, (double) i3, (double) i2);
        p.teleport(localLocation);
    }

    /**
     * Concatenate two arrays
     *
     * @param a   first array
     * @param b   second array
     * @param <T> array type
     * @return concatenated array
     */

    public static <T> T[] concatenateArray(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        // create the new array
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);

        // copy the old arrays into the new array
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public static Horse createHorse(PlayerInteractEvent event, Player player) {
        Horse horse = (Horse) event.getClickedBlock().getLocation().getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.HORSE);
        horse.setTamed(true);
        horse.setOwner(player);
        horse.setBreed(true);
        horse.setColor(getRandomColor());
        horse.setPassenger(player);
        return horse;
    }

    public static Horse.Color getRandomColor() {
        return com.pollogamer.sircrakedserver.utils.Utils.getObjectRandomfromArray(Horse.Color.values());
    }
}
