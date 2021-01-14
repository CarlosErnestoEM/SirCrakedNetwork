package com.pollogamer.uhcsimulator.task;

import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.walls.WallBuild;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ResourceBundle;

public class ChunkLoaderTask extends BukkitRunnable {


    public static double percent;
    public static int ancientPercent;
    public static double currentChunkLoad;
    public static double totalChunkToLoad;
    public static int cx;

    public ChunkLoaderTask(int paramInt) {
        percent = 0.0D;
        ancientPercent = 0;
        int i = paramInt * 2 / 16;
        cx = -i;
        totalChunkToLoad = i * i;
        currentChunkLoad = 0.0D;
    }

    public void run() {
        for (int i = cx; i <= cx + 1; i++) {
            for (int j = cx; j <= cx + 1; j++) {
                try {
                    Chunk localChunk = Bukkit.getWorld("game").getChunkAt(i, j);
                    localChunk.load();
                    currentChunkLoad += 1.0D;
                } catch (Exception localException2) {
                }
                try {
                    ResourceBundle.clearCache();
                } catch (Exception localException3) {
                }
            }
        }
        cx += 2;
        percent = currentChunkLoad / totalChunkToLoad * 100.0D;
        if (ancientPercent < (int) percent) {
            if ((ancientPercent > 0) && (ancientPercent <= 100)) {
                Principal.getPlugin().log("Cargando chunks.. " + (int) percent + "%");
            }
            ancientPercent = (int) percent;
        }
        if (percent >= 100.0D) {
            try {
                ResourceBundle.clearCache();
                System.gc();
            } catch (Exception localException1) {
            }
            WallBuild.build(100, Bukkit.getWorld("game"));
            cancel();
        }
    }
}
