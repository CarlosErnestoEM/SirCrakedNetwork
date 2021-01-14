package com.pollogamer.uhcsimulator.task;

import com.pollogamer.uhcsimulator.Principal;
import com.pollogamer.uhcsimulator.extras.Lang;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EnablePluginTask extends BukkitRunnable {

    public static BukkitTask a;

    public EnablePluginTask() {
        runTaskLater(Principal.getPlugin(), 1);
    }

    public void run() {
        a = new ChunkLoaderTask(Lang.bordersize + 10).runTaskTimer(Principal.getPlugin(), 0, 1);
    }
}
