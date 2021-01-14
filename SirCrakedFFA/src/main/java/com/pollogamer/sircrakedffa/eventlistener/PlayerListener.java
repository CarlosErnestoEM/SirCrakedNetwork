package com.pollogamer.sircrakedffa.eventlistener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.pollogamer.sircrakedffa.Principal;
import com.pollogamer.sircrakedffa.inv.Kits;
import com.pollogamer.sircrakedffa.inv.ModosDeJuego;
import com.pollogamer.sircrakedffa.manager.Lang;
import com.pollogamer.sircrakedffa.manager.PlayerManager;
import com.pollogamer.sircrakedserver.staffmode.StaffMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {
    private final PowerfulPermsPlugin pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    private PermissionManager permissionManager = pp.getPermissionManager();

    @EventHandler
    public void Join(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.teleport(Lang.ffa);
        PlayerManager.setCleanPlayer(p, GameMode.SURVIVAL);
        PlayerManager.setkitffadefault(p);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String prefix = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', permissionManager.getPermissionPlayer(p.getUniqueId()).getPrefix());
        if (p.hasPermission("sircraked.color")) {
            event.setFormat(prefix + " §a%1$s§f: §c%2$s");
        } else {
            event.setFormat(prefix + " §a%1$s§f: %2$s");
        }
    }

    @EventHandler
    public void Respawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        if (p.getWorld() == Lang.buildffa.getWorld()) {
            event.setRespawnLocation(Lang.buildffa);
            PlayerManager.setbuilduhckit(p);
        } else if (p.getWorld() == Lang.ffa.getWorld()) {
            event.setRespawnLocation(Lang.ffa);
            PlayerManager.setkitffadefault(p);
        } else if (p.getWorld() == Lang.comboffa.getWorld()) {
            event.setRespawnLocation(Lang.comboffa);
            PlayerManager.setkitcombo(p);
        }
    }

    @EventHandler
    public void PrenderFuego(PlayerInteractEvent event) {
        if (!event.hasItem()) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getItem().getType() != Material.FLINT_AND_STEEL) {
            return;
        }

        event.getPlayer().getInventory().setItemInHand(null);
    }

    @EventHandler
    public void AlMorir(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (((event.getEntity() != null)) && ((event.getEntity().getKiller() != null))) {
            Player killer = player.getKiller();
            player.sendMessage("§4§lSir§1§lCraked §aFFA §7§l» §cHas muerto a manos de " + ChatColor.GOLD + killer.getName() + " " + ChatColor.RED + "con " + (int) (float) killer.getHealth() / 2 + "❤");
            killer.sendMessage("§4§lSir§1§lCraked §aFFA §7§l» §cHas matado a " + ChatColor.GOLD + player.getName());
            player.getActivePotionEffects().clear();
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 90, 1));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2000, 0));
        }
    }

    @EventHandler
    public void Consume(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        if (!p.getInventory().getItemInHand().hasItemMeta()) return;
        if ((event.getPlayer().getItemInHand() != null) && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("§b§lGolden Head"))) {
            event.getPlayer().getActivePotionEffects().clear();
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2000, 2));
        }
    }

    @EventHandler
    public void Click(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();

        if (!event.hasItem()) {
            return;
        }

        if ((a == Action.PHYSICAL) || (event.getItem().getType() != Material.PAPER)) {
            return;
        }

        new ModosDeJuego(Principal.getPlugin(), p);
    }

    @EventHandler
    public void ClickEstrella(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();

        if (!event.hasItem()) {
            return;
        }

        if ((a == Action.PHYSICAL) || (event.getItem().getType() != Material.NETHER_STAR)) {
            return;
        }

        new Kits(Principal.getPlugin(), p);
    }

    @EventHandler
    public void DropeoDeItems(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.dropear")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void NoDrop(PlayerDeathEvent event) {
        event.getDrops().clear();
    }

    @EventHandler
    public void mundo(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        if (!StaffMode.isEnabled(p)) {
            p.getActivePotionEffects().forEach(potionEffect -> {
                p.removePotionEffect(potionEffect.getType());
            });
            if (p.getWorld() == Lang.buildffa.getWorld()) {
                PlayerManager.setbuilduhckit(p);
                p.setMaximumNoDamageTicks(20);
            } else if (p.getWorld() == Lang.ffa.getWorld()) {
                PlayerManager.setkitffadefault(p);
                p.setMaximumNoDamageTicks(20);
            } else if (p.getWorld() == Lang.comboffa.getWorld()) {
                PlayerManager.setkitcombo(p);
                p.setMaximumNoDamageTicks(0);
            } else {
                p.setMaximumNoDamageTicks(20);
            }
        }
    }
}
