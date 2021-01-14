package com.pollogamer.sircrakedserver.troll.trolls;

import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.troll.AbstractTroll;
import com.pollogamer.sircrakedserver.troll.TrollManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TrollTWD extends AbstractTroll {

    public TrollTWD() {
        super(11, "The Walking Dead", new ItemStackBuilder(Material.SKULL_ITEM).setStackData((short) 2).setName("&AThe Walking Dead"), false);
    }

    @Override
    public void action(Player player, Player trolled) {
        TrollManager.trolled.add(trolled);
        trolled.getInventory().setArmorContents(null);
        trolled.getInventory().clear();
        trolled.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 100, 3));
        trolled.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 100, 3));
        Zombie z1 = createZombie("&eeZ", trolled);
        Zombie z2 = createZombie("&aL", trolled);
        Zombie z3 = createZombie("&aAchingar su made el america", trolled);
        Zombie z4 = createZombie("&aputo el que lea", trolled);
        Zombie z5 = createZombie("&eVape", trolled);
        new BukkitRunnable() {
            int teleport = 0;

            @Override
            public void run() {
                if (trolled.isDead() || !trolled.isOnline()) {
                    z1.remove();
                    z2.remove();
                    z3.remove();
                    z4.remove();
                    z5.remove();
                    TrollManager.trolled.remove(trolled);
                    cancel();
                } else {
                    z1.setTarget(trolled);
                    z2.setTarget(trolled);
                    z3.setTarget(trolled);
                    z4.setTarget(trolled);
                    z5.setTarget(trolled);
                    z1.setFireTicks(0);
                    z2.setFireTicks(0);
                    z3.setFireTicks(0);
                    z4.setFireTicks(0);
                    z5.setFireTicks(0);
                    teleport++;
                    if (teleport % 50 == 0) {
                        z1.teleport(trolled);
                        z2.teleport(trolled);
                        z3.teleport(trolled);
                        z4.teleport(trolled);
                        z5.teleport(trolled);
                        teleport = 0;
                    }
                    if (teleport > 1000) {
                        if (trolled.isDead() || !trolled.isOnline()) {
                            z1.remove();
                            z2.remove();
                            z3.remove();
                            z4.remove();
                            z5.remove();
                            TrollManager.trolled.remove(trolled);
                            cancel();
                        } else {
                            TrollManager.trolled.remove(trolled);
                            cancel();
                            trolled.setHealth(0);
                        }
                    }
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 1, 1);

    }

    public Zombie createZombie(String name, Player trolled) {
        Zombie zombie = (Zombie) trolled.getWorld().spawnEntity(trolled.getLocation(), EntityType.ZOMBIE);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 2000, 2));
        zombie.setFireTicks(0);
        zombie.setTarget(trolled);
        zombie.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        zombie.setCustomNameVisible(true);
        return zombie;
    }
}

