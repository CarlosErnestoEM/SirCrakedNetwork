package com.pollogamer.sircrakedserver.troll.trolls;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.troll.AbstractTroll;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TrollTravelToMoon extends AbstractTroll {

    public TrollTravelToMoon() {
        super(12, "Mandalo a la luna xd", new ItemStackBuilder(Material.FIREBALL).setName("&aMandalo a la luna xd"), false);
    }

    @Override
    public void action(Player player, Player trolled) {
        MessagesController.sendTitle(trolled, "&cTe vas a la luna vato", "&ey gratis alv", 20, 10, 20);
        playEffect(Effect.SMOKE, trolled.getLocation(), 5);
        trolled.getInventory().setHelmet(new ItemStackBuilder(Material.BEACON).setName("§aCasco lunar (?"));
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                time++;
                if (!trolled.isOnline()) {
                    cancel();
                    return;
                } else {
                    playEffect(Effect.SMOKE, trolled.getLocation(), 2);
                    if (time == 40) {
                        trolled.getActivePotionEffects().forEach(potionEffect -> trolled.removePotionEffect(potionEffect.getType()));
                        trolled.setVelocity(new Vector(0, 5, 0));
                        cancel();
                    }
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 2, 2);
    }
}
