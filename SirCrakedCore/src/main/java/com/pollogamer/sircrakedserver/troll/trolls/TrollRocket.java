package com.pollogamer.sircrakedserver.troll.trolls;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.troll.AbstractTroll;
import com.pollogamer.sircrakedserver.troll.TrollManager;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TrollRocket extends AbstractTroll {


    public TrollRocket() {
        super(10, "Cohete", new ItemStackBuilder(Material.FIREWORK).setName("&aCohete xd"), false);
    }

    @Override
    public void action(Player player, Player trolled) {
        TrollManager.trolled.add(trolled);
        MessagesController.sendTitle(trolled, "&a&lA VOLAR PRRO!", "", 20, 20, 20);
        new BukkitRunnable() {
            Location oldloc = trolled.getLocation();
            double y = trolled.getLocation().getY();
            double maxy = y + 14;
            boolean stay = true;
            int time;

            @Override
            public void run() {
                if (!trolled.isOnline()) {
                    TrollManager.trolled.remove(trolled);
                    cancel();
                } else {
                    if (stay) {
                        Location location = oldloc;
                        location.setPitch(0);
                        location.setYaw(location.getYaw() + 8);
                        oldloc = location;
                        trolled.teleport(location);
                        playEffect(Effect.SMOKE, location, 3);
                        time++;
                        if (time > 90) {
                            stay = false;
                        }
                    } else {
                        if (y < maxy) {
                            Location location = oldloc;
                            double y = location.getY() + 0.4;
                            this.y = y;
                            location.setY(y);
                            location.setPitch(0);
                            location.setYaw(location.getYaw() + 8);
                            oldloc = location;
                            trolled.teleport(location);
                            playEffect(Effect.MOBSPAWNER_FLAMES, location, 2);
                        } else {
                            trolled.getWorld().createExplosion(trolled.getLocation().getX(), trolled.getLocation().getY(), trolled.getLocation().getZ(), 1, false, false);
                            playEffectBlock(trolled.getLocation(), 5, 152);
                            trolled.setHealth(0);
                            TrollManager.trolled.remove(trolled);
                            cancel();
                        }
                    }
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 0, 0);
    }
}
