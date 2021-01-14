package com.pollogamer.sircrakedhub.listener;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedhub.Principal;
import com.pollogamer.sircrakedhub.inv.MenuLobbies;
import com.pollogamer.sircrakedhub.inv.MenuPerfil;
import com.pollogamer.sircrakedhub.inv.MenuServidores;
import com.pollogamer.sircrakedhub.manager.PlayerManager;
import com.pollogamer.sircrakedserver.objects.SirPlayer;
import com.pollogamer.sircrakedserver.utils.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    public static ArrayList<String> motd = new ArrayList<String>();
    public static List<String> stacked = new ArrayList<>();
    public static String actionbar;
    public static boolean joinmessage;
    public static boolean leavemessage;
    public static Location spawn;
    PowerfulPermsPlugin pp = (PowerfulPermsPlugin) Bukkit.getPluginManager().getPlugin("PowerfulPerms");
    PermissionManager permissionManager = pp.getPermissionManager();

    public PlayerListener() {
        init();
    }

    @EventHandler
    public void DropeoDeItems(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.dropear")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getRightClicked();
            if (zombie.getPassenger() == null) {
                zombie.setPassenger(event.getPlayer());
            } else {
                //mandar mensaje que busque a un zombie libre
            }
        }
    }

    @EventHandler
    public void Morir(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("sircraked.agarrar")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionManager.getPermissionPlayer(p.getUniqueId()).getPrefix());
        if (p.hasPermission("sircraked.color")) {
            event.setFormat(prefix + " §a%1$s§f: §c%2$s");
        } else {
            event.setFormat(prefix + " §a%1$s§f: %2$s");
        }
    }

    @EventHandler
    public void Entrar(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        SirPlayer sirPlayer = SirPlayer.getPlayer(p);
        Principal.plugin.messageListener.getCount(p, null);
        if (joinmessage) {
            event.setJoinMessage(null);
        }
        p.teleport(spawn);
        p.setMaxHealth(2.0D);
        p.setCompassTarget(spawn);
        p.setHealth(p.getMaxHealth());
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionManager.getPermissionPlayer(p.getUniqueId()).getPrefix());
        for (String s : motd) {
            s = s.replaceAll("%player%", p.getName());
            s = s.replaceAll("%prefix%", prefix);
            MessagesController.sendCenteredMessage(p, s);
        }

        PlayerManager.setplayerkit(p);
        MessagesController.sendTitle(p, "§4§LSir§1§lCraked §a§lNetwork", "§bEstas en la §c" + Bukkit.getServerName(), 20, 50, 20);
        MessagesController.sendFullActionBar(p, actionbar);
        ScoreboardAPI scoreboard = new ScoreboardAPI("&4&lSir&1&lCraked");
        scoreboard.add("&a", 13);
        scoreboard.add("&e&l" + p.getName(), 12);
        scoreboard.add("&a ", 11);
        scoreboard.add("&aRango: &7" + permissionManager.getPermissionPlayer(p.getUniqueId()).getPrimaryGroup().getName(), 10);
        scoreboard.add("&aSirCoins: &7Cargando...", 9);
        scoreboard.add("   ", 8);
        scoreboard.add("&aServidor: &7" + Bukkit.getServerName(), 7);
        scoreboard.add("&aJugadores: &7" + Principal.plugin.messageListener.players, 6);
        scoreboard.add("&aPing: &7Cargando...", 5);
        scoreboard.add("  ", 4);
        scoreboard.add("§aNoticias", 3);
        scoreboard.add("§aCargando...", 2);
        scoreboard.add(" ", 1);
        scoreboard.add("&eplay.sircraked.com", 0);
        scoreboard.update();
        scoreboard.send(p);
        Scroller scroller = new Scroller("§aNuevo SkyBlock + 50% de descuento            ", 18, 0, '§');
        new BukkitRunnable() {
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                } else {
                    scoreboard.add("&aRango: &7" + permissionManager.getPermissionPlayer(p.getUniqueId()).getPrimaryGroup().getName(), 10);
                    scoreboard.add("&aSirCoins: &7" + sirPlayer.getCoins(), 9);
                    scoreboard.add("&aJugadores: &7" + Principal.plugin.messageListener.players, 6);
                    scoreboard.add("&aPing: &7" + PlayerManager.getPlayerPing(p), 5);
                    scoreboard.update();
                    scoreboard.send(p);
                }
            }
        }.runTaskTimer(Principal.plugin, 120, 160);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                } else {
                    scoreboard.add(scroller.next(), 2);
                    scoreboard.update();
                    scoreboard.send(p);
                }
            }
        }.runTaskTimer(SirCrakedCore.getCore(), 5, 2);
        new ScoreboardIP(scoreboard, p);
    }

    @EventHandler
    public void Salir(PlayerQuitEvent event) {
        if (leavemessage) {
            event.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        Player p = event.getPlayer();
        Action a = event.getAction();
        if (p.getItemInHand().hasItemMeta()) {
            if (p.getItemInHand().getItemMeta().hasDisplayName()) {
                if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (p.getItemInHand().getItemMeta().getDisplayName().equals(PlayerManager.compass.getItemMeta().getDisplayName())) {
                        new MenuServidores(Principal.plugin, p);
                    } else if (p.getItemInHand().getItemMeta().getDisplayName().equals(PlayerManager.cosmeticos.getItemMeta().getDisplayName())) {
                        Bukkit.dispatchCommand(p, "gadgetsmenu main");
                    } else if (p.getItemInHand().getItemMeta().getDisplayName().equals(PlayerManager.Lobbies.getItemMeta().getDisplayName())) {
                        new MenuLobbies(Principal.plugin, p);
                    } else if (p.getItemInHand().getItemMeta().getDisplayName().equals(PlayerManager.skull.getItemMeta().getDisplayName())) {
                        new MenuPerfil(Principal.plugin, p);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void ClickInventory(InventoryClickEvent event) {
        if ((!event.getWhoClicked().hasPermission("sircraked.moverinv")) && (event.getInventory().getName().equals(event.getWhoClicked().getInventory().getName()))) {
            event.setCancelled(true);
        }
    }

    public static void init() {
        for (String s : Principal.plugin.getConfig().getStringList("motd")) {
            s = s.replaceAll("&", "§");
            motd.add(s);
        }

        actionbar = Principal.plugin.getConfig().getString("actionbar");
        actionbar = actionbar.replaceAll("&", "§");
        spawn = Utils.deserializeLoc(Principal.plugin.getConfig().getString("spawn"));
        joinmessage = Principal.plugin.getConfig().getBoolean("disablejoinmessage");
        leavemessage = Principal.plugin.getConfig().getBoolean("disableleavemessage");
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().getBlockY() < 5) {
                        player.teleport(spawn);
                    }
                }
            }
        }.runTaskTimer(Principal.plugin, 5, 5);
    }
}
